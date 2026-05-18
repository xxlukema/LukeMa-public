package com.learn.manual.bean;

public class ChildOne {
	private long id;
	private String name;

	private Parent parent;
	private long parentId;
	
	private ChildOneChild child;

	private static int ctr = 0;
	
	public ChildOne()
	{
		setName("ChildOne " + ctr++);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public ChildOneChild getChild() {
		return child;
	}

	public void setChild(ChildOneChild child) {
		this.child = child;
	}

	public void print() {
		System.out.println("\t\tChildOne: " + getName() + ", Parent name: " + getParent().getName());
		
		if (child == null) {
			System.out.println("\t\t\tChildOneChild: None.");
		} else {
			child.print();
		}
	}
}
