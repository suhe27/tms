package com.intel.cid.common.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class Client {

	private String remoteIp;

	private int remotePort;

	private Socket outSocket;

	private static Logger logger = Logger.getLogger(Client.class);

	public Client(String remoteIp, int remotePort) {

		this.remoteIp = remoteIp;
		this.remotePort = remotePort;
	}

	public void sendCommand(String cmds) throws Exception {

		outSocket = new Socket(remoteIp, remotePort);
		OutputStream fos = outSocket.getOutputStream();
		fos.write(cmds.getBytes());
		fos.close();
		outSocket.close();
		logger.info("cmd:" + cmds);
	}

	public void sendFile(File file) {
		try {
			outSocket = new Socket(remoteIp, remotePort);
			OutputStream fos = outSocket.getOutputStream();

			String fileName = file.getName();
			fos.write(fileName.getBytes());
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[4096];
			int sum = 0;
			while ((sum = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, sum);
			}

			
			
			
			fis.close();
			fos.close();
			outSocket.close();

		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		File file = new File("C:\\test\\DB\\2013-01-27_23_30_02_backup.sql");

		new Client("10.239.128.72", 9008).sendFile(file);

	}

}
