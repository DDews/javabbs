package com.dews.bbs;

import javax.xml.bind.annotation.XmlRootElement;

public class Board {
	private String name;
	private String description;
	private int id;
	public Board() {}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setDesc(String desc) {
		this.description = desc;
	}
	public String getDesc() {
		return description;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
