package task4;
import java.util.StringTokenizer;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class wuclient {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try (ZContext context = new ZContext()) {
			System.out.println("Collecting updates from weather server");
			ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
			subscriber.connect("tcp://localhost:5556");

			String filter = (args.length > 0) ? args[0] : "1001 ";
			subscriber.subscribe(filter.getBytes(ZMQ.CHARSET));

			int update_nbr;
			long total_population = 0;
			for (update_nbr = 0; update_nbr < 100; update_nbr++) {
				String string = subscriber.recvStr(0).trim();

				StringTokenizer sscanf = new StringTokenizer(string, " ");
				int zipcode = Integer.valueOf(sscanf.nextToken());
				int population = Integer.valueOf(sscanf.nextToken());

				total_population += population;
			}

			System.out.println(String.format("Average population for zipcode '%s' was %d.", filter,
					(int) (total_population / update_nbr)));
		}
	}
}
