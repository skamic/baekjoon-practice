package com.sds.tech.ui.popup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.sds.tech.ui.ResourceMonitorUI;

public class ResultSettingsPopup extends JFrame {
	private static final long serialVersionUID = 8506499428711180729L;

	private ResourceMonitorUI mainUI;

	private JTextField resultName;
	private JTextField resultDirectory;

	public ResultSettingsPopup(ResourceMonitorUI mainUI) {
		this.mainUI = mainUI;
		
		setSize(new Dimension(400, 200));
		setName("");
		initUI();
	}

	public void initUI() {
		setType(Type.POPUP);
		setTitle("Result Settings");
		final JFileChooser fileChooser = new JFileChooser();

		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		JPanel resultSettingPanel = new JPanel();
		getContentPane().add(resultSettingPanel, BorderLayout.CENTER);

		JLabel lblResultName = new JLabel("Result Name");

		resultName = new JTextField();
		resultName.setColumns(24);

		resultDirectory = new JTextField();
		resultDirectory.setColumns(10);

		JLabel lblResultDirectory = new JLabel("Result Directory");

		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fileChooser.showOpenDialog(null);

				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();

					resultDirectory.setText(selectedFile.getAbsolutePath());
				}
			}
		});

		GroupLayout gl_resultSettingPanel = new GroupLayout(resultSettingPanel);
		gl_resultSettingPanel
				.setHorizontalGroup(gl_resultSettingPanel
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_resultSettingPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_resultSettingPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblResultDirectory,
																GroupLayout.DEFAULT_SIZE,
																93,
																Short.MAX_VALUE)
														.addGroup(
																gl_resultSettingPanel
																		.createSequentialGroup()
																		.addComponent(
																				lblResultName,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGap(20)))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_resultSettingPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_resultSettingPanel
																		.createSequentialGroup()
																		.addComponent(
																				resultDirectory,
																				GroupLayout.DEFAULT_SIZE,
																				171,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnBrowse,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addComponent(
																resultName,
																GroupLayout.DEFAULT_SIZE,
																265,
																Short.MAX_VALUE))
										.addGap(9)));
		gl_resultSettingPanel
				.setVerticalGroup(gl_resultSettingPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_resultSettingPanel
										.createSequentialGroup()
										.addGap(18)
										.addGroup(
												gl_resultSettingPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblResultName,
																GroupLayout.DEFAULT_SIZE,
																21,
																Short.MAX_VALUE)
														.addComponent(
																resultName))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_resultSettingPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_resultSettingPanel
																		.createSequentialGroup()
																		.addGap(4)
																		.addGroup(
																				gl_resultSettingPanel
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addGroup(
																								gl_resultSettingPanel
																										.createSequentialGroup()
																										.addGap(2)
																										.addComponent(
																												resultDirectory))
																						.addComponent(
																								btnBrowse,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)))
														.addComponent(
																lblResultDirectory,
																GroupLayout.DEFAULT_SIZE,
																27,
																Short.MAX_VALUE))
										.addGap(43)));
		gl_resultSettingPanel.setAutoCreateGaps(true);
		gl_resultSettingPanel.setAutoCreateContainerGaps(true);
		resultSettingPanel.setLayout(gl_resultSettingPanel);

		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		JButton btnConfirm = new JButton("확인");
		btnConfirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
		buttonPanelGroupLayout.setHorizontalGroup(buttonPanelGroupLayout
				.createParallelGroup(Alignment.LEADING).addGroup(
						buttonPanelGroupLayout.createSequentialGroup()
								.addGap(251).addComponent(btnConfirm)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnCancel).addContainerGap()));
		buttonPanelGroupLayout.setVerticalGroup(buttonPanelGroupLayout
				.createParallelGroup(Alignment.LEADING).addGroup(
						Alignment.TRAILING,
						buttonPanelGroupLayout
								.createSequentialGroup()
								.addContainerGap(20, Short.MAX_VALUE)
								.addGroup(
										buttonPanelGroupLayout
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(btnCancel)
												.addComponent(btnConfirm))
								.addContainerGap()));
		buttonPanelGroupLayout.setAutoCreateContainerGaps(true);
		buttonPanel.setLayout(buttonPanelGroupLayout);
	}

	private void resetForm() {
		this.resultName.setText(null);
		this.resultDirectory.setText(null);
	}

	public ResourceMonitorUI getParent() {
		return mainUI;
	}

	public void setParent(ResourceMonitorUI parent) {
		this.mainUI = parent;
	}
}
