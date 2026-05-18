package com.learn;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;

public class Pojo implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = Logger.getLogger(Pojo.class);

	private static int counter = 0;
	private int id = 0;

	public Pojo() {
		id = ++counter;
		LOG.info("Constructor" + getId());
	}

	@PostConstruct
	public void postConstruct() {
		LOG.info("postConstruct" + getId());
	}

	@PreDestroy
	public void preDestroy() {
		LOG.info("preDestroy" + getId());
	}

	@Override
	public void finalize() {
		LOG.info("finalize" + getId());
	}

	public String getId() {
		return " id = " + id + ": Pojo ";
	}

	public void setId(int id) {
		this.id = id;
	}
}
