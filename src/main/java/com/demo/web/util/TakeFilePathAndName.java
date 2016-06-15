package com.demo.web.util;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.demo.web.vo.FileVo;

import junit.awtui.Logo;

public class TakeFilePathAndName {
	// static Logger log =
	// Logger.getLogger(TakeFilePathAndName.class.getClass());
	private static String path;
	static {
		path = TakeFilePathAndName.class.getResource("/").getFile();
		path = path.substring(0, path.lastIndexOf("/")) + "/";
		System.out.println(path);
	}

	public static List<FileVo> getFileVo(String userPath) {
		List<FileVo> fileVos = new ArrayList<FileVo>();
		File file = new File(path + userPath);
		File[] array = file.listFiles();
		for (File userFile : array) {
			System.out.println(userFile.getName() + "~~~~~~~~~~~~~~~" + userFile.isDirectory());
			fileVos.add(new FileVo(userPath + "/" + userFile.getName(), userFile.isDirectory()));
		}
		return fileVos;
	}

	public static List<FileVo> name(List<FileVo> fileVos) {

		for (FileVo fileVo : fileVos) {
			if (fileVo.getIsDirectory()) {
				name(getFileVo(fileVo.getUrl()));
			}

		}
		return null;

	}

	public static void main(String[] args) {

		name(getFileVo(""));

	}
}
