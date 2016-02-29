package cn.huangchaosuper;

import cn.huangchaosuper.plugin.IOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by I311579 on 10/21/2015.
 */
public class OutputExecutor implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(OutputExecutor.class);
    private List<IOutput> iOutputs = null;

    public OutputExecutor(List<IOutput> iOutputList) {
        this.iOutputs = iOutputList;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        StaticQueue.getInstance().getQueueList().forEach((elasticollectQueue) -> {
            iOutputs.forEach((iOutput) -> {
                try {
                    iOutput.receive(elasticollectQueue.outQueue);
                } catch (InterruptedException e) {
                    logger.error("output queue", e.fillInStackTrace());
                }
            });
        });
    }
}
