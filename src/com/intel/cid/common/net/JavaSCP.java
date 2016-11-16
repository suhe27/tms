package com.intel.cid.common.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.ExecutionOS;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.intel.cid.common.constant.Constant;

public class JavaSCP {

	private static Logger logger = Logger.getLogger(JavaSCP.class);

	public Connection getConnection(ExecutionOS os) throws IOException {

		String ip = os.getIp();
		String loginName = os.getLoginName();
		String passwd = os.getPasswd();
		Connection conn = new Connection(ip);

		conn.connect();
		conn.authenticateWithPassword(loginName, passwd);
		return conn;
	}

	public Session getSSHSession(Connection conn) throws IOException {

		Session session = null;
		if (conn != null) {
			session = conn.openSession();
			session.requestPTY("vt100", 100, 10000, 640, 480, null);
		}

		return session;
	}

	public SCPClient getSCPClient(Connection conn) throws IOException {

		SCPClient client = null;
		if (conn != null) {

			client = conn.createSCPClient();
		}
		return client;
	}

	public void closeConnection(Connection conn) {

		if (conn != null) {
			conn.close();
		}

	}

	public void closeSession(Session session) {

		if (session != null) {

			session.close();
		}
	}

	public String copyFileTORemote(SCPClient client, String localFile,
			String remoteDir) throws IOException {

		client.put(localFile, remoteDir);
		return Constant.SUCCESS;
	}

	public String copyFilesTORemote(SCPClient client, String[] localFiles,
			String remoteDir) throws IOException {

		client.put(localFiles, remoteDir);
		return Constant.SUCCESS;
	}
    
	public String copyFileFromRemote(SCPClient client, String localDir,
			String remoteFile) throws IOException{
		
		client.get(remoteFile, localDir);
		return Constant.SUCCESS;
	}
	
	public String copyFilesFromRemote(SCPClient client, String localDir,
			String[] remoteFiles) throws IOException{
		
		client.get(remoteFiles, localDir);
		return Constant.SUCCESS;
	}
	
	public String executeCmds(Session session, String cmds) throws IOException {

		session.execCommand(cmds);
		return Constant.SUCCESS;
	}

	public static void main(String[] args) throws IOException {
		String user = "root";
		String pass = "tester";
		String ip = "10.239.129.149";

		ExecutionOS os = new ExecutionOS();
		os.setIp(ip);
		os.setLoginName(user);
		os.setPasswd(pass);
		JavaSCP scp = new JavaSCP();
		Connection conn = scp.getConnection(os);
		SCPClient client = scp.getSCPClient(conn);
		Session session = scp.getSSHSession(conn);

		scp.copyFileTORemote(client, "C:\\work\\itms-case-upload.xml",
						"/root/");
		//scp.executeCmds(session, "cd /root/DTF/ && screen ./dtf & ");

		/*System.out.println("Here is some information about the remote host:");
		InputStream stdout = new StreamGobbler(session.getStdout());

		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			System.out.println(line);
		}*/

		/* Show exit status, if available 0 success. (otherwise "null" fail ) */

		System.out.println("ExitCode: " + session.getExitStatus());
		scp.closeSession(session);
		scp.closeConnection(conn);
	}
}