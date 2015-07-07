package com.sds.tech.component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.sds.tech.ServerResourceMonitor;
import com.sds.tech.component.vo.ServerInfoVO;

public class ConnectionManager {
	private ServerResourceMonitor srm;

	private JSch sch;

	private Map<String, ServerInfoVO> serverInfoList;

	private boolean isStarted = false;

	public ConnectionManager(ServerResourceMonitor srm) {
		this.srm = srm;

		sch = new JSch();
		serverInfoList = new HashMap<String, ServerInfoVO>();
	}

	public ServerResourceMonitor getSrm() {
		return srm;
	}

	public void setSrm(ServerResourceMonitor srm) {
		this.srm = srm;
	}

	public JSch getSch() {
		return sch;
	}

	public void setSch(JSch sch) {
		this.sch = sch;
	}

	public Map<String, ServerInfoVO> getServerInfoList() {
		return serverInfoList;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public void connect(ServerInfoVO newServer) {
		try {
			final String password = newServer.getPassword();
			Session session = sch.getSession(newServer.getUserId(),
					newServer.getServerIP(), newServer.getServerPort());

			session.setUserInfo(new UserInfo() {
				@Override
				public void showMessage(String arg0) {

				}

				@Override
				public boolean promptYesNo(String arg0) {
					return true;
				}

				@Override
				public boolean promptPassword(String arg0) {
					return true;
				}

				@Override
				public boolean promptPassphrase(String arg0) {
					return true;
				}

				@Override
				public String getPassword() {
					return password;
				}

				@Override
				public String getPassphrase() {
					return null;
				}
			});

			session.connect();

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("uname");

			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			newServer.setSession(session);
			newServer.setChannel(channel);

			serverInfoList.put(newServer.getServerId(), newServer);
			/*
			 * byte[] tmp = new byte[1024]; while (true) { while (in.available()
			 * > 0) { int i = in.read(tmp, 0, 1024); if (i < 0) break;
			 * System.out.print(new String(tmp, 0, i)); } if
			 * (channel.isClosed()) { if (in.available() > 0) continue;
			 * System.out.println("exit-status: " + channel.getExitStatus());
			 * break; } try { Thread.sleep(1000); } catch (Exception ee) { } }
			 * channel.disconnect(); session.disconnect();
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disconnect(String serverId) {
		ServerInfoVO serverInfo = serverInfoList.get(serverId);

		serverInfo.getChannel().disconnect();
		serverInfo.getSession().disconnect();
	}
}
