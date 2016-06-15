package com.demo.web.vo;

public class FileVo {

	private String Url;
	private Boolean isDirectory;
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
