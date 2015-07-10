package com.sds.tech.component;

import com.jcraft.jsch.Session;
import com.sds.tech.ServerResourceMonitor;

public class MemoryUsageCollector implements ResourceCollector {
	private ServerResourceMonitor srm;
	private String serverName;
	private Session session;

	public MemoryUsageCollector(ServerConnector serverConnector) {
		this.srm = serverConnector.getSrm();
		this.serverName = serverConnector.getServerName();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void executeCommand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertData(int percent) {
		// TODO Auto-generated method stub

	}

}
