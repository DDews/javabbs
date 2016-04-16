package com.dews.bbs;

public class Board {
	private String name;
	private String description;
	private int id;
	private int posts;
	private int topics;
	private String recentTopic;
	private int recentTopicId;
	public Board() {}
	public void setRecentTopic(String recentTopic) {
		this.recentTopic = recentTopic;
	}
	public String getRecentTopic() {
		return recentTopic;
	}
	public void setRecentTopiicId(int id) {
		this.recentTopicId = id;
	}
	public int getRecentTopicId() {
		return recentTopicId;
	}
	public void setPosts(int posts) {
		this.posts = posts;
	}
	public int getPosts() {
		return posts;
	}
	public void setTopics(int topics) {
		this.topics = topics;
	}
	public int getTopics() {
		return topics;
	}
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
