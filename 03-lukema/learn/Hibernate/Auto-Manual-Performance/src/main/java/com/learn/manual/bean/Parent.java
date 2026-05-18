package com.learn.manual.bean;

import java.util.List;

/**
 * The idea of this parent class is to test auto-relationships with hibernate.
 * 
 * In this case, we have a single parent class which may or may not have children.
 * 
 * The ChildOneChild belongs to the ChildOne object and therefore needs to be tested
 * for auto relationship.
 * 
 * Essentially what we want to prove here is this:
 *    <ol>
 *    	<li>Does retrieving the parent auto-retrieve all the related objects (ie all the children)</li>
 *    	<li>Does making the relationships manual work faster than actually coding
 *    		them in hibernate?
 *    	</li>
 *    	<li>Are there any gotchas here in the mapping of these relationships?</li>
 *   </ol>
 *   
 *   Also, with the auto relationships available, I should be able to dynamically 
 *   create queries such as:
 *   	<ul>
 *   		<ol>select parent where childOne.uid like 'x'</ol>
 *   		<ol>select parent where childOne.childOneChild.name like 'x'</ol>
 *   	</ul>
 *   
 *   This needs to be verified.
 * @author pcooley
 *
 */
public class Parent {
	private long id;
	private String name;

	private List<ChildOne> childOneChildren;
	private ChildTwo childTwo;

	private static int ctr = 0;
	
	public Parent()
	{
		setName("Parent " + ctr++);
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

	public void setChildOneChildren(List<ChildOne> childOneChildren) {
		this.childOneChildren = childOneChildren;
	}

	public List<ChildOne> getChildOneChildren() {
		return childOneChildren;
	}

	public void setChildOne(List<ChildOne> childOneChildren) {
		this.childOneChildren = childOneChildren;
	}

	public ChildTwo getChildTwo() {
		return childTwo;
	}

	public void setChildTwo(ChildTwo childTwo) {
		this.childTwo = childTwo;
	}

	public void print() {
		System.out.println("\nParent: " + getName());

		if (getChildOneChildren() == null) {
			System.out.println("\tChildOneChildren: None.");
		} else {
			System.out.println("\tChildOneChildren size: " + getChildOneChildren().size());

			for (ChildOne childOne : getChildOneChildren()) {
				childOne.print();
			}
		}

		if (getChildTwo() == null) {
			System.out.println("\tChildTwo: None.");
		} else {
			getChildTwo().print();
		}
	}
}
