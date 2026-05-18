package com.learn.auto.bean;

public class AutoChildOne {
	private long id;
	private String name;

	private AutoParent parent;
	private AutoChildOneChild child;

	private static int ctr = 0;
	
	public AutoChildOne()
	{
		setName("AutoChildOne " + ctr++);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AutoParent getParent() {
		return parent;
	}

	public void setParent(AutoParent parent) {
		this.parent = parent;
	}

	public AutoChildOneChild getChild() {
		return child;
	}

	public void setChild(AutoChildOneChild child) {
		this.child = child;
	}

	public void print() {
		System.out.println("\t\tAutoChildOne: " + getName() + ", Parent name: " + getParent().getName());
		
		if (child == null) {
			System.out.println("\t\t\tAutoChildOneChild: None.");
		} else {
			child.print();
		}
	}
}
