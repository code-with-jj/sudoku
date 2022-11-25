package es.ulpgc.seminar;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		Server server = new Server();
		server.put("solve", new SolveCommand());
		server.start();
	}
}
