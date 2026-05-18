package com.learn;


public class CascadingRequestEntityBean {
	private CascadingRequestPK id;

	private String status;


	public String getSsn() {
		return getId().getSsn();
	}

	public void setSsn(String value) {
		getId().setSsn(value);
	}

	public Long getAge() {
		return getId().getAge();
	}

	public void setAge(Long value) {
		getId().setAge(value);
	}

	public CascadingRequestPK getId() {
		if (id == null) {
			id = new CascadingRequestPK();
		}

		return id;
	}

	public void setId(CascadingRequestPK id) {
		this.id = id;
	}
	
	public int hashCode() {
		return getId().hashCode();
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if (!(o instanceof CascadingRequestEntityBean)) {
			return false;
		}
		
		final CascadingRequestEntityBean rhs = (CascadingRequestEntityBean) o;

		if (getId().equals(rhs.getId())) {
			return true;
		}

		return false;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
