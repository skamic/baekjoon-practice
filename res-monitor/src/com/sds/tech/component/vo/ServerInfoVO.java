package com.sds.tech.component.vo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;

public class ServerInfoVO {
	private String serverId;
	private String serverName;
	private String serverIP;
	private int serverPort;
	private String userId;
	private String password;
	private String osType;

	private Session session;
	private Channel channel;

	public ServerInfoVO() {

	}

	public ServerInfoVO(String serverId, String serverName, String serverIP,
			int serverPort, String userId, String password, String osType) {
		super();
		this.serverId = serverId;
		this.serverName = serverName;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.userId = userId;
		this.password = password;
		this.osType = osType;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "ServerInfoVO [serverName=" + serverName + ", serverIP="
				+ serverIP + ", serverPort=" + serverPort + ", userId="
				+ userId + ", password=" + password + ", osType=" + osType
				+ "]";
	}
}
