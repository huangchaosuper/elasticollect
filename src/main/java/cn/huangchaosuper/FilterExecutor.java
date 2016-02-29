package cn.huangchaosuper;

import cn.huangchaosuper.plugin.IFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by I311579 on 10/21/2015.
 */
public class FilterExecutor implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(FilterExecutor.class);
    private List<IFilter> iFilters = null;

    public FilterExecutor(List<IFilter> iFilterList) {
        this.iFilters = iFilterList;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        StaticQueue.getInstance().getQueueList().forEach((elasticollectQueue) -> {
            iFilters.forEach((iFilter) -> {
                try {
                    iFilter.filter(elasticollectQueue.inQueue, elasticollectQueue.outQueue);
                } catch (InterruptedException e) {
                    logger.error("output queue", e.fillInStackTrace());
                }
            });
        });
    }
}
