package com.samsung.sds.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class TestThreadDumpParser {

	@Test
	public void parseTest() throws IOException {
		File dump = new File("dump/ihome_jstack_20150828_1115.log");
		FileInputStream fis = new FileInputStream(dump);
		List<ThreadInfo> threadList = ThreadDumpParser.parse(fis);

		System.out.println(threadList.size());
	}
}
