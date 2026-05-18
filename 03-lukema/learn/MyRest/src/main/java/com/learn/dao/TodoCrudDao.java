package com.learn.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.element.Todo;

public class TodoCrudDao {
    private static final Logger LOG = LogManager.getLogger();

	private static final TodoCrudDao instance = new TodoCrudDao();

	private Map<Long, Todo> dataResource;

	private TodoCrudDao() {
		dataResource = new HashMap<Long, Todo>();

		Todo todo = new Todo();
		todo.setId(101L);
		todo.setSummary("First Summary 1");
		todo.setDescription("First Desc 1");
		create(todo);

		todo = new Todo();
		todo.setId(102L);
		todo.setSummary("2nd Summary 2");
		todo.setDescription("2nd Desc 2");
		create(todo);

		printDB();

		LOG.info("Todo DAO initialized.");
	}

	public static TodoCrudDao getInstance() {
		return instance;
	}

	public Todo create(Todo todo) {
		dataResource.put(todo.getId(), todo);

		LOG.info("Created: " + todo);

		return todo;
	}

	public Todo get(Long id) {
		Todo todo = dataResource.get(id);

		LOG.info("Found: " + todo);

		return todo;
	}

	public Todo update(Todo todo) {
		Todo dbTodo = dataResource.get(todo.getId());
		if (dbTodo != null) {
			dbTodo.setDescription(todo.getDescription());
			dbTodo.setSummary(todo.getSummary());

			LOG.info("Updated: " + dbTodo);
			printDB();
		} else {
			LOG.info("Todo not found: " + todo);
		}

		return dbTodo;
	}

	public Todo delete(Long id) {
		Todo todo = dataResource.remove(id);

		LOG.info("Deleted: " + todo);
		printDB();

		return todo;
	}

	public List<Todo> list() {
		List<Todo> list = new LinkedList<Todo>();
		for (Long id : dataResource.keySet()) {
			Todo todo = dataResource.get(id);
			list.add(todo);
		}

		LOG.info("List size: " + list.size());
		printDB();

		return list;
	}

	private void printDB() {
		StringBuilder sb = new StringBuilder();
		for (Long id : dataResource.keySet()) {
			Todo todo = dataResource.get(id);

			sb.append(todo.toString()).append('\n');
		}

		LOG.info(sb.toString());
	}
}
