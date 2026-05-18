package com.learn.rest.element;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "todo")
public class Todo {

	private Long id;

	// State code
	@NotNull(message = "{todo.summary.notnull}")
	@Pattern(regexp = "[A-Z]{2}", message = "{todo.summary.pattern}")
	private String summary;

	// Site id
	@NotNull(message = "{todo.description.notnull}")
	@Size(min = 2, max = 40, message = "{todo.description.size}")
	@Pattern(regexp = "[0-9]{2,6}", message = "{todo.description.pattern}")
	private String description;

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", summary=" + summary + ", description=" + description + "]";
	}

	public String toJSon() {
		return "{\"id=" + id + ", summary=" + summary + ", description=" + description + "\"}";
	}

}
