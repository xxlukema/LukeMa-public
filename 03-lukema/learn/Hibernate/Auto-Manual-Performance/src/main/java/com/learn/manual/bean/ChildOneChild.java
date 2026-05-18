package com.learn.manual.bean;

public class ChildOneChild {
	private long id;
	private String name;

	private ChildOne parent;
	private long parentId;

	private static int ctr = 0;
	
	public ChildOneChild()
	{
		setName("ChildOneChild " + ctr++);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ChildOne getParent() {
		return parent;
	}

	public void setParent(ChildOne parent) {
		this.parent = parent;
	}

	public void print() {
		System.out.println("\t\t\tChildOneChild: " + getName() + ", Parent name: " + getParent().getName());
	}
}
