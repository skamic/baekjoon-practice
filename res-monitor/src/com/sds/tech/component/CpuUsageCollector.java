package com.sds.tech.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.StringTokenizer;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import com.sds.tech.ServerResourceMonitor;

public class CpuUsageCollector implements Runnable {
	private final String RESOURCE_TYPE = "cpu";
	private final String COMMAND = "vmstat 5 10000";

	private ServerResourceMonitor srm;
	private String serverName;
	private String osType;
	private Session session;
	private Channel channel;

	private int seq;

	public CpuUsageCollector() {

	}

	public CpuUsageCollector(ServerConnector serverConnector) {
		this.srm = serverConnector.getSrm();
		this.serverName = serverConnector.getServerName();
		this.osType = serverConnector.getOsType();
		this.session = serverConnector.getSession();
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setSrm(ServerResourceMonitor srm) {
		this.srm = srm;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void run() {
		seq = 1;

		executeCommand();
	}

	public void executeCommand() {

		try {
			// channel = session.openChannel("exec");
			// ((ChannelExec) channel).setCommand(COMMAND);
			//
			// channel.setInputStream(null);
			// ((ChannelExec) channel).setErrStream(System.err);
			//
			// InputStream in = channel.getInputStream();
			InputStream in = new FileInputStream(new File("sample/aix.log"));
			//
			// channel.connect();

			byte[] tmp = new byte[1024];
			do {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);

					if (i < 0) {
						break;
					}

					insertData(new String(tmp, 0, i));
				}

				Thread.sleep(5000);

				// } while (srm.isStarted() && (in.available() > 0) &&
				// !channel.isClosed());
			} while (srm.isStarted() && (in.available() > 0));

			// channel.disconnect();

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertData(String result) {
		DataAccessManager dataAccessManager = getSrm().getDataAccessManager();
		int percent = 100;
		StringTokenizer tokenizer = new StringTokenizer(result, " ");
		String[] token = new String[tokenizer.countTokens()];
		int index = 0;

		while (tokenizer.hasMoreTokens()) {
			token[index++] = tokenizer.nextToken();
		}

		if (ServerConnector.OS_AIX.equals(osType)) {
			percent -= Integer.parseInt(token[token.length - 2]);
		} else if (ServerConnector.OS_HPUX.equals(osType)) {
			percent -= Integer.parseInt(token[token.length - 1]);
		} else if (ServerConnector.OS_LINUX.equals(osType)) {
			percent -= Integer.parseInt(token[token.length - 3]);
		} else if (ServerConnector.OS_SOLARIS.equals(osType)) {
			percent -= Integer.parseInt(token[token.length - 1]);
		}

		dataAccessManager.insertData(seq++, serverName, RESOURCE_TYPE, percent);
	}
}
