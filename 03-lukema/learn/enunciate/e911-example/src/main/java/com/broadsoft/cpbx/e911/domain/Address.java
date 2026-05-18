/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author chris
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Address implements Serializable {

	/**
	 * Serialization Id
	 */
	private static final long serialVersionUID = 1779003449169198359L;


	private OrderStatus orderStatus;
	
	private String houseNumber;
	
	private String street;

	private String thoroughfare;
	
	private String unitNumber;
	
	private String preDirection;
	
	private String postDirection;
	
	private String city;
	
	private String state;
	
	private String zip;
	
	private String unit;
	
	private String country;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String streetName) {
		this.street = streetName;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String streetNumber) {
		this.houseNumber = streetNumber;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPreDirection() {
		return preDirection;
	}

	public void setPreDirection(String preDirection) {
		this.preDirection = preDirection;
	}

	public String getPostDirection() {
		return postDirection;
	}

	public void setPostDirection(String postDirection) {
		this.postDirection = postDirection;
	}

	public String getThoroughfare() {
		return thoroughfare;
	}

	public void setThoroughfare(String thoroughfare) {
		this.thoroughfare = thoroughfare;
	}

	/**
	 * Getter for unit. ex apartment number
	 * 
	 * @return
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Setter for unit. ex apartment number
	 * 
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Get the order state of the address.
	 * 
	 * @see OrderState
	 * @return
	 */	
	@XmlElement(required = true)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * Sets the order state for the address,
	 * 
	 * @see OrderState
	 * @param orderState
	 */
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
