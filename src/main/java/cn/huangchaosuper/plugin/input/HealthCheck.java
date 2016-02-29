package cn.huangchaosuper.plugin.input;

import cn.huangchaosuper.bean.Tz;
import cn.huangchaosuper.plugin.IInput;

import java.util.concurrent.ArrayBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by I311579 on 10/21/2015.
 */
public class HealthCheck implements IInput {
    private static Logger logger = LoggerFactory.getLogger(File.class);
    private long millis;

    public HealthCheck() {
        this(10 * 1000);
    }

    public HealthCheck(long millis) {
        this.millis = millis;
    }

    @Override
    public void run(ArrayBlockingQueue<Tz> inQueue) throws InterruptedException {
        logger.debug(Thread.currentThread().getName());
        inQueue.put(new Tz("HealthCheck"));
        Thread.sleep(this.millis);
    }
}
