package cn.huangchaosuper.plugin.filter;

import cn.huangchaosuper.bean.Tz;
import cn.huangchaosuper.plugin.IFilter;

import java.util.concurrent.ArrayBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by I311579 on 10/23/2015.
 */
public class Empty implements IFilter {
    private static Logger logger = LoggerFactory.getLogger(Empty.class);
    @Override
    public void filter(ArrayBlockingQueue<Tz> inQueues, ArrayBlockingQueue<Tz> outQueues) throws InterruptedException {
        logger.debug(Thread.currentThread().getName());
        if(inQueues.size()>0) {
            outQueues.put(inQueues.take());
        }
    }
}
