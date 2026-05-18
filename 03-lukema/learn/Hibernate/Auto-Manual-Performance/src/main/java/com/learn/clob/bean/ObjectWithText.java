package com.learn.clob.bean;

/**
 * This class should be tested two ways.
 * 
 * One with the hbm file pointing at a clob for a property
 * and the other with the hbm file pointing at a regular varchar2 field
 * @author pcooley
 *
 */
public class ObjectWithText {
	private long id;
	private String text;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
