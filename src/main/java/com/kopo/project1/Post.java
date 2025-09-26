package com.kopo.project1;

public class Post {
	int idx;
	String title;
	String contents;
	String writer;
	String created;
	String lastUpdated;
	String titleChosung;
	
	Post(){
	}
	
	Post(int idx, String title, String contents, String writer, String created, String lastUpdated, String titleChosung){
		this.idx=idx;
		this.title=title;
		this.contents=contents;
		this.writer=writer;
		this.created=created;
		this.lastUpdated=lastUpdated;
		this.titleChosung=titleChosung;
	}
	
	Post(String title, String contents, String writer, String created, String lastUpdated){
		this.title=title;
		this.contents=contents;
		this.writer=writer;
		this.created=created;
		this.lastUpdated=lastUpdated;
	}
	
	Post(String title, String contents, String writer, String created, String lastUpdated, String titleChosung){
		this.title=title;
		this.contents=contents;
		this.writer=writer;
		this.created=created;
		this.lastUpdated=lastUpdated;
		this.titleChosung=titleChosung;
	}
	
	Post(int idx, String title, String writer, String created, String lastUpdated, String titleChosung){
		this.idx=idx;
		this.title=title;
		this.writer=writer;
		this.created=created;
		this.lastUpdated=lastUpdated;
		this.titleChosung=titleChosung;
	}
	
	Post(String title, String contents, String writer, String titleChosung){
		this.title=title;
		this.contents=contents;
		this.writer=writer;
		this.titleChosung=titleChosung;
	}
	
	Post(String title, String contents, String titleChosung){
		this.title=title;
		this.contents=contents;
		this.titleChosung=titleChosung;
	}

	public int getIdx() {
		return this.idx;
	}

	public String getTitle() {
		return this.title;
	}

	public String getContents() {
		return this.contents;
	}

	public String getWriter() {
		return this.writer;
	}

	public String getCreated() {
		return this.created;
	}

	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public String getTitleChosung() {
		return titleChosung;
	}

	public void setTitleChosung(String titleChosung) {
		this.titleChosung = titleChosung;
	}
}
