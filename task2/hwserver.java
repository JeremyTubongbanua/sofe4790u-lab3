package task2;

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

				int input = Integer.parseInt(replyStr);

				List<Integer> primes = getPrimesBefore(input);	

				String message = "";
				for (int i = 0; i < primes.size(); i++) {
					if(i > 0) {
						message += ", ";
					}
					message += primes.get(i);
				}

				System.out.println("Sending " + ": [" + message + "]...");
				socket.send(message.getBytes(ZMQ.CHARSET), 0);
				System.out.println("Sent.");
			}
		}
	}

	private static boolean isPrime(int input0) {
		if (input0 < 2) {
			return false;
		}
		for (int i = 2; i < input0; i++) {
			if (input0 % i == 0) {
				return false;
			}
		}
		return true;
	}

	private static List<Integer> getPrimesBefore(int input) {
		List<Integer> primes = new ArrayList<Integer>();
		for (int i = 2; i < input; i++) {
			if (isPrime(i)) {
				primes.add(i);
			}
		}
		return primes;
	}
}