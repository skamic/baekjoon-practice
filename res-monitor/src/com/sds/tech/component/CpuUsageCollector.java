package com.sds.tech.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.sds.tech.ServerResourceMonitor;

public class CpuUsageCollector implements Runnable {
	private static final String COMMAND = "vmstat 5 10000";

	private static final String RESOURCE_TYPE = "cpu";

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
		String buffer = null;
		BufferedReader br = null;

		try {
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(COMMAND);

			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			br = new BufferedReader(new InputStreamReader(
					channel.getInputStream()));

			channel.connect();

			while (true) {
				while ((buffer = br.readLine()) != null) {
					buffer = buffer.trim();
					char firstChar = buffer.charAt(0);

					if (firstChar < '0' || firstChar > '9') {
						continue;
					}

					insertData(buffer);
				}

				if (!srm.isStarted() || channel.isClosed()) {
					break;
				}

				Thread.sleep(1000);
			}

			channel.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void insertData(String result) {
		int percent = getCpuUsage(result);
		DataAccessManager dataAccessManager = getSrm().getDataAccessManager();

		dataAccessManager.insertData(seq++, serverName, RESOURCE_TYPE, percent);
	}

	/**
	 * @param result
	 * @return
	 */
	private int getCpuUsage(String result) {
		int percent = 100;
		StringTokenizer tokenizer = new StringTokenizer(result, " ");
		String[] token = new String[tokenizer.countTokens()];
		int index = 0;

		while (tokenizer.hasMoreTokens()) {
			token[index++] = tokenizer.nextToken();
		}

		if (ServerConnector.OS_AIX.equals(osType)) {
			percent -= Integer.parseInt(token[token.length - 2].trim());
		} else if (ServerConnector.OS_HPUX.equals(osType)) {
			percent -= Integer.parseInt(token[token.length - 1].trim());
		} else if (ServerConnector.OS_LINUX.equals(osType)) {
			percent -= Integer.parseInt(token[token.length - 3].trim());
		} else if (ServerConnector.OS_SOLARIS.equals(osType)) {
			percent -= Integer.parseInt(token[token.length - 1].trim());
		}

		return percent;
	}
}
