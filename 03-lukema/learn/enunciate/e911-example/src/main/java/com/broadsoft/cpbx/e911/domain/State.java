package com.broadsoft.cpbx.e911.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * A state is a US state representation. 
 * @author chris
 */
@XmlRootElement(name="state")
public enum State implements Serializable {
	
	AK("Alaska"),
	AL("Alabama"),
	AR("Arkansas"),
	AZ("Arizona"),
	CA("California"),
	CO("Colorado"),
	CT("Connecticut"),
	DC("District Of Coumbia"),
	DE("Deleware"),
	FL("Florida"),
	HI("Hawaii"),
	IA("Iowa"),
	ID("Idaho"),
	IL("Illinois"),
	IN("Indiana"),
	KS("Kansas"),
	KY("Kentucky"),
	LA("Louisiana"),
	MA("Massachusetts"),
	MD("Maryland"),
	ME("Maine"),
	MI("Michigan"),
	MN("Minnesota"),
	MO("Missouri"),
	MS("Mississippi"),
	MT("Montana"),
	NC("North Carolina"),
	ND("North Dakota"),
	NE("Nebraska"),
	NH("New Hampshire"),
	NJ("New Jersey"),
	NM("New Mexico"),
	NV("Nevada"),
	NY("New York"),
	OH("Ohio"),
	OK("Oklahoma"),
	OR("Oregon"),
	RI("Rhode Island"),
	SC("South Carolina"),
	SD("South Dakota"),
	TN("Tennessee"),
	TX("Texas"),
	UT("Utah"),
	VA("Virginia"),
	VT("Vermont"),
	WA("Washington"),
	WI("Wisconsin"),
	WV("West Virginia"),
	WY("Wyoming");
	
	private String description;
	
	State(String description) {
		this.description = description;
	}
	
	@XmlAttribute
	public String getDescription() {
		return description;
	}
}