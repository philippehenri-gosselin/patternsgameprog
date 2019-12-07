/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network01.mt;

class ConsumerThread extends Thread {

    private final TaskManager manager;
    
    public ConsumerThread(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = manager.takeConsumerTask();
                task.run();
                manager.notifyConsumerTaskDone();
            }
        } catch (InterruptedException ex) {
        }
    }

}
