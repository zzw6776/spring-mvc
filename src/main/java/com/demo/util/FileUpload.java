package com.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class FileUpload {
	// 根据spring获取项目根目录
	//兼容windows反斜杠
	public static String FILE_PATH = ContextLoader.getCurrentWebApplicationContext().getServletContext()
			.getRealPath("/").replaceAll("\\\\", "/");
	public static String OUT_FILE_PATH = FILE_PATH.substring(0,
			FILE_PATH.substring(0, FILE_PATH.length() - 1).lastIndexOf("/"));

	/**
	 * 
	 * @param file
	 * @param path  路径
	 * @param isCompress 是否压缩 
	 * @param w  宽
	 * @param h  高
	 * @return
	 * @throws IOException
	 */
	public static String uploadImg(MultipartFile file, String path, Boolean isCompress, Integer w, Integer h)
			throws IOException {
		String Filepath = uploadFile(file, path);
		if (isCompress) {
			ImgCompress imgCom = new ImgCompress(OUT_FILE_PATH + Filepath);
			imgCom.resizeFix(w, h);
		}
		return Filepath;
	}

	public static String uploadImgByBase64(String base64, String fileName, String path, String realName,
			Boolean isCompress, Integer w, Integer h) throws IOException {
		String Filepath = uploadByBase64(base64, fileName, path, realName);
		if (isCompress) {
			ImgCompress imgCom = new ImgCompress(OUT_FILE_PATH + Filepath);
			imgCom.resizeFix(w, h);
		}
		return Filepath;
	}

	// 文件上传
	public static String uploadFile(MultipartFile file, String path) throws IOException {
		String realPath = "/upload/" + path + "/";
		String discPath = OUT_FILE_PATH + realPath;

		String fileName = file.getOriginalFilename();
		String type = fileName.substring(fileName.lastIndexOf(".") + 1);
		//若上传文件没有后缀名
		if (type.length() == fileName.length()) {
			type = file.getContentType().substring(file.getContentType().indexOf("/") + 1,
					file.getContentType().length());
		}

		fileName = EncryptUtil.MD5(new Date().getTime() + file.getOriginalFilename(), true) + "." + type;
		File tempFile = new File(discPath + fileName);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
		String contextPath = realPath + fileName;
		if (!tempFile.exists()) {
			tempFile.createNewFile();
		}
		file.transferTo(tempFile);
		return contextPath;
	}

	public static String uploadByBase64(String base64, String fileName, String path) throws IOException {
		return uploadByBase64(base64, fileName, path, null);
	}
/**
 * 
 * @param base64   
 * @param fileName	文件名字
 * @param path		路径
 * @param realName	真实名字
 * @return
 * @throws IOException
 */
	public static String uploadByBase64(String base64, String fileName, String path, String realName)
			throws IOException {
		String realPath = "/upload/" + path + "/";
		String discPath = OUT_FILE_PATH + realPath;
		File discPathFile = new File(discPath);
		if (!discPathFile.exists()) {
			discPathFile.mkdirs();
		}
		String type = fileName.substring(fileName.lastIndexOf(".") + 1);
		String md5Name = EncryptUtil.MD5(new Date().getTime() + fileName, true);
		String dateFileName = "";
		if (!StringUtils.isEmpty(realName)) {
			dateFileName = realName + "." + type;
		} else {
			dateFileName = md5Name + "." + type;
		}
		File tempFile = new File(discPath + dateFileName);
		if (!tempFile.exists()) {
			tempFile.createNewFile();
		}
		byte[] bytes = Base64.decodeBase64(base64);
		for (int i = 0; i < bytes.length; ++i) {
			if (bytes[i] < 0) {// 调整异常数据
				bytes[i] += 256;
			}
		}
		OutputStream out = new FileOutputStream(tempFile);
		out.write(bytes);
		out.flush();
		out.close();
		String contextPath = realPath + dateFileName;
		return contextPath;
	}

	public static void main(String[] args) throws IOException {
		String temp = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		System.out.println(temp);
	}

	public static String GetImageStr(String file) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = file;// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
}
