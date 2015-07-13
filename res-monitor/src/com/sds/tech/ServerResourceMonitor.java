package com.sds.tech;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JFrame;

import com.sds.tech.component.DataAccessManager;
import com.sds.tech.component.GraphManager;
import com.sds.tech.component.ServerConnector;
import com.sds.tech.component.ServerManager;
import com.sds.tech.ui.ResourceMonitorUI;

public class ServerResourceMonitor {
	private ResourceMonitorUI mainUI;

	private DataAccessManager dataAccessManager;
	private ServerManager serverManager;
	private GraphManager cpuGraphManager;
	private GraphManager memoryGraphManager;

	private boolean isStarted = false;

	public ServerResourceMonitor() {
		this.dataAccessManager = new DataAccessManager(this);
		this.serverManager = new ServerManager(this);
		this.cpuGraphManager = new GraphManager(this, "cpu");
		this.memoryGraphManager = new GraphManager(this, "memory");

		this.mainUI = new ResourceMonitorUI(this);
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

	public ServerManager getServerManager() {
		return serverManager;
	}

	public void setServerManager(ServerManager connectionManager) {
		this.serverManager = connectionManager;
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

	public DataAccessManager getDataAccessManager() {
		return dataAccessManager;
	}

	public void setDataAccessManager(DataAccessManager dataLoggingManager) {
		this.dataAccessManager = dataLoggingManager;
	}

	public boolean isStarted() {
		return isStarted;
	}

	private void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
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

	public void addServer(ServerConnector serverConnector) {
		this.serverManager.addServer(serverConnector);
		this.mainUI.addServer(serverConnector);
	}

	public void removeServer(String serverId) {
		this.serverManager.removeServer(serverId);
	}

	public void loadServerList(File serverListFile) {
		BufferedReader br = null;

		try {
			String buffer = null;

			this.serverManager.initServerList();
			br = new BufferedReader(new FileReader(serverListFile));

			while ((buffer = br.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(buffer, ",");

				if (tokenizer.countTokens() == 5) {
					ServerConnector serverConnector = new ServerConnector(
							tokenizer.nextToken(), tokenizer.nextToken(),
							tokenizer.nextToken(), tokenizer.nextToken(),
							tokenizer.nextToken());

					addServer(serverConnector);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveServerList(File serverListFile) {
		this.serverManager.saveServerList(serverListFile);
	}

	public void startMonitoring() {
		try {
			// this.dataAccessManager.startMonitoring();

			Thread cpuGraphManagerThread = new Thread(this.cpuGraphManager,
					"CPU GraphManager");
			Thread memoryGraphManagerThread = new Thread(
					this.memoryGraphManager, "Memory GraphManager");

			cpuGraphManagerThread.start();
			memoryGraphManagerThread.start();

			setStarted(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopMonitoring() {
		setStarted(false);

		// this.serverManager.stopMonitoring();
	}

	public void saveResultSettings(String resultName, String resultDirectoryPath) {
		this.dataAccessManager.saveResultSettings(resultName,
				resultDirectoryPath);
	}

	public void saveGraphAsImage() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String imageName = formatter.format(new Date());

		this.dataAccessManager.createResultFolder();
		this.cpuGraphManager.saveChartAsImage(imageName);
		this.memoryGraphManager.saveChartAsImage(imageName);
	}

}
