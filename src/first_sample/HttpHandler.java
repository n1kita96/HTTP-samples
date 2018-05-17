package first_sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @author Mykyta Shvets
 * @since 2018-01-21.
 */
public class HttpHandler implements Callable<Void> {

    public final Socket socket;

    public HttpHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Void call() throws IOException {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            //READ request
            byte[] request = HttpUtils.readRequestFully(in);
            System.out.println(new String(request, "ISO-8859-1"));
            //WRITE response
            byte[] response = new Date().toString().getBytes();
            System.out.println(new String(response, "ISO-8859-1"));
            out.write(response);
        } finally {
            socket.close();
        }
        return null;
    }
}
