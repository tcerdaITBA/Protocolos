package log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import connection.tcp.IterativeSocketServer;

public class RemoteLoggerServer {
	private static final int PORT = 20000;
	private static final String hostname = "localhost";

	public static void main(String[] args) throws UnknownHostException, IOException {
		IterativeSocketServer socketServer = new IterativeSocketServer(InetAddress.getByName(hostname), PORT,
				new RemoteLogHandler(new MyCodingMethod()));
		socketServer.run();
	}

}
