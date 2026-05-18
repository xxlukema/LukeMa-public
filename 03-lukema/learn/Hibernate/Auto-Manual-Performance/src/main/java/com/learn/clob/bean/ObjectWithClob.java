package com.learn.clob.bean;

import java.sql.Clob;

/**
 * This class should be tested two ways.
 * 
 * One with the hbm file pointing at a clob for a property
 * and the other with the hbm file pointing at a regular varchar2 field
 * @author pcooley
 *
 */
public class ObjectWithClob {
	private long id;
	private Clob clob;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Clob getClob() {
		return clob;
	}

	public void setClob(Clob clob) {
		this.clob = clob;
	}
}
