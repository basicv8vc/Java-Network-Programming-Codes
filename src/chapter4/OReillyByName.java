package chapter4;

import java.net.*;

public class OReillyByName {

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("www.oreilly.com");
            System.out.println(address);

            address = InetAddress.getByAddress(new byte[]{(byte)202, 108, 33, 60});
            System.out.println(address.getHostName());
            System.out.println(address.getCanonicalHostName());

            InetAddress[] addresses = InetAddress.getAllByName("www.oreilly.com");
            for (InetAddress add: addresses)
                System.out.println(add);


        } catch (UnknownHostException ex) {
            System.out.println("Could not find www.oreilly.com");
        }
    }
}
