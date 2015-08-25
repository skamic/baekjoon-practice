package com.sds.tech.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.sds.tech.ServerResourceMonitor;

public class MemoryUsageCollector implements Runnable {
	private static final String LINUX_MEM_TOTAL_COMMAND = "free | grep ^Mem | gawk '{print $2}'";
	private static final String LINUX_MEM_FREE_COMMAND = "free | grep ^Mem | gawk '{print $4}'";

	private static final String AIX_MEM_TOTAL_COMMAND = "svmon -G | grep ^memory | awk '{print $2}'";
	private static final String AIX_MEM_FREE_COMMAND = "svmon -G | grep ^memory | awk '{print $4}'";

	private static final String SOLARIS_MEM_TOTAL_COMMAND = "prtconf | grep Memory | head -1 | awk 'BEGIN {FS=\" \"} {print $3}'";
	private static final String SOLARIS_MEM_FREE_COMMAND = "sar -r 1 1 | tail -1 | awk 'BEGIN {FS=\" \"} {print $2}'";

	private static final String HPUX_MEM_TOTAL_COMMAND = "swapinfo -tam | grep total | awk '{print $2}'";
	private static final String HPUX_MEM_FREE_COMMAND = "swapinfo -tam | grep total | awk '{print $4}'";

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
		String memFreeCommand = null;
		long memTotal = 0;
		float memFreeFactor = 1;

		seq = 1;

		if (ServerConnector.OS_AIX.equals(osType)) {
			memTotalCommand = AIX_MEM_TOTAL_COMMAND;
			memFreeCommand = AIX_MEM_FREE_COMMAND;
		} else if (ServerConnector.OS_HPUX.equals(osType)) {
			memTotalCommand = HPUX_MEM_TOTAL_COMMAND;
			memFreeCommand = HPUX_MEM_FREE_COMMAND;
		} else if (ServerConnector.OS_LINUX.equals(osType)) {
			memTotalCommand = LINUX_MEM_TOTAL_COMMAND;
			memFreeCommand = LINUX_MEM_FREE_COMMAND;
		} else if (ServerConnector.OS_SOLARIS.equals(osType)) {
			memTotalCommand = SOLARIS_MEM_TOTAL_COMMAND;
			memFreeCommand = SOLARIS_MEM_FREE_COMMAND;
			memFreeFactor = 8 / 1024;
		}

		memTotal = getMemoryTotal(memTotalCommand);
		executeCommand(memFreeCommand, memTotal, memFreeFactor);
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

			while ((buffer = br.readLine()) != null) {
				memTotal = Long.parseLong(buffer);
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

	private void executeCommand(final String MEM_FREE_COMMAND,
			final long memTotal, float memFreeFactor) {
		long memFree = 0;
		double percent = 0;
		String buffer = null;
		BufferedReader br = null;

		try {
			while (srm.isStarted()) {
				channel = session.openChannel("exec");
				((ChannelExec) channel).setCommand(MEM_FREE_COMMAND);

				channel.setInputStream(null);
				((ChannelExec) channel).setErrStream(System.err);

				br = new BufferedReader(new InputStreamReader(
						channel.getInputStream()));

				channel.connect();

				while ((buffer = br.readLine()) != null) {
					memFree = Long.parseLong(buffer);
					percent = Math
							.round(((memTotal - memFree * memFreeFactor) / memTotal) * 100);
					insertData((int) percent);
				}

				channel.disconnect();
				br.close();

				Thread.sleep(5000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertData(int percent) {
		DataAccessManager dataAccessManager = srm.getDataAccessManager();

		dataAccessManager.insertData(seq++, serverName, RESOURCE_TYPE, percent);
	}
}
