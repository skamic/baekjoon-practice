package com.sds.tech.component;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sds.tech.ServerResourceMonitor;

public class MemoryUsageCollector implements Runnable {
	private final String RESOURCE_TYPE = "memory";

	private ServerResourceMonitor srm;
	private String serverName;
	private String osType;
	private Session session;
	private Channel channel;

	private int seq;
	private long memTotal;

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
		seq = 1;

		if (ServerConnector.OS_AIX.equals(osType)) {
			executeAixCommand();
		} else if (ServerConnector.OS_HPUX.equals(osType)) {
			executeHpuxCommand();
		} else if (ServerConnector.OS_LINUX.equals(osType)) {
			executeLinuxCommand();
		} else if (ServerConnector.OS_SOLARIS.equals(osType)) {
			executeSolarisCommand();
		}
	}
	
	private void executeAixCommand() {
		final String MEM_TOTAL_COMMAND = "svmon -G | grep ^memory | awk '{print $2}'";
		final String MEM_USED_COMMAND = "svmon -G | grep ^memory | awk '{print $3}'";
	}

	private void executeHpuxCommand() {

	}
	
	private void executeLinuxCommand() {
		final String MEM_TOTAL_COMMAND = "free | grep ^Mem | gawk '{print $2}'";
		final String MEM_USED_COMMAND = "free | grep ^-/+ | gawk '{print $3}'";
		long memTotal = 0;
		long memUsed = 0;

		try {
			channel = session.openChannel("exec");
			((ChannelExec) channel)
					.setCommand(MEM_TOTAL_COMMAND);

			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();
			// InputStream in = new FileInputStream(new File("sample/aix.log"));

			channel.connect();

			byte[] tmp = new byte[1024];
			do {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);

					if (i < 0) {
						break;
					}

					memTotal = Long.parseLong(new String(tmp, 0, i));
				}
				// } while (srm.isStarted() && (in.available() > 0) &&
				// channel.isClosed());
			} while (srm.isStarted() && (in.available() > 0));

			// channel.disconnect();
			in.close();

			channel = session.openChannel("exec");
			((ChannelExec) channel)
					.setCommand(MEM_USED_COMMAND);

			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			in = channel.getInputStream();
			// in = new FileInputStream(new File("sample/aix.log"));

			do {
				channel.connect();

				tmp = new byte[1024];
				do {
					while (in.available() > 0) {
						int i = in.read(tmp, 0, 1024);

						if (i < 0) {
							break;
						}

						memUsed = Long.parseLong(new String(tmp, 0, i));

						insertData(Math.round(memUsed / memTotal));
					}
					// } while (srm.isStarted() && (in.available() > 0) &&
					// channel.isClosed());
				} while (srm.isStarted() && (in.available() > 0));

				// channel.disconnect();
			} while (srm.isStarted());
			in.close();
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void executeSolarisCommand() {
		// TODO Auto-generated method stub

	}

	public void insertData(int percent) {

	}
}
