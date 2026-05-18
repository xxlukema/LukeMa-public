package com.learn.rest.element;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;


@Data
@ToString
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
	@Pattern(regexp = "\\d{2,6}", message = "{todo.description.pattern}")
	private String description;

	public String toJSon() {
		return "{\"id=" + id + ", summary=" + summary + ", description=" + description + "\"}";
	}

}
