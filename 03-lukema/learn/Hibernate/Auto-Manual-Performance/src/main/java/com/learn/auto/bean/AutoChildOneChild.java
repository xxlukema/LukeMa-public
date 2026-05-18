package com.learn.auto.bean;

public class AutoChildOneChild {
	private long id;
	private String name;

	private AutoChildOne parent;

	private static int ctr = 0;
	
	public AutoChildOneChild()
	{
		setName("AutoChildOneChild " + ctr++);
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

	public AutoChildOne getParent() {
		return parent;
	}

	public void setParent(AutoChildOne parent) {
		this.parent = parent;
	}

	public void print() {
		System.out.println("\t\t\tAutoChildOneChild: " + getName() + ", Parent name: " + getParent().getName());
	}
}
