package first_sample;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Mykyta Shvets
 * @since 11.01.2018.
 *
 * ADD: Multi-threading with
 *      <br>- 'Thread Pool' (ThreadPoolExecutor) of 'Workers'-threads
 *      <br>- 'Blocked Queue' to 'Thread Pool' of accepted sockets
 *      <br>- main() - is 'Master'-thread
 */
public class Http_02 {
    public static void main(String[] args) throws IOException {
        //TODO: read about ThreadPoolExecutor, ScheduleExecutorService, ForkJoinPool (work stealing)
        //TODO: read about Blocking, submit returns Future()

        ExecutorService threadPool = new ThreadPoolExecutor(
                4, 64,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(256));
        ServerSocket serverSocket = new ServerSocket(8080, 256);

        while (true) {
            final Socket socket = serverSocket.accept();
            threadPool.submit(new HttpHandler(socket));
        }
    }
}
