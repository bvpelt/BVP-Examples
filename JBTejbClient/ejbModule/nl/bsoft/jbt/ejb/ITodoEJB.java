package nl.bsoft.jbt.ejb;

import java.util.List;


public interface ITodoEJB {
	
	public String persist(ITodoItem todo);

	public String delete(ITodoItem todo);

	public String update(ITodoItem todo);

	public List<ITodoItem> findTodos();

	public ITodoItem findTodo(String id);
}
