package cn.huangchaosuper.plugin;

import cn.huangchaosuper.bean.Tz;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by I311579 on 10/19/2015.
 */
public interface IInput {
    void run(ArrayBlockingQueue<Tz> inQueue) throws InterruptedException;
}
