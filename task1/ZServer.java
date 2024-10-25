package task1;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ZServer {

    public static void main(String[] args) throws Exception {
        try (ZContext context = new ZContext()) {
            System.out.println("Creating ZMQ.Socket...");
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            System.out.println("Binding to tcp://*:5555...");
            socket.bind("tcp://*:5555");

            int i = 0;

            while (i < 10) {
                String request = socket.recvStr();
                System.out.println("Received request: \"" + request + "\"");
                Thread.sleep(100);
                String reply = "Hello from server";
                socket.send(reply);
                System.out.println("Sent reply: \"" + reply + "\"");
                i++;
            }

            socket.close();
            context.close();
        }
    }
}
