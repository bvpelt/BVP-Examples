package nl.bsoft.jbt.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
//import javax.ejb.TransactionAttribute;
//import javax.ejb.TransactionManagement;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import nl.bsoft.jbt.util.CalculatorParams;

import org.apache.log4j.Logger;

/**
 * Message-Driven Bean implementation class for: CalculatorJMSBean
 * 
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/OrderQueue") })
// @ResourceAdapter("hornetq-ra.rar")
// @TransactionManagement(value= TransactionManagementType.CONTAINER)
// @TransactionAttribute(value= TransactionAttributeType.REQUIRED)
public class CalculatorJMSBean implements MessageListener {

	private Logger logger = Logger.getLogger(CalculatorJMSBean.class);

	/**
	 * Injecting the EJB
	 */
	@EJB
	private CalculatorLocal calculator;

	/**
	 * Default constructor.
	 */
	public CalculatorJMSBean() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		if (logger.isInfoEnabled()) {
			logger.info("Start Received message");
		}

		if (message instanceof ObjectMessage) {
			ObjectMessage msg = (ObjectMessage) message;
			CalculatorParams icp;

			try {
				icp = (CalculatorParams) msg.getObject();

				String a = icp.getA();
				String op = icp.getOperation();
				String b = icp.getB();

				try {
					int ia = Integer.parseInt(a);
					int ib = Integer.parseInt(b);
					int result;
					double dresult;

					if (op.equals("+") || op.equals("plus")) {
						if (logger.isInfoEnabled()) {
							logger.info("Start add operation");
						}
						result = calculator.add(ia, ib);
						if (logger.isInfoEnabled()) {
							logger.info("Resultaat: "
									+ Integer.toString(result));
						}
						if (logger.isInfoEnabled()) {
							logger.info("Start add operation");
						}
					} else if (op.equals("-")) {
						if (logger.isInfoEnabled()) {
							logger.info("Start subtract operation");
						}
						result = calculator.subtract(ia, ib);
						if (logger.isInfoEnabled()) {
							logger.info("Resultaat: "
									+ Integer.toString(result));
						}
						if (logger.isInfoEnabled()) {
							logger.info("Start subtract operation");
						}
					} else if (op.equals("*")) {
						if (logger.isInfoEnabled()) {
							logger.info("Start multiply operation");
						}
						result = calculator.multiply(ia, ib);
						if (logger.isInfoEnabled()) {
							logger.info("Resultaat: "
									+ Integer.toString(result));
						}
						if (logger.isInfoEnabled()) {
							logger.info("Start multiply operation");
						}
					} else if (op.equals("/")) {
						if (logger.isInfoEnabled()) {
							logger.info("Start divide operation");
						}
						dresult = calculator.divide(ia, ib);
						if (logger.isInfoEnabled()) {
							logger.info("Resultaat: "
									+ Double.toString(dresult));
						}
						if (logger.isInfoEnabled()) {
							logger.info("Start divide operation");
						}
					} else {
						if (logger.isInfoEnabled()) {
							logger.info("Geen geldige combinatie gevonden");
						}
						if (logger.isInfoEnabled()) {
							logger.info("Geen geldige operatie");
						}
						if (logger.isInfoEnabled()) {
							logger.info("Start multiply operation");
						}
						ia = 4;
						ib = 7;
						result = calculator.multiply(ia, ib);
						if (logger.isInfoEnabled()) {
							logger.info("Resultaat: "
									+ Integer.toString(result));
						}
						if (logger.isInfoEnabled()) {
							logger.info("Start multiply operation");
						}
					}

				} catch (Exception e) {
					logger.error("Error in execution: ", e);
				}
			} catch (JMSException e1) {

				logger.error("Error in jms: ", e1);
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("End   Received message");
		}
	}
}
