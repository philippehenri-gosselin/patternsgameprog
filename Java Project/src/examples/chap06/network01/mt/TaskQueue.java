/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network01.mt;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class TaskQueue {
    
    private final ReentrantLock lock;
    
    private final Task[] tasks;
    
    private final Condition queueIncreasing;

    private final Condition queueDecreasing;
    
    private final Condition taskDone;
    
    private int index;
    
    private int size;
    
    private int runningTasks;
    
    public TaskQueue(ReentrantLock lock,int capacity) {
        this.lock = lock;
        tasks = new Task[capacity];
        queueIncreasing = lock.newCondition();
        queueDecreasing = lock.newCondition();
        taskDone = lock.newCondition();
    }
        
    public void add(Task task) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (size == tasks.length)
                queueDecreasing.await();
            tasks[index] = task;
            index ++;
            if (index >= tasks.length)
                index = 0;
            size ++;
            queueIncreasing.signal();
        } finally {
            lock.unlock();
        }        
    }
    
    public Task take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (size == 0)
                queueIncreasing.await();
            index --;
            if (index < 0) {
                index = tasks.length - 1;
            }
            Task task = tasks[index];
            size --;
            runningTasks ++;
            queueDecreasing.signal();
            return task;
        } finally {
            lock.unlock();
        }    
    }

    public void notifyTaskDone() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            runningTasks --;
            taskDone.signal();
        } finally {
            lock.unlock();
        }            
    }    
    
    public void waitAllDone() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (size != 0)
                queueDecreasing.await();
            while (runningTasks != 0)
                taskDone.await();
        } finally {
            lock.unlock();
        }            
    }

}
