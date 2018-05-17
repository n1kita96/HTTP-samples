package first_sample;

import sun.nio.cs.US_ASCII;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static java.nio.charset.StandardCharsets.US_ASCII;


/**
 * @author Mykyta Shvets
 * @since 09.01.2018.
 */

public class Http_01 {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);

        while (true){
            System.out.println("Wait for TCP-connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Get one!");
            try(OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream()){
                //read request
                byte[] request = HttpUtils.readRequestFully(in);
                System.out.println("-----------");
                System.out.print(new String(request, US_ASCII));
                System.out.println("-----------");
                //write response
                byte[] response = new String("Hello!").getBytes(US_ASCII);// new Date().toString().getBytes(US_ASCII);
                System.out.println("********");
                System.out.println(response);
                System.out.println("********");
                out.write(response);
            } finally {
                    socket.close();
            }
        }
    }

}
