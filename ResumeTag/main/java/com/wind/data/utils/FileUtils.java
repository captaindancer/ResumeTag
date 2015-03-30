package com.wind.data.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *@author liufeng E-mail:fliu.Dancer@wind.com.cn
 *@version Time:Aug 1, 2014  1:52:36 PM
 *@Description
 */

public class FileUtils {

	public static File getFile(String... names) {
		if (names == null) {
			throw new NullPointerException("names must not be null");
		}
		File file = null;
		for (String name : names) {
			if (file == null) {
				file = new File(name);
			} else {
				file = new File(file, name);
			}
		}
		return file;
	}

	public static BufferedReader getFileReader(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException(file + "is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException(file + "cann't be read");
			}
		} else {
			throw new FileNotFoundException(file + "doesn't exist");
		}

		InputStream inputStream = new FileInputStream(file);
		InputStreamReader inReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inReader);
		return bufferedReader;
	}

	public static BufferedReader getFileReader(InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
		return bufferedReader;
	}

	public static BufferedWriter getFileWriter(File file, String encoding,
			boolean append) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException(file + "is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException(file + "cann't be written to");
			}
		}
		OutputStream outputStream = new FileOutputStream(file, append);
		OutputStreamWriter ouStreamWriter = new OutputStreamWriter(
				outputStream, encoding);
		BufferedWriter bufferedWriter = new BufferedWriter(ouStreamWriter);
		return bufferedWriter;
	}

	public static BufferedWriter getFileWriter(File file, boolean append)
			throws IOException {
		return getFileWriter(file, "utf-8", append);
	}

	public static BufferedWriter getFileWriter(File file) throws IOException {
		return getFileWriter(file, false);
	}
}