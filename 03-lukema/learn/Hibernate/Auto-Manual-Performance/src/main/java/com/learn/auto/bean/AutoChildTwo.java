package com.learn.auto.bean;

public class AutoChildTwo {
	private long id;
	private String name;

	private AutoParent parent;

	private static int ctr = 0;
	
	public AutoChildTwo()
	{
		setName("AutoChildTwo " + ctr++);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AutoParent getParent() {
		return parent;
	}

	public void setParent(AutoParent parent) {
		this.parent = parent;
	}

	public void print() {
		System.out.println("\tAutoChildTwo: " + getName() + ", Parent name: " + getParent().getName());
	}
}
