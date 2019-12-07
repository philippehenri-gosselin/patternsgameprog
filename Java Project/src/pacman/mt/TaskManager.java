/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package pacman.mt;

import java.util.concurrent.locks.ReentrantLock;

public class TaskManager {

    private final TaskQueue consumerTaskQueue;
    
    private final TaskQueue producerTaskQueue;
    
    private final ConsumerThread[] consumerThreads;

    private final ProducerThread[] producerThreads;
    
    private final ReentrantLock lock;
    
    private boolean launched = false;
    
    private static TaskManager taskManager;
    
    public static TaskManager getInstance() {
        synchronized(TaskManager.class) {
            if (taskManager == null) {
                taskManager = new TaskManager();
            }
        }
        return taskManager;
    }

    private TaskManager() {
        this(Runtime.getRuntime().availableProcessors(),
             Runtime.getRuntime().availableProcessors());
    }

    public TaskManager(int producerCount, int consumerCount) {
        lock = new ReentrantLock(false);
        consumerTaskQueue = new TaskQueue(lock,consumerCount);
        producerTaskQueue = new TaskQueue(lock,producerCount);
        consumerThreads = new ConsumerThread[consumerCount];
        producerThreads = new ProducerThread[producerCount];
    }
        
    public void addProducerTask(Task task) throws InterruptedException {
        producerTaskQueue.add(task);
    }
    
    public void addConsumerTask(Task task) throws InterruptedException {
        consumerTaskQueue.add(task);
    }
    
    public void launch() {
        lock.lock();
        try {
            if (launched)
                return;
            launched = true;
            for (int i=0;i<producerThreads.length;i++) {
                ProducerThread producer = new ProducerThread(this);
                producerThreads[i] = producer;
                producer.start();
            }
            for (int i=0;i<consumerThreads.length;i++) {
                ConsumerThread consumer = new ConsumerThread(this);
                consumerThreads[i] = consumer;
                consumer.start();
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    public void join() throws InterruptedException {
        producerTaskQueue.waitAllDone();
        consumerTaskQueue.waitAllDone();
    }

    public void terminate() {
        lock.lock();
        try {
            if (!launched)
                return;
            launched = false;
            for (int i=0;i<producerThreads.length;i++) {
                producerThreads[i].interrupt();
            }
            for (int i=0;i<consumerThreads.length;i++) {
                consumerThreads[i].interrupt();
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    Task takeProducerTask() throws InterruptedException {
        return producerTaskQueue.take();
    }

    Task takeConsumerTask() throws InterruptedException {
        return consumerTaskQueue.take();
    }

    void notifyProducerTaskDone() throws InterruptedException {
        producerTaskQueue.notifyTaskDone();
    }
    
    void notifyConsumerTaskDone() throws InterruptedException {
        consumerTaskQueue.notifyTaskDone();
    }
}
