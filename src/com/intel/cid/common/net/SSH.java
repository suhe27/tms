package com.intel.cid.common.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSH {

	private static Logger logger = Logger.getLogger(SSH.class);

	public static Connection getConnection(String hostname, String userName,
			String password) throws Exception {

		Connection connection = new Connection(hostname);

		connection.connect();
		boolean isAuth = connection
				.authenticateWithPassword(userName, password);
		if (!isAuth) {
			throw new IOException("auth faild");
		}

		return connection;
	}

	public static void sendFile(Connection conn, String localFile,
			String remoteDir) throws Exception {
		SCPClient scpClient = conn.createSCPClient();
		scpClient.put(localFile, remoteDir);
	}

	public static void closeConnection(Connection conn) throws Exception {

		conn.close();

	}

	public static void executeCommand(Connection conn, String command)
			throws Exception {

		StringBuffer buffer = new StringBuffer();

		String[] commands = command.split(";");
		for (int i = 0; i < commands.length; i++) {
			buffer.append(commands[i]);
			if (i != commands.length - 1) {
				buffer.append("&&");
			}
		}
		Session session = conn.openSession();
		session.execCommand(buffer.toString());
		System.out.println("here is some info about the remote host:");
		InputStream stout = new StreamGobbler(session.getStdout());
		BufferedReader br = new BufferedReader(new InputStreamReader(stout));
		while (true) {
			String line = br.readLine();
			if (line == null) {
				break;
			}
			logger.info(line);
			System.out.println(line);
		}
		logger.info("ExitCode:" + session.getExitStatus());
		System.out.println("ExitCode:" + session.getExitStatus());

	}

	public static void main(String[] args) throws Exception {
//		String hostname = "stv-server";
//		String username = "root";
//		String password = "tester";
		String hostname = "itms-server";
		String username = "tanghaix";
		String password = "test123";
		Connection conn = getConnection(hostname, username, password);
		sendFile(conn, "C:\\test\\2012-12-17-14-56-40.xml", "/home/tanghaix");
		executeCommand(conn, "ls -l;date;pwd");
		closeConnection(conn);
	}

}
