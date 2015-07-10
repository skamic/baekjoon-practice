package com.sds.tech;

import org.junit.Before;
import org.junit.Test;

import com.sds.tech.component.CpuUsageCollector;
import com.sds.tech.component.ServerConnector;

public class ResourceCollectorTest {
	private ServerResourceMonitor srm;

	@Before
	public void initialize() {
		this.srm = new ServerResourceMonitor();
	}

	@Test
	public void executeCommandTest() {
		ServerConnector serverConnector = new ServerConnector();

		serverConnector.setSrm(srm);
		serverConnector.setServerName("test");
		serverConnector.setOsType("AIX");

		CpuUsageCollector cpuUsageCollector = new CpuUsageCollector(
				serverConnector);
		Thread thread = new Thread(cpuUsageCollector, "Test");

		thread.run();
	}
}
