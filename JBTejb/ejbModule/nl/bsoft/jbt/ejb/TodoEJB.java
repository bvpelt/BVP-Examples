package nl.bsoft.jbt.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bsoft.jbt.dbase.Todo;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class TodoEJB
 */
// @Service(objectName="TodoEJB")
@Stateless
@Remote(TodoEJBRemote.class)
@Local(TodoEJBLocal.class)
public class TodoEJB implements TodoEJBRemote, TodoEJBLocal {
	
	@PersistenceContext(unitName = "TodoJPA")
	private EntityManager em;

	private Logger logger = Logger.getLogger(TodoEJB.class);

	private List<ITodoItem> todos;

	/**
	 * Default constructor.
	 */
	public TodoEJB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String persist(ITodoItem todo) {
		try {
			em.persist(todo);
		} catch (Exception e) {
			logger.error("Error during persist: ", e);
		}
		return null;
	}

	@Override
	public String delete(ITodoItem todo) {
		Todo t = (Todo) em.merge(todo);
		em.remove(t);
		return null;
	}

	@Override
	public String update(ITodoItem todo) {
		em.merge(todo);
		return null;
	}

	@Override
	public List<ITodoItem> findTodos() {
		todos = (List<ITodoItem>) em.createQuery("select t from Todo t")
				.getResultList();
		return todos;
	}

	@Override
	public ITodoItem findTodo(String id) {
		return (ITodoItem) em.find(Todo.class, Long.parseLong(id));
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public List<ITodoItem> getTodos() {
		return todos;
	}

	public void setTodos(List<ITodoItem> todos) {
		this.todos = todos;
	}

}
