/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network03.mt;

class ProducerThread extends Thread {
    
    protected final TaskManager manager;

    public ProducerThread(TaskManager manager) {
        this.manager = manager;
    }

    public void run() {
        try {
            while (true) {
                Task task = manager.takeProducerTask();
                task.run();
                manager.notifyProducerTaskDone();
            }
        } catch (InterruptedException ex) {
        }
    }

}
