package task3;

import java.util.ArrayList;
import java.util.List;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class hwserver {
	public static void main(String[] args) throws Exception {
		try (ZContext context = new ZContext()) {
			// Socket to talk to clients
			ZMQ.Socket socket = context.createSocket(SocketType.REP);
			socket.bind("tcp://*:5554");
			System.out.println("Server started...");

			while (!Thread.currentThread().isInterrupted()) {
				byte[] reply = socket.recv(0);
				String replyStr = new String(reply, ZMQ.CHARSET);
				System.out.println("Received " + ": [" + replyStr + "]");

				Thread.sleep(1000); // Do some 'work'

				String message = reverse(replyStr);

				System.out.println("Sending " + ": [" + message + "]...");
				socket.send(message.getBytes(ZMQ.CHARSET), 0);
				System.out.println("Sent.");
			}
		}
	}

	private static String reverse(String input) {
		return new StringBuilder(input).reverse().toString();
	}
}