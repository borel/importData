package main.java;

import java.util.Date;

import com.mongodb.BasicDBObject;

public class MessageData {

	private Integer priority;
	private String subject;
	private String content;
	private Date date;

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BasicDBObject toBasicDBObject() {
		BasicDBObject document = new BasicDBObject();
		document.put("priority", this.getPriority());
		document.put("subject", this.getSubject());
		document.put("content", this.getContent());
		document.put("date", this.getDate());
		return document;
	}
	
	public MessageData(Integer priority, String subject, String content,
			Date date) {
		super();
		if(priority != null){
			this.priority = priority;
		}else{
			this.priority = 0;
		}
		this.subject = subject;
		this.content = content;
		this.date = date;
	}

}
