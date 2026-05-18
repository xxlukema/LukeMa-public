package com.broadsoft.cpbx.e911.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is used for predirection and post direction
 * 
 * http://gisweb.miamidade.gov/addresssearch/Standards/DC_USPS_Address_Standards.pdf
 * @author chris
 */
@XmlRootElement
public enum Directional {

	N("North"),
	S("South"),
	E("East"),
	W("West"),
	NE("Northeast"),
	NW("Northwest"),
	SE("SouthEast"),
	SW("SouthWest");
	
	private String abbreviation;
	
	Directional(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
}
