package cn.huangchaosuper.plugin.input;

import cn.huangchaosuper.bean.Tz;
import cn.huangchaosuper.plugin.IInput;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by I311579 on 10/21/2015.
 */
public class File implements IInput {
    private static Logger logger = LoggerFactory.getLogger(File.class);
    private String path;

    public File(String path) {
        this.path = path;
    }

    @Override
    public void run(ArrayBlockingQueue<Tz> inQueue) {
        logger.debug(Thread.currentThread().getName());
    }
}
