package com.wind.data.DatabaseOperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Jul 30, 2014  1:30:37 PM
 *@Description
 */
public class TestLog {

	@Test
	public void test() {
		final Logger logger=LogManager.getLogger(this.getClass());
		logger.info("log4j2 info");
		logger.error("log4j2 error");
	}

}
