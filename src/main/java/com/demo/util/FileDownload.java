package com.demo.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件下载工具类
 */
public class FileDownload {

	public static void main(String[] args) throws IOException {
		httpDownload("http://www.500d.me/editresume/export/500d_329950_69_336867_20161222172158.pdf",
				"C:\\Users\\Administrator\\Desktop\\1.pdf");
	}

	public static boolean httpDownload(String httpUrl, String saveFile) {
		// 下载网络文件
		int bytesum = 0;
		int byteread = 0;
		URL url = null;
		try {
			url = new URL(httpUrl);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			URLConnection conn = url.openConnection();
			inStream = conn.getInputStream();
			fs = new FileOutputStream(saveFile);
			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				inStream.close();
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
