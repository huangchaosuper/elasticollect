package cn.huangchaosuper.plugin.output;

import cn.huangchaosuper.bean.Tz;
import cn.huangchaosuper.plugin.IOutput;

import java.util.concurrent.ArrayBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by I311579 on 10/23/2015.
 */
public class Console implements IOutput {
    private static Logger logger = LoggerFactory.getLogger(Console.class);
    @Override
    public void receive(ArrayBlockingQueue<Tz> outQueue) throws InterruptedException {
        logger.debug(Thread.currentThread().getName());
        if (outQueue.size() > 0) {
            System.out.println(outQueue.take().toString());
        }
    }
}
