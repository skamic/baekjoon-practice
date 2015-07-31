package com.sds.tech.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.sds.tech.ServerResourceMonitor;

public class MemoryUsageCollector implements Runnable {
	private static final String LINUX_MEM_USED_COMMAND = "free | grep ^-/+ | gawk '{print $3}'";
	private static final String LINUX_MEM_TOTAL_COMMAND = "free | grep ^Mem | gawk '{print $2}'";

	private static final String AIX_MEM_USED_COMMAND = "svmon -G | grep ^memory | awk '{print $3}'";
	private static final String AIX_MEM_TOTAL_COMMAND = "svmon -G | grep ^memory | awk '{print $2}'";

	private static final String RESOURCE_TYPE = "memory";

	private ServerResourceMonitor srm;
	private String serverName;
	private String osType;
	private Session session;
	private Channel channel;

	private int seq;

	public MemoryUsageCollector() {

	}

	public MemoryUsageCollector(ServerConnector serverConnector) {
		this.srm = serverConnector.getSrm();
		this.serverName = serverConnector.getServerName();
		this.osType = serverConnector.getOsType();
		this.session = serverConnector.getSession();
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setServerConnector(ServerResourceMonitor srm) {
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
		String memTotalCommand = null;
		String memUsedCommand = null;
		long memTotal = 0;

		seq = 1;

		if (ServerConnector.OS_AIX.equals(osType)) {
			memTotalCommand = AIX_MEM_TOTAL_COMMAND;
			memUsedCommand = AIX_MEM_USED_COMMAND;
		} else if (ServerConnector.OS_HPUX.equals(osType)) {

		} else if (ServerConnector.OS_LINUX.equals(osType)) {
			memTotalCommand = LINUX_MEM_TOTAL_COMMAND;
			memUsedCommand = LINUX_MEM_USED_COMMAND;
		} else if (ServerConnector.OS_SOLARIS.equals(osType)) {

		}

		memTotal = getMemoryTotal(memTotalCommand);
		executeCommand(memUsedCommand, memTotal);
	}

	private long getMemoryTotal(final String MEM_TOTAL_COMMAND) {
		long memTotal = 0;
		String buffer = null;
		BufferedReader br = null;

		try {
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(MEM_TOTAL_COMMAND);

			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			br = new BufferedReader(new InputStreamReader(
					channel.getInputStream()));

			channel.connect();

			while (channel.isClosed()) {
				while ((buffer = br.readLine()) != null) {
					memTotal = Long.parseLong(buffer);
				}

				if (!srm.isStarted()) {
					break;
				}
			}

			channel.disconnect();

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return memTotal;
	}

	private void executeCommand(final String MEM_USED_COMMAND,
			final long memTotal) {
		long memUsed = 0;
		String buffer = null;
		BufferedReader br = null;

		try {
			channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(MEM_USED_COMMAND);

			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			br = new BufferedReader(new InputStreamReader(
					channel.getInputStream()));

			while (srm.isStarted()) {
				channel.connect();

				while (channel.isClosed()) {
					while ((buffer = br.readLine()) != null) {
						memUsed = Long.parseLong(buffer);

						insertData(Math.round(memUsed / memTotal));
					}

					if (!srm.isStarted()) {
						break;
					}
				}

				channel.disconnect();

				Thread.sleep(5000);
			}
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

	public void insertData(int percent) {
		DataAccessManager dataAccessManager = srm.getDataAccessManager();

		dataAccessManager.insertData(seq++, serverName, RESOURCE_TYPE, percent);
	}
}
