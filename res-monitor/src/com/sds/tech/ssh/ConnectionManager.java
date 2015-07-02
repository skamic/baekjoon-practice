package com.sds.tech.ssh;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.sds.tech.vo.ServerInfoVO;

public class ConnectionManager {
	private JSch sch;

	public ConnectionManager() {
		setSch(new JSch());
	}

	public JSch getSch() {
		return sch;
	}

	public void setSch(JSch sch) {
		this.sch = sch;
	}

	public void connect(final ServerInfoVO newServer) {
		try {
			Session session = sch.getSession(newServer.getUserId(),
					newServer.getServerIP(), newServer.getServerPort());

			session.setUserInfo(new UserInfo() {

				@Override
				public void showMessage(String arg0) {
					// TODO Auto-generated method stub

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
					return newServer.getPassword();
				}

				@Override
				public String getPassphrase() {
					return null;
				}
			});

			session.connect();

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand("uname -a");

			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					System.out.println("exit-status: "
							+ channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
