package nl.bsoft.jbt.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import nl.bsoft.jbt.dbase.Todo;

/**
 * Session Bean implementation class Calculator
 */

@Stateless(name = "calculator")
@Remote(CalculatorRemote.class)
@Local(CalculatorLocal.class)
public class Calculator implements CalculatorRemote, CalculatorLocal {

	private Logger logger = Logger.getLogger(Calculator.class);

	@EJB
	private TodoEJBLocal todoEJB;

	/**
	 * Default constructor.
	 */
	public Calculator() {
		
	}

	@Override
	public int add(int a, int b) {
		int result = a + b;

		Todo tod = new Todo();
		tod.setDescription("Test een");
		tod.setTitle("Mijn test titel");
		todoEJB.persist(tod);

		return result;
	}

	@Override
	public int subtract(int a, int b) {
		int result = a - b;
		return result;
	}

	@Override
	public int multiply(int a, int b) {
		int result = a * b;
		return result;
	}

	@Override
	public double divide(int a, int b) throws RuntimeException {
		if (0 == b) {
			RuntimeException rt = new ArithmeticException("divisor is null");
			throw rt;
		}

		double result = (double) a / (double) b;

		return result;
	}

	public TodoEJBLocal getTodoEJB() {
		return todoEJB;
	}

	public void setTodoEJB(TodoEJBLocal todoEJB) {
		this.todoEJB = todoEJB;
	}

	@PreDestroy
	public void predestroy() {
		if (logger.isInfoEnabled()) { logger.info("Start predestroy"); }
		// initialize
		if (logger.isInfoEnabled()) { logger.info("End   predestroy"); }
	}
	
	@PostConstruct
	public void postconstruct() {
		if (logger.isInfoEnabled()) { logger.info("Start postconstruct"); }
		// cleanup
		if (logger.isInfoEnabled()) { logger.info("End   postconstruct"); }
	}
	
	/* statefull
	@PrePassivate
	public void prepasivate() {
		logger.info("Start prepasivate");
		
		logger.info("End   prepasivate");
	}
	
	@PostActivate
	public void postactivate() {
		logger.info("Start postactivate");
		
		logger.info("End   postactivate");
	}
	
	@Remove
	public void remove() {
		logger.info("Start remove");
		// end of statfull process
		logger.info("End   remove");
	}
	*/
	
}
