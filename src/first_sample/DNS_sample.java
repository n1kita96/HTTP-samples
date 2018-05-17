package first_sample;

import sun.net.spi.nameservice.NameService;
import sun.net.spi.nameservice.dns.DNSNameService;

import java.net.InetAddress;

/**
 * Created by N1kita on 10.01.2018.
 */
public class DNS_sample {
    public static void main(String[] args) throws Exception {
        NameService dns = new DNSNameService();
        InetAddress[] result = dns.lookupAllHostAddr("google.com");

        for (InetAddress address : result) {
            System.out.println(address);
            System.out.println(" " + dns.getHostByAddr(address.getAddress()));
        }

    }
}
