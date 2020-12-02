package com.lib.showfield.other.tinyTask.onebyone;

import com.lib.showfield.other.tinyTask.Task;
import com.lib.showfield.other.tinyTask.TinyTaskExecutor;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinySyncExecutor {

    private volatile static TinySyncExecutor sTinySyncExecutor;

    //store incoming task, waiting to put into ArrayBlockingQueue;
    public ArrayDeque<BaseSyncTask> pendingQueue = new ArrayDeque<>();
    public BaseSyncTask currentTask;

    private final AtomicInteger count = new AtomicInteger(1);

    public static TinySyncExecutor getInstance() {
        if (sTinySyncExecutor == null) {
            synchronized (TinySyncExecutor.class) {
                sTinySyncExecutor = new TinySyncExecutor();
            }
        }
        return sTinySyncExecutor;
    }

    private void coreExecute() {
        currentTask = pendingQueue.poll();
        if (currentTask != null) {
            TinyTaskExecutor.execute(new Task() {
                @Override
                public Object doInBackground() {
                    return null;
                }

                @Override
                public void onSuccess(Object o) {
                    currentTask.doTask();
                }

                @Override
                public void onFail(Throwable throwable) {

                }
            });
        }
    }

    public void enqueue(final BaseSyncTask task) {
        task.setId(count.getAndIncrement());
        pendingQueue.offer(task);//the ArrayDeque should not be blocked when operate offer
        if (currentTask == null) {
            coreExecute();
        }
    }

    public void finish() {
        coreExecute();
    }

    public void remove() {
        pendingQueue.clear();
    }

    public void shuntDown() {
        TinyTaskExecutor.shuntDown();
    }
}
