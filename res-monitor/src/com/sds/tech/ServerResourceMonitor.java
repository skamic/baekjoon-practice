package com.sds.tech;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.sds.tech.component.ConnectionManager;
import com.sds.tech.component.GraphManager;
import com.sds.tech.component.vo.ServerInfoVO;
import com.sds.tech.ui.ResourceMonitorUI;

public class ServerResourceMonitor {
	private ResourceMonitorUI mainUI;

	private ConnectionManager connectionManager;
	private GraphManager cpuGraphManager;
	private GraphManager memoryGraphManager;

	public ServerResourceMonitor() {
		this.mainUI = new ResourceMonitorUI(this);

		this.connectionManager = new ConnectionManager(this);
		this.cpuGraphManager = new GraphManager(this);
		this.memoryGraphManager = new GraphManager(this);
	}

	public static void main(String[] args) {
		ServerResourceMonitor srm = new ServerResourceMonitor();

		srm.openUI();
	}

	public ResourceMonitorUI getMainUI() {
		return mainUI;
	}

	public void setMainUI(ResourceMonitorUI mainUI) {
		this.mainUI = mainUI;
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

	public GraphManager getCpuGraphManager() {
		return cpuGraphManager;
	}

	public void setCpuGraphManager(GraphManager cpuGraphManager) {
		this.cpuGraphManager = cpuGraphManager;
	}

	public GraphManager getMemoryGraphManager() {
		return memoryGraphManager;
	}

	public void setMemoryGraphManager(GraphManager memoryGraphManager) {
		this.memoryGraphManager = memoryGraphManager;
	}

	private void openUI() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				ResourceMonitorUI mainUI = getMainUI();

				mainUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
				mainUI.setVisible(true);
			}
		});
	}

	public void addServer(ServerInfoVO serverInfo) {
		this.connectionManager.connect(serverInfo);
	}

	public void removeServer(String serverId) {
		this.connectionManager.disconnect(serverId);
	}
}
