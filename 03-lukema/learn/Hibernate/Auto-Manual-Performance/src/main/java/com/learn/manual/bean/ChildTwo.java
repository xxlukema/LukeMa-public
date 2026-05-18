package com.learn.manual.bean;

public class ChildTwo {
	private long id;
	private String name;

	private long parentId;
	private Parent parent;

	private static int ctr = 0;
	
	public ChildTwo()
	{
		setName("ChildTwo " + ctr++);
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

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public void print() {
		System.out.println("\tChildTwo: " + getName() + ", Parent name: " + getParent().getName());
	}
}
