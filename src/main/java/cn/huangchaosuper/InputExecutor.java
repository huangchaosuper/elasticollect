package cn.huangchaosuper;

import cn.huangchaosuper.plugin.IInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by I311579 on 10/20/2015.
 */
public class InputExecutor implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(InputExecutor.class);
    private List<IInput> iInputs = null;

    public InputExecutor(List<IInput> iInputList) {
        this.iInputs = iInputList;
    }

    @Override
    public void run() {
        StaticQueue.getInstance().getQueueList().forEach((elasticollectQueue) -> {
            iInputs.forEach((iInput) -> {
                try {
                    iInput.run(elasticollectQueue.inQueue);
                } catch (InterruptedException e) {
                    logger.error("input queue", e.fillInStackTrace());
                }
            });
        });
    }
}
