package first_sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by N1kita on 09.01.2018.
 */
public class HttpUtils {

    public static byte[] readRequestFully(InputStream in) throws IOException {
        byte[] buff = new byte[8192]; //for every request spends 8kb. Optimization: reuse this buffer
        int headerLen = 0;
        while (true) {
            int count = in.read(buff, headerLen, buff.length - headerLen);
            if (count < 0) {
                throw new RuntimeException("Incoming connection closed but not full HTTP header.");
            } else {
                headerLen += count;
                if (isRequestEnd(buff, headerLen)) {
                    return Arrays.copyOfRange(buff, 0, headerLen);
                }
                if (headerLen == buff.length) {
                    throw new RuntimeException("Too big HTTP header! More than " + buff.length);
                }
            }
        }
    }

    /**
     * @param request - buffer with incoming http request
     * @param length  - length of actual data
     * @return true if last 4 bytes is ???? (signal if header finished)
     */
    public static boolean isRequestEnd(byte[] request, int length) {
        if (length < 4) {
            return false;
        }
        return request[length - 4] == '\r' &&
                request[length - 3] == '\n' &&
                request[length - 2] == '\r' &&
                request[length - 1] == '\n';
    }
}
