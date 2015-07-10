package com.sds.tech.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import com.sds.tech.ServerResourceMonitor;
import com.sds.tech.component.DataAccessManager;
import com.sds.tech.component.GraphManager;
import com.sds.tech.component.ServerConnector;
import com.sds.tech.ui.popup.AddNewServerPopup;
import com.sds.tech.ui.popup.ResultSettingsPopup;

public class ResourceMonitorUI extends JFrame {
	private static final long serialVersionUID = -8623816897416048151L;

	private ServerResourceMonitor srm;

	private AddNewServerPopup addNewServerPopup;
	private ResultSettingsPopup resultSettingsPopup;

	private JPanel serverListPanel;
	private JLabel statusBar;

	public ResourceMonitorUI(ServerResourceMonitor srm) {
		getContentPane().setName("");
		this.srm = srm;

		getContentPane().setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);
		setMinimumSize(new Dimension(600, 400));
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(2);
		borderLayout.setHgap(2);

		initUI();

		this.addNewServerPopup = new AddNewServerPopup(this);
		this.resultSettingsPopup = new ResultSettingsPopup(this);
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setSrm(ServerResourceMonitor srm) {
		this.srm = srm;
	}

	public JLabel getStatusBar() {
		return statusBar;
	}

	public void setStatusBar(JLabel statusBar) {
		this.statusBar = statusBar;
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
		final JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(
				"*.sl(Server List File)", "sl");
		fileChooser.setFileFilter(fileFilter);

		JMenuItem loadServerListMenuItem = new JMenuItem("Load Server List");
		loadServerListMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int result = fileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File serverListFile = fileChooser.getSelectedFile();
					String fileName = serverListFile.getName();
					String extension = fileName.substring(
							fileName.lastIndexOf(".") + 1, fileName.length());

					if (DataAccessManager.SERVER_LIST_FILE_EXTENSION
							.equals(extension)) {
						getSrm().loadServerList(serverListFile);
					} else {
						// TODO alert popup
					}
				}
			}
		});

		JMenuItem saveServerListMenuItem = new JMenuItem("Save Server List");
		saveServerListMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showSaveDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File serverListFile = fileChooser.getSelectedFile();

					getSrm().saveServerList(serverListFile);
				}
			}
		});

		fileMenu.add(loadServerListMenuItem);
		fileMenu.add(saveServerListMenuItem);

		JMenu optionMenu = new JMenu("Option");

		JMenuItem resultSettingsMenuItem = new JMenuItem("Result Settings");
		resultSettingsMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resultSettingsPopup.setVisible(true);
			}
		});

		optionMenu.add(resultSettingsMenuItem);

		menuBar.add(fileMenu);
		menuBar.add(optionMenu);

		setJMenuBar(menuBar);
	}

	/**
	 * 
	 */
	private void createLayout() {
		getContentPane().add(createLeftPanel(), BorderLayout.WEST);
		getContentPane().add(createRightPanel(), BorderLayout.CENTER);
		getContentPane().add(createBottomPanel(), BorderLayout.SOUTH);
	}

	/**
	 * @return
	 */
	private JPanel createLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(300, 10));
		leftPanel.setAutoscrolls(true);
		leftPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		leftPanel.setLayout(new BorderLayout(2, 2));

		JLabel lblServerList = new JLabel("Server List");
		leftPanel.add(lblServerList, BorderLayout.NORTH);

		createLeftServerListPanel();
		leftPanel.add(serverListPanel, BorderLayout.CENTER);

		return leftPanel;
	}

	/**
	 * 
	 */
	private void createLeftServerListPanel() {
		serverListPanel = new JPanel();
		serverListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		serverListPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		serverListPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		serverListPanel.setAutoscrolls(true);

		JButton btnAddServer = new JButton("Add New Server");
		btnAddServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addNewServerPopup.setVisible(true);
			}
		});
		serverListPanel.setLayout(new MigLayout("", "[left]", "[top][]"));
		serverListPanel.add(btnAddServer, "cell 0 0,alignx left,growy,wrap");

	}

	/**
	 * @return
	 */
	private JPanel createRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		rightPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		rightPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		rightPanel.setLayout(new BorderLayout(0, 0));

		rightPanel.add(createRightButtonPanel(), BorderLayout.NORTH);
		rightPanel.add(createRightGraphPanel(), BorderLayout.CENTER);

		return rightPanel;
	}

	/**
	 * 
	 * @return
	 */
	private JPanel createRightButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buttonPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		buttonPanel
				.setLayout(new MigLayout("", "[fill][fill][fill]", "[fill]"));

		JButton btnStartStop = new JButton("Start Monitoring");
		btnStartStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton) e.getSource();

				if (getSrm().isStarted()) {
					getSrm().stopMonitoring();
					source.setText("Start Monitoring");
				} else {
					getSrm().startMonitoring();
					source.setText("Stop Monitoring");
				}
			}
		});

		JButton btnGranularity = new JButton("Set Granularity");
		btnGranularity.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				displayMessage("Set Granularity");
			}
		});

		JButton btnSaveImage = new JButton("Save as Image");
		btnSaveImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getSrm().saveGraphAsImage();

				displayMessage("CPU and Memory Graphs have successfully saved.");
			}
		});

		buttonPanel.add(btnStartStop, "cell 0 0,grow");
		buttonPanel.add(btnGranularity, "cell 1 0,grow");
		buttonPanel.add(btnSaveImage, "cell 2 0,grow");

		return buttonPanel;
	}

	/**
	 * @return
	 */
	private JPanel createRightGraphPanel() {
		GraphManager cpuGraphManager = getSrm().getCpuGraphManager();
		GraphManager memoryGraphManager = getSrm().getMemoryGraphManager();
		JPanel graphPanel = new JPanel();

		graphPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		graphPanel.setLayout(new MigLayout("", "[fill]", "[fill][fill]"));

		JPanel cpuUsagePanel = createCpuUsagePanel();
		JPanel memoryUsagePanel = createMemoryUsagePanel();

		graphPanel.add(cpuUsagePanel, "cell 0 0,grow");
		graphPanel.add(memoryUsagePanel, "cell 0 1,grow");

		cpuGraphManager.setGraph(cpuUsagePanel);
		memoryGraphManager.setGraph(memoryUsagePanel);

		return graphPanel;
	}

	/**
	 * @return
	 * 
	 */
	private JPanel createCpuUsagePanel() {
		JPanel cpuUsagePanel = new JPanel();

		cpuUsagePanel.setLayout(new MigLayout("", "[fill]", "[top][fill]"));

		JLabel lblCpuUsage = new JLabel("CPU Usage (%)");
		cpuUsagePanel.add(lblCpuUsage, "cell 0 0,grow");

		JFreeChart cpuChart = ChartFactory.createTimeSeriesChart(null,
				"Elapsed Time (s)", "CPU Usage (%)", null, true, true, false);

		final XYPlot plot = cpuChart.getXYPlot();

		plot.setBackgroundPaint(new Color(0xffffe0));
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.lightGray);

		ValueAxis xaxis = plot.getDomainAxis();
		xaxis.setAutoRange(true);

		ChartPanel cpuChartPanel = new ChartPanel(cpuChart);
		cpuChartPanel.setHorizontalAxisTrace(true);
		cpuChartPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		cpuChartPanel.setLayout(new MigLayout("", "[]", "[]"));

		cpuUsagePanel.add(cpuChartPanel, "cell 0 1,grow");

		return cpuUsagePanel;
	}

	/**
	 * @return
	 * 
	 */
	private JPanel createMemoryUsagePanel() {
		JPanel memoryUsagePanel = new JPanel();

		memoryUsagePanel.setLayout(new MigLayout("", "[fill]", "[top][fill]"));

		JLabel lblMemoryUsage = new JLabel("Memory Usage (%)");
		memoryUsagePanel.add(lblMemoryUsage, "cell 0 0,grow");

		JFreeChart memoryChart = ChartFactory.createXYLineChart(null,
				"Elapsed Time (s)", "Memory Usage (%)", null,
				PlotOrientation.VERTICAL, true, true, false);
		ChartPanel memoryChartPanel = new ChartPanel(memoryChart);
		memoryChartPanel.setHorizontalAxisTrace(true);
		memoryChartPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		memoryChartPanel.setLayout(new MigLayout("", "[]", "[]"));

		memoryUsagePanel.add(memoryChartPanel, "cell 0 1,grow");

		return memoryUsagePanel;
	}

	/**
	 * @return
	 */
	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		bottomPanel.setPreferredSize(new Dimension(10, 40));
		bottomPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		bottomPanel.setLayout(new MigLayout("", "[fill]", "[15px]"));

		statusBar = new JLabel("");
		bottomPanel.add(statusBar, "cell 0 0,grow");

		return bottomPanel;
	}

	/**
	 * 
	 * @param serverName
	 * @param serverIP
	 * @param serverPort
	 * @param userId
	 * @param password
	 */
	public void addServer(ServerConnector server) {
		StringBuffer sb = new StringBuffer();
		String serverId = server.getServerId();

		sb.append(server.getServerName()).append("\n(").append(serverId)
				.append(")");

		serverListPanel.add(createServerItemPanel(sb.toString(), serverId),
				"wrap");
		serverListPanel.revalidate();
		serverListPanel.repaint();
	}

	/**
	 * @param serverName
	 * @param serverId
	 * @return
	 */
	private JPanel createServerItemPanel(String serverName, String serverId) {
		JPanel serverItemPanel = new JPanel();

		serverItemPanel.setLayout(new BoxLayout(serverItemPanel,
				BoxLayout.X_AXIS));
		serverItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		serverItemPanel.setVisible(true);

		JCheckBox chckbxServerName = new JCheckBox(serverName.toString());
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

				getSrm().removeServer(serverId);

				serverListPanel.remove(btnObj.getParent());
				serverListPanel.revalidate();
				serverListPanel.repaint();
			}
		});
		serverItemPanel.add(btnDelete);

		return serverItemPanel;
	}

	public void displayMessage(String message) {
		getStatusBar().setText(message);
	}
}
