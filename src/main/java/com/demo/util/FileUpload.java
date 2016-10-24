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
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
public class FileUpload {
	public static String FILE_PATH = FileUpload.class.getResource("/").getFile();
	static {
		FILE_PATH = FILE_PATH.substring(0, FILE_PATH.lastIndexOf("/"));
		FILE_PATH = FILE_PATH.substring(0, FILE_PATH.lastIndexOf("/"));
		FILE_PATH = FILE_PATH.substring(0, FILE_PATH.lastIndexOf("/"));
	}
	public static String OUT_FILE_PATH = FILE_PATH.substring(0, FILE_PATH.lastIndexOf("/"));

	//图片上传
	public static String uploadImg(MultipartFile file, String path,Boolean isCompress,Integer w,Integer h) throws IOException {
		String Filepath = uploadFile(file, path);
		if (isCompress) {
			 ImgCompress imgCom = new ImgCompress(OUT_FILE_PATH+Filepath);
			 imgCom.resizeFix(w, h);
		}
		return Filepath;
	}
	
	public static String uploadImgByBase64(String base64, String fileName, String path, String realName,Boolean isCompress,Integer w,Integer h) throws IOException {
		String Filepath = uploadByBase64(base64, fileName, path, realName);
		if (isCompress) {
			 ImgCompress imgCom = new ImgCompress(OUT_FILE_PATH+Filepath);
			 imgCom.resizeFix(w, h);
		}
		return Filepath;
	}
	
	// 文件上传
	public static String uploadFile(MultipartFile file, String path) throws IOException {
		//FILE_PATH = OUT_FILE_PATH;
		String realPath = "/upload/" + path + "/";
		String discPath = OUT_FILE_PATH + realPath;

		String fileName = file.getOriginalFilename();
		String type = fileName.substring(fileName.lastIndexOf(".") + 1);
		fileName = RandomUtil.MD5(new Date().getTime() + file.getOriginalFilename()) + "." + type;
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

	public static String uploadByBase64(String base64, String fileName, String path, String realName) throws IOException {

		String realPath = "/upload/" + path + "/";
		String discPath = OUT_FILE_PATH + realPath;
		File discPathFile = new File(discPath);
		if (!discPathFile.exists()) {
			discPathFile.mkdirs();
		}
		String type = fileName.substring(fileName.lastIndexOf(".") + 1);
		String md5Name = RandomUtil.MD5(new Date().getTime() + fileName);
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
		System.out.println(GetImageStr("d:/456.png"));
//		File file = new File("d:/123");
//		File[] array = file.listFiles();
//		for (File userFile : array) {
//			ImgCompress imgCom = new ImgCompress(userFile.getPath().toString());
//			 imgCom.resizeFix(70, 70);
//			System.out.println(userFile.getName() + "~~~~~~~~~~~~~~~" + userFile.isDirectory()+"~~"+userFile.getPath());
//		}
	}
	 public static String GetImageStr(String file)
	    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	        String imgFile = file;//待处理的图片
	        InputStream in = null;
	        byte[] data = null;
	        //读取图片字节数组
	        try 
	        {
	            in = new FileInputStream(imgFile);        
	            data = new byte[in.available()];
	            in.read(data);
	            in.close();
	        } 
	        catch (IOException e) 
	        {
	            e.printStackTrace();
	        }
	        //对字节数组Base64编码
	        BASE64Encoder encoder = new BASE64Encoder();
	        return encoder.encode(data);//返回Base64编码过的字节数组字符串
	    }
}
