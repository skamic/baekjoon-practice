package com.sds.tech.component;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.sds.tech.ServerResourceMonitor;
import com.sds.tech.component.vo.ServerInfoVO;

public class DataAccessManager {
	private static final String INSERT_SQL = "insert into resource_usage (seq, server_name, type, percent) values (?, ?, ?, ?)";

	private ServerResourceMonitor srm;

	private String resultName = "result";
	private String resultDirectoryPath = "c:\\Temp";

	public DataAccessManager(ServerResourceMonitor srm) {
		this.setSrm(srm);
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setSrm(ServerResourceMonitor srm) {
		this.srm = srm;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public String getResultDirectoryPath() {
		return resultDirectoryPath;
	}

	public void setResultDirectoryPath(String resultDirectoryPath) {
		this.resultDirectoryPath = resultDirectoryPath;
	}

	public void setResultSettings(String resultName, String resultDirectoryPath) {
		setResultName(resultName);
		setResultDirectoryPath(resultDirectoryPath);
	}

	public String getFileFullPath(String fileName) {
		StringBuffer path = new StringBuffer();

		path.append(getResultDirectoryPath()).append(File.separator)
				.append(getResultName()).append("_").append(fileName);

		return path.toString().replaceAll("\\\\", "/");
	}

	public Connection getConnection() throws IOException {
		Connection conn = null;
		StringBuffer url = new StringBuffer();

		url.append("jdbc:ucanaccess://").append(
				getFileFullPath("data.mdb;Newdatabaseversion=V2010;"));

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(url.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public int createTable() throws Exception {
		int result = 0;

		String resultDataFileName = getFileFullPath("data.mdb");
		File resultDataFile = new File(resultDataFileName);

		if (resultDataFile.exists()) {
			resultDataFile.delete();
		}

		Connection conn = getConnection();
		Statement stmt = null;
		StringBuffer sql = new StringBuffer();

		sql.append("create table resource_usage (")
				.append("	seq	int	not null,")
				.append("	server_name	text(50)	not null,")
				.append("	type	text(6)	not null,")
				.append("	percent	int	not null,")
				.append("	constraint resource_usage_pk primary key(seq, server_name, type)")
				.append(")");

		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public int insertData(int seq, String serverName, String type,
			int usagePercentage) throws Exception {
		int result = 0;

		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();

		sql.append(INSERT_SQL);

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, seq);
			pstmt.setString(2, serverName);
			pstmt.setString(3, type);
			pstmt.setInt(4, usagePercentage);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public Map<String, Integer> selectData(String type, int seq) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		Map<String, ServerInfoVO> serverInfoList = getSrm()
				.getConnectionManager().getServerInfoList();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer();

		sql.append("select seq\n");

		for (String key : serverInfoList.keySet()) {
			ServerInfoVO vo = serverInfoList.get(key);
			String serverName = vo.getServerName();

			sql.append(", nz(sum(switch(server_name = '").append(serverName)
					.append("', percent)), 0) as ").append(serverName)
					.append("\n");
		}

		sql.append("from resource_usage\n");
		sql.append("where type = ? and seq = ?");
		sql.append("group by seq, type");

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, type);
			pstmt.setInt(2, seq);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				for (String key : serverInfoList.keySet()) {
					ServerInfoVO vo = serverInfoList.get(key);
					String serverName = vo.getServerName();

					result.put(serverName, rs.getInt(serverName));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
}
