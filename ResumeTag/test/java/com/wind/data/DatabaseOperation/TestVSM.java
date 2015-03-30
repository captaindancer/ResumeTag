package com.wind.data.DatabaseOperation;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import com.wind.service.VectorSpaceModel;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 4, 2014  4:59:44 PM
 *@Description
 */
public class TestVSM {

	@Test
	public void test() {
		VectorSpaceModel vsm=new VectorSpaceModel();
		int limit=100;
		String filename="/home/liufeng/data/existvsm080500.dat";
//		int number=10;
		try {
//			System.out.println(vsm.getVSMMap(limit, number).size());
			System.out.println(vsm.getValidMap(100,limit).size());
			vsm.writeToFile(filename, vsm.getValidMap(100,limit));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
