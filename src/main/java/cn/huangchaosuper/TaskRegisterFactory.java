package cn.huangchaosuper;

import cn.huangchaosuper.core.Utils;
import cn.huangchaosuper.plugin.IFilter;
import cn.huangchaosuper.plugin.IInput;
import cn.huangchaosuper.plugin.IOutput;
import cn.huangchaosuper.plugin.filter.Empty;
import cn.huangchaosuper.plugin.input.HealthCheck;
import cn.huangchaosuper.plugin.output.Console;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by I311579 on 10/21/2015.
 */
public class TaskRegisterFactory {
    private static Logger logger = LoggerFactory.getLogger(TaskRegisterFactory.class);
    private List<IInput> iInputs = new ArrayList<IInput>();
    private List<IOutput> iOutputs = new ArrayList<IOutput>();
    private List<IFilter> iFilters = new ArrayList<IFilter>();

    private long initialDelay;
    private long runnableDelay;
    private int queueLength;

    private static TaskRegisterFactory taskRegisterFactory = null;

    private TaskRegisterFactory() {
    }

    public static TaskRegisterFactory getInstance() {
        if (taskRegisterFactory == null) {
            taskRegisterFactory = new TaskRegisterFactory();
        }
        return taskRegisterFactory;
    }

    public void initialization() throws IOException {
        this.initialDelay = Utils.getProperty("schedule.initial.delay", 10000, Integer.class);
        this.runnableDelay = Utils.getProperty("schedule.runnable.delay", 100, Integer.class);
        this.queueLength = Utils.getProperty("process.queue.length", 100, Integer.class);
        String groovyScriptPath = Utils.getProperty("groovy.script.path", "./scripts/", String.class);

        GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine(groovyScriptPath);
        Binding binding = new Binding();
        binding.setVariable("input", new Input());
        binding.setVariable("output", new Output());
        binding.setVariable("filter", new Filter());

        for (String item : Utils.getFilesByPath(groovyScriptPath)) {
            try {
                groovyScriptEngine.run(item, binding);
            } catch (ResourceException e) {
                logger.error("ResourceException", e.fillInStackTrace());
            } catch (ScriptException e) {
                logger.error("ScriptException", e.fillInStackTrace());
            }
        }

        if (this.iInputs.size() == 0) {
            new Input().register(new HealthCheck());
        }

        if (this.iFilters.size() == 0){
            new Filter().register(new Empty());
        }

        if(this.iOutputs.size() == 0){
            new Output().register(new Console());
        }
    }

    public void execution() {
        StaticQueue.getInstance(this.iInputs.size(), this.queueLength);

        ScheduledExecutorService inputExecutorService = Executors.newScheduledThreadPool(this.iInputs.size());
        inputExecutorService.scheduleWithFixedDelay(new InputExecutor(this.iInputs), initialDelay, runnableDelay, TimeUnit.MILLISECONDS);

        ScheduledExecutorService filterExecutorService = Executors.newScheduledThreadPool(this.iFilters.size());
        filterExecutorService.scheduleWithFixedDelay(new FilterExecutor(this.iFilters), initialDelay, runnableDelay, TimeUnit.MILLISECONDS);

        ScheduledExecutorService outputExecutorService = Executors.newScheduledThreadPool(this.iOutputs.size());
        outputExecutorService.scheduleWithFixedDelay(new OutputExecutor(this.iOutputs), initialDelay, runnableDelay, TimeUnit.MILLISECONDS);

        logger.info(Utils.getElasticollectResourceString("process.started"));
    }

    public class Input {
        public void register(IInput iInput) {
            iInputs.add(iInput);
        }
    }

    public class Output {
        public void register(IOutput iOutput) {
            iOutputs.add(iOutput);
        }
    }

    public class Filter {
        public void register(IFilter iFilter) {
            iFilters.add(iFilter);
        }
    }
}
