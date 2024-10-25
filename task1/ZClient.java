package task1;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ZClient {

    public static void main(String[] args) {

        try (ZContext context = new ZContext()) {
            System.out.println("Connecting to hello world server...");

            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://localhost:5555");

            String message = "Hello";
            System.out.println("Sending Hello...");
            socket.send(message);

            String reply = socket.recvStr();
            System.out.println("Received reply: \"" + reply + "\"");

            socket.close();
            context.close();
        }
    }
}
