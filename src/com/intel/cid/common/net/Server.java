package com.intel.cid.common.net;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends  Thread {

	private ServerSocket serverSocket;

	private String desDir;

	private Socket socket;

	private int  port ;

	public Server(int port, String desDir) {

		this.port = port;
		this.desDir = desDir;

		try {
			serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void run() {
		while(true){
			
			try {
				socket = serverSocket.accept();

				if (socket != null) {

					new FileReceiver(socket).start();
				}

			} catch (IOException e) {

				e.printStackTrace();
			}

		}
	}

	class FileReceiver extends Thread {
		private Socket tSocket;

		public FileReceiver(Socket socket) {

			tSocket = socket;

		}

		public void run() {
			try {
				InputStream inp = tSocket.getInputStream();
				DataInputStream dis = 	new DataInputStream(inp);
				byte[] buf = new byte[4096];

				File desDirs = new File(desDir);
				if (!desDirs.exists()) {
					desDirs.mkdir();
				}			
				byte[] names = new byte[30];
				dis.read(names);
				String fileName = new String(names, "UTF-8");
				File desFile = new File(desDir+File.separator+fileName);
				FileOutputStream fos = new FileOutputStream(desFile);
				int sum = dis.read(buf, 30, buf.length-30);
				fos.write(buf, 30, sum);
				while((sum = dis.read(buf))!=-1){
				fos.write(buf, 0, sum);
				}
				
				dis.close();
				fos.close();
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
		}

	}

	
	public static void main(String[] args) {
		new Server(9008, "/var/www/DBBackup").start();
		
		
	}
}
