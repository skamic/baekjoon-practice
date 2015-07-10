package com.sds.tech.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import com.sds.tech.ServerResourceMonitor;

public class CpuUsageCollector implements ResourceCollector {
	private final String RESOURCE_TYPE = "cpu";
	private final String COMMAND = "vmstat 5 10000";

	private ServerResourceMonitor srm;
	private String serverName;
	private String osType;
	private Session session;
	private Channel channel;

	private int seq;

	public CpuUsageCollector() {
		super();
	}

	public CpuUsageCollector(ServerConnector serverConnector) {
		super();
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

	@Override
	public void executeCommand() {

		try {
			boolean isStarted = false;

			// channel = session.openChannel("exec");
			// ((ChannelExec) channel).setCommand(COMMAND);
			//
			// channel.setInputStream(null);
			// ((ChannelExec) channel).setErrStream(System.err);
			//
			// InputStream in = channel.getInputStream();
			//
			// channel.connect();

			InputStream in = new FileInputStream(new File("sample/aix.log"));

			byte[] tmp = new byte[1024];
			do {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);

					if (i < 0) {
						break;
					}

					System.out.print(new String(tmp, 0, i));
				}

				Thread.sleep(5000);

				isStarted = srm.isStarted();
				// } while (isStarted && (in.available() > 0) &&
				// !channel.isClosed());
			} while (isStarted && (in.available() > 0));

			// channel.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertData(int percent) {
		DataAccessManager dataAccessManager = getSrm().getDataAccessManager();

		dataAccessManager.insertData(seq++, serverName, RESOURCE_TYPE, percent);
	}
}
