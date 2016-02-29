package cn.huangchaosuper;

import cn.huangchaosuper.bean.Tz;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by I311579 on 10/23/2015.
 */
public class StaticQueue {

    private List<ElasticollectQueue> queueList;

    public static StaticQueue staticQueue = null;

    private StaticQueue(int size, int queueLength) {
        this.queueList = new Vector<ElasticollectQueue>(size);
        for (int i = 0; i < size; i++) {
            this.queueList.add(new ElasticollectQueue(queueLength));
        }
    }

    public static StaticQueue getInstance() {
        return staticQueue;
    }

    public static StaticQueue getInstance(int size, int queueLength) {
        if (staticQueue == null) {
            staticQueue = new StaticQueue(size, queueLength);
        }
        return staticQueue;
    }

    public class ElasticollectQueue {
        public ElasticollectQueue(int size) {
            this.inQueue = new ArrayBlockingQueue<Tz>(size);
            this.outQueue = new ArrayBlockingQueue<Tz>(size);
        }

        public ArrayBlockingQueue<Tz> inQueue = null;
        public ArrayBlockingQueue<Tz> outQueue = null;
    }

    public List<ElasticollectQueue> getQueueList() {
        return queueList;
    }
}
