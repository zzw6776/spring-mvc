package com.demo.web.vo;

import java.util.List;

public class FileVo {

	private String Url;
	private Boolean isDirectory;
	private List<FileVo> fileVos;
	
	
	public List<FileVo> getFileVos() {
		return fileVos;
	}
	public void setFileVos(List<FileVo> fileVos) {
		this.fileVos = fileVos;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public Boolean getIsDirectory() {
		return isDirectory;
	}
	public void setIsDirectory(Boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public FileVo(String url, Boolean isDirectory) {
		super();
		Url = url;
		this.isDirectory = isDirectory;
	}
	public FileVo() {
		super();
	}
	
	
}
