package com.sds.tech;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.sds.tech.component.ConnectionManager;
import com.sds.tech.ui.ResourceMonitorUI;

public class ServerResourceMonitor {
	private ResourceMonitorUI mainUI;
	private ConnectionManager connectionManager;

	public ServerResourceMonitor() {
		this.mainUI = new ResourceMonitorUI(this);
		this.connectionManager = new ConnectionManager(this);
	}

	public static void main(String[] args) {
		ServerResourceMonitor srm = new ServerResourceMonitor();

		srm.openUI();
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
}
