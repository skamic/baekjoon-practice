package com.sds.tech;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sds.tech.component.ConnectionManager;
import com.sds.tech.component.DataAccessManager;
import com.sds.tech.component.vo.ServerInfoVO;

public class DataLoggingManagerTest {
	private ServerResourceMonitor srm;

	@Before
	public void initialize() {
		this.srm = new ServerResourceMonitor();
	}

	// @Test
	public void createTableTest() throws Exception {
		DataAccessManager dataLoggingManager = this.srm
				.getDataLoggingManager();

		dataLoggingManager.createTable();
	}

	// @Test
	public void insertDataTest() throws Exception {
		DataAccessManager dataLoggingManager = this.srm
				.getDataLoggingManager();

		dataLoggingManager.insertData(1, "WEB_1", "cpu", 4);
		dataLoggingManager.insertData(1, "WEB_2", "cpu", 5);
		dataLoggingManager.insertData(1, "WAS_1", "cpu", 12);
		dataLoggingManager.insertData(1, "WAS_2", "cpu", 15);
		dataLoggingManager.insertData(1, "DB_1", "cpu", 7);
	}

	@Test
	public void selectDataTest() {
		ConnectionManager connectionManager = this.srm.getConnectionManager();
		DataAccessManager dataLoggingManager = this.srm
				.getDataLoggingManager();
		Map<String, ServerInfoVO> serverInfoList = connectionManager
				.getServerInfoList();

		serverInfoList.put("123.123.123.123:22", new ServerInfoVO(
				"123.123.123.123:22", "WEB_1", "123.123.123.123", 22, "", ""));
		serverInfoList.put("123.123.123.124:22", new ServerInfoVO(
				"123.123.123.124:22", "WEB_2", "123.123.123.124", 22, "", ""));
		serverInfoList.put("123.123.123.125:22", new ServerInfoVO(
				"123.123.123.125:22", "WAS_1", "123.123.123.125", 22, "", ""));
		serverInfoList.put("123.123.123.126:22", new ServerInfoVO(
				"123.123.123.126:22", "WAS_2", "123.123.123.126", 22, "", ""));
		serverInfoList.put("123.123.123.127:22", new ServerInfoVO(
				"123.123.123.127:22", "DB_1", "123.123.123.127", 22, "", ""));

		dataLoggingManager.selectData("cpu", 1);
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setSrm(ServerResourceMonitor srm) {
		this.srm = srm;
	}
}
