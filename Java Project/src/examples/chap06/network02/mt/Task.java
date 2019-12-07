/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */


package examples.chap06.network02.mt;

public abstract class Task {

    public abstract void run();
    
    public void addTask(Task task) throws InterruptedException {
        TaskManager.getInstance().addConsumerTask(task);
    }
    
}
