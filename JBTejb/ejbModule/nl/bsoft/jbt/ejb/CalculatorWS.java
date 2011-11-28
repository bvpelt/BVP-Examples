package nl.bsoft.jbt.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;

@WebService(targetNamespace = "urn:Calculator")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)

/**
 * Session Bean implementation class CalculatorWS
 */
@Stateless
@Remote(CalculatorWSRemote.class)
@Local(CalculatorWSLocal.class)
public class CalculatorWS implements CalculatorWSRemote, CalculatorWSLocal {

	private Logger logger = Logger.getLogger(CalculatorWS.class);
	
    /**
     * Default constructor. 
     */
    public CalculatorWS() {
        // TODO Auto-generated constructor stub
    }

	@WebMethod
	@WebResult(name = "result")
	public double calculate(
			@WebParam(name = "a") int a, 
			@WebParam(name = "operation") String op,
			@WebParam(name = "b") int b)  throws RuntimeException {
		
		double result;
		
		if (op.equals("+") || op.equals("plus")) {
			if (logger.isInfoEnabled()) {
				logger.info("Start add operation");
			}
			result = add(a, b);
			
			if (logger.isInfoEnabled()) {
				logger.info("End   add operation");
			}
		} else if (op.equals("-")) {
			if (logger.isInfoEnabled()) {
				logger.info("Start subtract operation");
			}
			result = subtract(a, b);
			
			if (logger.isInfoEnabled()) {
				logger.info("End   subtract operation");
			}
		} else if (op.equals("*")) {
			if (logger.isInfoEnabled()) {
				logger.info("Start multiply operation");
			}
			result = multiply(a, b);
			
			if (logger.isInfoEnabled()) {
				logger.info("End   multiply operation");
			}
		} else if (op.equals("/")) {
			if (logger.isInfoEnabled()) {
				logger.info("Start divide operation");
			}
			result = divide(a, b);
			
			if (logger.isInfoEnabled()) {
				logger.info("End   divide operation");
			}
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("Geen geldige combinatie gevonden");
			}
			
			if (logger.isInfoEnabled()) {
				logger.info("Start multiply operation");
			}
			a = 4;
			b = 7;
			result = multiply(a, b);
			
			if (logger.isInfoEnabled()) {
				logger.info("End   multiply operation");
			}
		}
		return result;
	}

	@Override
	public int add( int a, int b) {
		int result = a + b;
		return result;
	}

	@Override
	public int subtract(int a, int b) {
		int result = a -b ;
		return result;
	}

	@Override
	public int multiply(int a, int b) {
		int result = a*b;
		return result;
	}

	@Override
	public double divide(int a, int b) {
		double result = (double)a / (double) b;
		return result;
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
}
