package com.sds.tech;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.sds.tech.popup.AddNewServerPopup;
import com.sds.tech.ssh.ConnectionManager;
import com.sds.tech.vo.ServerInfoVO;

public class ResourceMonitor extends JFrame {
	private static final long serialVersionUID = -8623816897416048151L;

	private ConnectionManager connectionManager;
	private static AddNewServerPopup addNewServerPopup;
	private JPanel serverListPanel;

	public ResourceMonitor() {
		getContentPane().setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);
		setMinimumSize(new Dimension(600, 400));
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(2);
		borderLayout.setHgap(2);

		initUI();

		setConnectionManager(new ConnectionManager());
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			private ResourceMonitor monitor;

			@Override
			public void run() {
				monitor = new ResourceMonitor();
				monitor.setExtendedState(JFrame.MAXIMIZED_BOTH);
				monitor.setVisible(true);

				addNewServerPopup = new AddNewServerPopup(monitor);
			}
		});
	}

	private void initUI() {
		setTitle("System Resource Monitor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		createMenuBar();
		createLayout();
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");

		JMenuItem loadServerListMenuItem = new JMenuItem("Load Server List");
		loadServerListMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		JMenuItem saveServerListMenuItem = new JMenuItem("Save Server List");
		saveServerListMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		fileMenu.add(loadServerListMenuItem);
		fileMenu.add(saveServerListMenuItem);

		JMenu optionMenu = new JMenu("Option");

		JMenuItem resultSettingsMenuItem = new JMenuItem("Result Settings");
		resultSettingsMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		optionMenu.add(resultSettingsMenuItem);

		menuBar.add(fileMenu);
		menuBar.add(optionMenu);

		setJMenuBar(menuBar);
	}

	private void createLayout() {

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(300, 10));
		leftPanel.setAutoscrolls(true);
		leftPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(2, 2));

		JLabel lblServerList = new JLabel("Server List");
		leftPanel.add(lblServerList, BorderLayout.NORTH);

		serverListPanel = new JPanel();
		serverListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		serverListPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		serverListPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		serverListPanel.setAutoscrolls(true);
		leftPanel.add(serverListPanel, BorderLayout.CENTER);
		serverListPanel.setLayout(new BoxLayout(serverListPanel,
				BoxLayout.Y_AXIS));

		JButton btnAddServer = new JButton("Add New Server");
		btnAddServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addNewServerPopup.setVisible(true);
			}
		});
		serverListPanel.add(btnAddServer);

		JPanel rightPanel = new JPanel();
		rightPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		rightPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		rightPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		getContentPane().add(rightPanel, BorderLayout.CENTER);
		rightPanel.setLayout(new BorderLayout(0, 0));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		rightPanel.add(buttonPanel, BorderLayout.NORTH);
		buttonPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));

		JButton btnStartStop = new JButton("Start/Stop");

		JButton btnGranularity = new JButton("Set Granularity");

		JButton btnSaveImage = new JButton("Save as Image");
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(btnStartStop);
		buttonPanel.add(btnGranularity);
		buttonPanel.add(btnSaveImage);

		JPanel graphPanel = new JPanel();
		graphPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		rightPanel.add(graphPanel, BorderLayout.CENTER);
		graphPanel.setLayout(new GridLayout(0, 1, 2, 2));

		JPanel cpuUsagePanel = new JPanel();
		graphPanel.add(cpuUsagePanel);
		cpuUsagePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		cpuUsagePanel.setLayout(new BorderLayout(2, 2));

		JLabel lblCpuUsage = new JLabel("CPU Usage (%)");
		cpuUsagePanel.add(lblCpuUsage, BorderLayout.NORTH);

		JPanel memoryUsagePanel = new JPanel();
		graphPanel.add(memoryUsagePanel);
		memoryUsagePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		memoryUsagePanel.setLayout(new BorderLayout(2, 2));

		JLabel lblMemoryUsage = new JLabel("Memory Usage (%)");
		memoryUsagePanel.add(lblMemoryUsage, BorderLayout.NORTH);
	}

	/**
	 * 
	 * @param serverName
	 * @param serverIP
	 * @param serverPort
	 * @param userId
	 * @param password
	 * @param osType
	 */
	public void addNewServer(String serverName, String serverIP,
			int serverPort, String userId, String password, String osType) {
		StringBuffer sb = new StringBuffer();
		String serverId = sb.append(serverIP).append(":").append(serverPort)
				.toString();

		sb.delete(0, sb.length());
		sb.append(serverName).append("\n(").append(serverId).append(")");

		JPanel serverItemPanel = new JPanel();
		serverItemPanel.setLayout(new BoxLayout(serverItemPanel,
				BoxLayout.X_AXIS));
		serverItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		serverItemPanel.setVisible(true);

		JCheckBox chckbxServerName = new JCheckBox(sb.toString());
		chckbxServerName.setAlignmentX(Component.CENTER_ALIGNMENT);
		chckbxServerName.setSelected(true);
		chckbxServerName.setVisible(true);
		serverItemPanel.add(chckbxServerName);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnDelete.setVisible(true);
		btnDelete.setToolTipText(serverId);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btnObj = (JButton) e.getSource();
				String serverId = btnObj.getToolTipText();
				serverListPanel.remove(btnObj.getParent());
				serverListPanel.revalidate();
				serverListPanel.repaint();
			}
		});
		serverItemPanel.add(btnDelete);

		serverListPanel.add(serverItemPanel);
		serverListPanel.revalidate();
		serverListPanel.repaint();

		ServerInfoVO newServer = new ServerInfoVO(serverName, serverIP,
				serverPort, userId, password, osType);

		getConnectionManager().connect(newServer);
	}

	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}

	public void setConnectionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}
}
