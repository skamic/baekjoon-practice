package com.sds.tech.component;

import com.sds.tech.ServerResourceMonitor;

public class GraphManager {
	private ServerResourceMonitor srm;

	public GraphManager(ServerResourceMonitor srm) {
		this.srm = srm;
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setSrm(ServerResourceMonitor srm) {
		this.srm = srm;
	}
}
