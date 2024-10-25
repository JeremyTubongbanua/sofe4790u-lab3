package task3;
import java.util.Scanner;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class hwclient {

	public static void main(String[] args) {
		try (ZContext context = new ZContext()) {
			// Socket to talk to server
			System.out.println("Connecting to hello world server");

			ZMQ.Socket socket = context.createSocket(SocketType.REQ);
			socket.connect("tcp://localhost:5554");
			System.out.println("Connected to server...");

			Scanner scanner = new Scanner(System.in);
			while (true) {
				System.out.println("Enter text: ");
				String input = scanner.nextLine();
				socket.send(input.getBytes(ZMQ.CHARSET), 0);

				byte[] reply = socket.recv(0);
				String replyStr = new String(reply, ZMQ.CHARSET);
				System.out.println("Received " + ": [" + replyStr + "]");
			}
		}
	}
}
