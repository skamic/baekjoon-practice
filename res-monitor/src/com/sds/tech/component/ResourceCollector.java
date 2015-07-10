package com.sds.tech.component;

public interface ResourceCollector extends Runnable {
	public void executeCommand();

	public void insertData(int percent);
}
