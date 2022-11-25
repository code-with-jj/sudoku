package es.ulpgc.seminar;

import spark.Request;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

public class Server {
	private Map<String, Command> commands = new HashMap<>();

	public void put(String name, Command command) {
		commands.put(name, command);
	}

	public void start() {
		Spark.port(80);
		Spark.get("solver", (q, r) -> prettyPrint(commands.get("solve").execute(parametersIn(q))));
	}

	private String prettyPrint(String sudoku) {
		return sudoku.replace("\n", "<br>");
	}

	private Command.Parameters parametersIn(Request query) {
		return parameter -> query.queryParams(parameter);
	}


}
