package com.sds.tech.component;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.sds.tech.ServerResourceMonitor;

public class GraphManager implements Runnable {
	private ServerResourceMonitor srm;
	private String type;

	private Component chart;
	private XYPlot plot;
	private Map<String, TimeSeries> timeSeriesMap;

	public GraphManager(ServerResourceMonitor srm, String type) {
		this.srm = srm;
		this.type = type;
		this.timeSeriesMap = new HashMap<String, TimeSeries>();
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setSrm(ServerResourceMonitor srm) {
		this.srm = srm;
	}

	public void setChart(Component graph) {
		this.chart = graph;
	}

	public XYPlot getPlot() {
		return plot;
	}

	public void setPlot(XYPlot plot) {
		this.plot = plot;
	}

	public void saveChartAsImage(String imageName) {
		DataAccessManager dataAccessManager = getSrm().getDataAccessManager();
		StringBuffer imageNameBuffer = new StringBuffer();

		imageNameBuffer.append(imageName).append("_").append(this.type)
				.append(".png");

		String imagePath = dataAccessManager.getFileFullPath(imageNameBuffer
				.toString());

		BufferedImage image = new BufferedImage(this.chart.getWidth(),
				this.chart.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = image.createGraphics();

		this.chart.paint(graphics2D);

		try {
			ImageIO.write(image, "png", new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int sequence = 1;
		DataAccessManager dataAccessManager = getSrm().getDataAccessManager();

		initializeTimeSeriesMap();

		do {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Map<String, Integer> data = dataAccessManager.selectData(this.type,
					sequence);
			int datasetIndex = 0;

			for (String key : data.keySet()) {
				int percent = data.get(key);
				TimeSeries series = timeSeriesMap.get(key);
				RegularTimePeriod t = new Second();

				series.add(t, percent);

				if (sequence == 1) {
					plot.setDataset(datasetIndex, new TimeSeriesCollection(
							series));
					plot.setRenderer(datasetIndex++,
							new StandardXYItemRenderer());
				}
			}

			sequence++;
		} while (srm.isStarted());

		System.out.println(this.type + " : " + new Date());
	}

	private void initializeTimeSeriesMap() {
		Map<String, ServerConnector> serverMap = srm.getServerManager()
				.getServerMap();

		timeSeriesMap.clear();

		for (String serverId : serverMap.keySet()) {
			ServerConnector serverConnector = serverMap.get(serverId);
			String serverName = serverConnector.getServerName();

			timeSeriesMap.put(serverName, new TimeSeries(serverName));
		}
	}

	public void toggleChartDataset(String text, boolean selected) {
		int index = 0;

		for (String key : timeSeriesMap.keySet()) {
			if (key.equals(text)) {
				break;
			}

			index++;
		}

		plot.getRenderer(index).setSeriesVisible(0, new Boolean(selected));
	}
}
