package task4;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class wuserver {

	public static void main(String[] args) throws Exception {
		try (ZContext context = new ZContext()) {
			ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
			publisher.bind("tcp://*:5556");
			publisher.bind("ipc://weather");

			Random srandom = new Random(System.currentTimeMillis());
			while (!Thread.currentThread().isInterrupted()) {
				int zipcode, population;
				zipcode = 1000 + srandom.nextInt(1000); // 1000-1999 zipcodes
				population = srandom.nextInt(1000); // 0-99 people

				String update = String.format("%04d %04d", zipcode, population);
				System.out.println(update);
				publisher.send(update, 0);
			}
		}
	}
}
