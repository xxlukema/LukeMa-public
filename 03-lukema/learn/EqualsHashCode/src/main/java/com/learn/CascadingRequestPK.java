package com.learn;

import java.io.Serializable;

public class CascadingRequestPK implements Serializable {
	private static final long serialVersionUID = 0L;

	private String ssn;

	private Long age;

	public int hashCode() {
		return ssn.hashCode() + age.hashCode();
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (!(o instanceof CascadingRequestPK)) {
			return false;
		}

		final CascadingRequestPK pk = (CascadingRequestPK) o;

		if (pk.ssn == null) {
			return false;
		}

		if (pk.age == null) {
			return false;
		}

		if (!pk.ssn.equals(ssn)) {
			return false;
		}

		if (!pk.age.equals(age)) {
			return false;
		}

		return true;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}



}
