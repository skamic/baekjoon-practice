package com.sds.tech.component;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.sds.tech.ServerResourceMonitor;

public class GraphManager {
	private ServerResourceMonitor srm;

	private final String DEFAULT_IMAGE_DIRECTORY_PATH = "C:\\Users\\SDS\\Downloads";

	public GraphManager(ServerResourceMonitor srm) {
		this.srm = srm;
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setSrm(ServerResourceMonitor srm) {
		this.srm = srm;
	}

	public void saveGraphAsImage(Component graph, String type) {
		StringBuffer imageFullPath = new StringBuffer();
		String resultDirectoryPath = getSrm().getDataLoggingManager()
				.getResultDirectoryPath();
		String resultName = getSrm().getDataLoggingManager().getResultName();

		if (resultDirectoryPath == null) {
			resultDirectoryPath = DEFAULT_IMAGE_DIRECTORY_PATH;
		}

		if (resultName == null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
			resultName = formatter.format(new Date());
		}

		imageFullPath.append(resultDirectoryPath).append(File.separator)
				.append(resultName).append("_").append(type).append(".png");

		BufferedImage image = new BufferedImage(graph.getWidth(),
				graph.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = image.createGraphics();

		graph.paint(graphics2D);

		try {
			ImageIO.write(image, "png", new File(imageFullPath.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
