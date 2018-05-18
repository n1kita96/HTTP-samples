package first_sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author Mykyta Shvets
 * @since 11.01.2018.
 */
public class SimpleExecutorService {
    private final ThreadGroup group = new ThreadGroup("");
    private final Collection<Thread> workersPool = new ArrayList<>();
    private final BlockingQueue<Callable> taskQueue;
   
    private class Worker implements Runnable{
        @Override
        public void run() {
            while (true){
                try {
                    Callable nextTask = taskQueue.take();
                    nextTask.call();
                } catch (InterruptedException e) {
                    break; //thread interrupted so kill thread
                } catch (Exception e) {
                    e.printStackTrace(); //task broken
                }
            }
        }
    }

    public SimpleExecutorService(int threadCount, final BlockingQueue<Callable> taskQueue){
        this.taskQueue = taskQueue;
        for (int i = 0; i < threadCount; i++) {
            Thread worker = new Thread(group, new Worker());
            worker.start();
            workersPool.add(worker);
        }
    }

    public <T> void submit(Callable<T> task) throws InterruptedException{
        taskQueue.put(task);
    }

    public void shutdown(){
        group.interrupt(); //Listener pattern here
    }
}
