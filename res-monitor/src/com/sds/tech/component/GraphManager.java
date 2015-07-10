package com.sds.tech.component;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;

import com.sds.tech.ServerResourceMonitor;

public class GraphManager implements Runnable {
	private ServerResourceMonitor srm;
	private String type;
	private Component graph;

	public GraphManager(ServerResourceMonitor srm, String type) {
		this.srm = srm;
		this.type = type;
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setSrm(ServerResourceMonitor srm) {
		this.srm = srm;
	}

	public void setGraph(Component graph) {
		this.graph = graph;
	}

	public void saveGraphAsImage(String imageName) {
		DataAccessManager dataAccessManager = getSrm().getDataAccessManager();
		StringBuffer imageNameBuffer = new StringBuffer();

		imageNameBuffer.append(imageName).append("_").append(this.type)
				.append(".png");

		String imagePath = dataAccessManager.getFileFullPath(imageNameBuffer
				.toString());

		BufferedImage image = new BufferedImage(this.graph.getWidth(),
				this.graph.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = image.createGraphics();

		this.graph.paint(graphics2D);

		try {
			ImageIO.write(image, "png", new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int sequence = 1;
		boolean isStarted = false;
		DataAccessManager dataAccessManager = getSrm().getDataAccessManager();

		do {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Map<String, Integer> data = dataAccessManager.selectData(this.type,
					sequence);

			isStarted = getSrm().isStarted();
		} while (isStarted);

		System.out.println(this.type + " : " + new Date());
	}
}
