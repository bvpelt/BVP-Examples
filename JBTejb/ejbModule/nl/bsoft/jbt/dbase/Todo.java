package nl.bsoft.jbt.dbase;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import nl.bsoft.jbt.ejb.ITodoItem;

import org.apache.log4j.Logger;

@Entity
@Table(name="todo")
public class Todo implements Serializable, ITodoItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1037025700491199629L;

	private Logger logger = Logger.getLogger(Todo.class);

	private long id;
	private String title;
	private String description;
	
	@Version
	private int version;

	public Todo() {
		logger.info("Start create bean");
		title = "";
		description = "";
		logger.info("End   create bean");
	}

	@Id
	@GeneratedValue
	//@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Long.toString(id));
		sb.append("-");
		sb.append(title);
		sb.append("-");
		sb.append(description);
		sb.append("-");
		sb.append(Integer.toString(version));
		return sb.toString();
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

}