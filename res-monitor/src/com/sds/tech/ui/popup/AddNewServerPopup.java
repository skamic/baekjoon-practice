package com.sds.tech.ui.popup;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.sds.tech.ui.ResourceMonitorUI;

public class AddNewServerPopup extends JFrame {
	private static final long serialVersionUID = 8506499428711180729L;

	private ResourceMonitorUI parent;

	private JTextField serverName;
	private JTextField serverIP;
	private JTextField serverPort;
	private JTextField userId;
	private JTextField password;
	private final ButtonGroup osTypeButtonGroup = new ButtonGroup();
	private String osType;

	public AddNewServerPopup(ResourceMonitorUI parent) {
		this.parent = parent;
		initUI();
	}

	public void initUI() {
		setMaximumSize(new Dimension(400, 350));
		setMinimumSize(new Dimension(400, 350));
		setType(Type.POPUP);
		setSize(new Dimension(400, 300));
		setTitle("Add New Server");

		JPanel serverInformationPanel = new JPanel();
		getContentPane().add(serverInformationPanel, BorderLayout.CENTER);

		serverIP = new JTextField();
		serverIP.setColumns(15);

		JLabel lblPort = new JLabel("Port");

		JLabel lblIP = new JLabel("IP");

		serverPort = new JTextField();
		serverPort.setColumns(5);
		serverPort.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				char key = e.getKeyChar();

				if (!(Character.isDigit(key) || key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_DELETE)) {
					e.consume();
				}

				int currentLen = ((JTextField) e.getSource()).getText()
						.length();

				if (currentLen >= 5) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		JLabel lblId = new JLabel("ID");

		userId = new JTextField();
		userId.setColumns(15);

		JLabel lblPassword = new JLabel("Password");

		password = new JPasswordField();
		password.setColumns(15);

		JRadioButton rdbtnAix = new JRadioButton("AIX");
		rdbtnAix.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setOsType("AIX");
			}
		});
		osTypeButtonGroup.add(rdbtnAix);

		JRadioButton rdbtnHpux = new JRadioButton("HP-UX");
		rdbtnHpux.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setOsType("HP-UX");
			}
		});
		osTypeButtonGroup.add(rdbtnHpux);

		JRadioButton rdbtnSolaris = new JRadioButton("Solaris");
		rdbtnSolaris.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setOsType("Solaris");
			}
		});
		osTypeButtonGroup.add(rdbtnSolaris);

		JLabel lblOs = new JLabel("OS");

		JLabel lblServerName = new JLabel("Server Name");

		serverName = new JTextField();
		serverName.setColumns(24);
		GroupLayout serverInformationPanelGroupLayout = new GroupLayout(
				serverInformationPanel);
		serverInformationPanelGroupLayout
				.setHorizontalGroup(serverInformationPanelGroupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								serverInformationPanelGroupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												serverInformationPanelGroupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																serverInformationPanelGroupLayout
																		.createSequentialGroup()
																		.addComponent(
																				rdbtnAix)
																		.addGap(18)
																		.addComponent(
																				rdbtnHpux)
																		.addGap(18)
																		.addComponent(
																				rdbtnSolaris))
														.addGroup(
																serverInformationPanelGroupLayout
																		.createSequentialGroup()
																		.addComponent(
																				lblServerName)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				serverName,
																				274,
																				274,
																				274))
														.addGroup(
																serverInformationPanelGroupLayout
																		.createSequentialGroup()
																		.addGroup(
																				serverInformationPanelGroupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								serverIP,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblIP))
																		.addGap(18)
																		.addGroup(
																				serverInformationPanelGroupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								serverPort,
																								GroupLayout.DEFAULT_SIZE,
																								171,
																								Short.MAX_VALUE)
																						.addComponent(
																								lblPort)))
														.addGroup(
																serverInformationPanelGroupLayout
																		.createSequentialGroup()
																		.addGroup(
																				serverInformationPanelGroupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								userId,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblId))
																		.addGap(18)
																		.addGroup(
																				serverInformationPanelGroupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblPassword)
																						.addComponent(
																								password,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)))
														.addComponent(lblOs))
										.addContainerGap()));
		serverInformationPanelGroupLayout
				.setVerticalGroup(serverInformationPanelGroupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								serverInformationPanelGroupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												serverInformationPanelGroupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblServerName)
														.addComponent(
																serverName,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												serverInformationPanelGroupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblIP)
														.addComponent(lblPort))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												serverInformationPanelGroupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																serverIP,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																serverPort,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												serverInformationPanelGroupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(lblId)
														.addComponent(
																lblPassword))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												serverInformationPanelGroupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																password,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																userId,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addComponent(lblOs)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												serverInformationPanelGroupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(rdbtnAix)
														.addComponent(rdbtnHpux)
														.addComponent(
																rdbtnSolaris))
										.addContainerGap(60, Short.MAX_VALUE)));
		serverInformationPanelGroupLayout.setAutoCreateContainerGaps(true);
		serverInformationPanel.setLayout(serverInformationPanelGroupLayout);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		JButton btnAddServer = new JButton("추가");
		btnAddServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getParent().addNewServer(getServerName(), getServerIP(),
						Integer.parseInt(getServerPort()), getUserId(),
						getPassword(), getOsType());

				resetForm();
				setVisible(false);
			}
		});

		JButton btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetForm();
				setVisible(false);
			}
		});
		GroupLayout buttonPanelGroupLayout = new GroupLayout(buttonPanel);
		buttonPanelGroupLayout.setAutoCreateContainerGaps(true);
		buttonPanelGroupLayout.setHorizontalGroup(buttonPanelGroupLayout
				.createParallelGroup(Alignment.LEADING).addGroup(
						buttonPanelGroupLayout.createSequentialGroup()
								.addGap(251).addComponent(btnAddServer)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnCancel).addContainerGap()));
		buttonPanelGroupLayout.setVerticalGroup(buttonPanelGroupLayout
				.createParallelGroup(Alignment.LEADING).addGroup(
						buttonPanelGroupLayout
								.createSequentialGroup()
								.addGroup(
										buttonPanelGroupLayout
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(btnCancel)
												.addComponent(btnAddServer))
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		buttonPanel.setLayout(buttonPanelGroupLayout);
	}

	private void resetForm() {
		this.serverName.setText(null);
		this.serverIP.setText(null);
		this.serverPort.setText(null);
		this.userId.setText(null);
		this.password.setText(null);
		this.osTypeButtonGroup.clearSelection();
	}

	public String getServerName() {
		return serverName.getText();
	}

	public void setServerName(JTextField serverName) {
		this.serverName = serverName;
	}

	public String getServerIP() {
		return serverIP.getText();
	}

	public void setServerIP(JTextField serverIP) {
		this.serverIP = serverIP;
	}

	public String getServerPort() {
		return serverPort.getText();
	}

	public void setServerPort(JTextField serverPort) {
		this.serverPort = serverPort;
	}

	public String getUserId() {
		return userId.getText();
	}

	public void setUserId(JTextField userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password.getText();
	}

	public void setPassword(JTextField password) {
		this.password = password;
	}

	public ButtonGroup getOsTypeButtonGroup() {
		return osTypeButtonGroup;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public ResourceMonitorUI getParent() {
		return parent;
	}

	public void setParent(ResourceMonitorUI parent) {
		this.parent = parent;
	}
}
