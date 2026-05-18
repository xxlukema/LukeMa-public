package com.learn.auto.bean;

import java.util.Set;

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
public class AutoParent {
	private long id;
	private String name;

	private Set<AutoChildOne> childOneChildren;
	private AutoChildTwo childTwo;
	
	private AutoChildOne thatChildOne;

	private static int ctr = 0;
	
	public AutoParent()
	{
		setName("AutoParent " + ctr++);
	}

	public AutoChildOne getThatChildOne() {
		return thatChildOne;
	}

	public void setThatChildOne(AutoChildOne thatChildOne) {
		this.thatChildOne = thatChildOne;
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

	public void setChildOneChildren(Set<AutoChildOne> childOneChildren) {
		this.childOneChildren = childOneChildren;
	}

	public Set<AutoChildOne> getChildOneChildren() {
		return childOneChildren;
	}

	public void setChildOne(Set<AutoChildOne> childOneChildren) {
		this.childOneChildren = childOneChildren;
	}

	public AutoChildTwo getChildTwo() {
		return childTwo;
	}

	public void setChildTwo(AutoChildTwo childTwo) {
		this.childTwo = childTwo;
	}

	public void print() {
		System.out.println("\nAutoParent: " + getName());

		if (getChildOneChildren() == null) {
			System.out.println("\tAutoChildOneChildren: None.");
		} else {
			System.out.println("\tAutoChildOneChildren size: " + getChildOneChildren().size());

			for (AutoChildOne childOne : getChildOneChildren()) {
				childOne.print();
			}
		}

		if (getChildTwo() == null) {
			System.out.println("\tAutoChildTwo: None.");
		} else {
			getChildTwo().print();
		}
		
		System.out.println("");
	}
}
