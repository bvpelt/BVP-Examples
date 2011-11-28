package nl.bsoft.jbt.war;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import nl.bsoft.jbt.ejb.CalculatorLocal;
import nl.bsoft.jbt.util.CalculatorParams;

/**
 * Servlet implementation class CalculatorActionServlet
 */
@WebServlet(description = "Calculator entrance point", urlPatterns = { "/CalculatorAction" })
public class CalculatorActionServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(CalculatorActionServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1419405487940732918L;

	/**
	 * Injecting the EJB
	 */
	@EJB
	private CalculatorLocal calculator;

	private Queue orderQueue = null;
	private ConnectionFactory cf = null;
	private Connection connection = null;
	private Session session = null;
	private MessageProducer producer = null;

	public void init() {
		if (logger.isInfoEnabled()) {
			logger.info("Start init");
		}

		// http://docs.jboss.org/hornetq/2.2.2.Final/user-manual/en/html_single/index.html#using-server

		if ((null == orderQueue) || (null == cf) || (null == connection)
				|| (null == session) || (null == producer)) {
			try {
				// First we'll create a JNDI initial context from which to
				// lookup our JMS objects:
				InitialContext ic = new InitialContext();

				// Now we'll look up the connection factory:
				cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");

				// And look up the Queue:
				orderQueue = (Queue) ic.lookup("/queue/OrderQueue");

				// Next we create a JMS connection using the connection factory:
				connection = cf.createConnection();

				// And we create a non transacted JMS Session, with
				// AUTO_ACKNOWLEDGE acknowledge mode:
				session = connection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);

				// We create a MessageProducer that will send orders to the
				// queue:
				producer = session.createProducer(orderQueue);

				// We make sure we start the connection, or delivery won't occur
				// on it:
				connection.start();

			} catch (NamingException e) {
				logger.error("Probleem in jndi: ", e);
			} catch (JMSException e) {
				logger.error("Probleem in jms: ", e);
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("End   init");
		}
	}

	public void destroy() {
		if (logger.isInfoEnabled()) {
			logger.info("Start destroy");
		}
		try {
			// Close connection
			connection.close();

			// Close producer
			producer.close();

			// Close session
			session.close();

			// Close connection
			connection.close();

		} catch (Exception e) {
			logger.error("Fout tijdens destroy: ", e);
		}
		connection = null;
		producer = null;
		session = null;
		connection = null;
		orderQueue = null;
		cf = null;
		if (logger.isInfoEnabled()) {
			logger.info("End   destroy");
		}
	}

	private void sendTextMessage(String text) throws JMSException {
		if (logger.isInfoEnabled()) {
			logger.info("Start send text message");
		}
		// We create a simple TextMessage and send it:
		TextMessage message = session.createTextMessage(text);
		producer.send(message);
		if (logger.isInfoEnabled()) {
			logger.info("End   send text message");
		}
	}

	private void sendObjectMessage(CalculatorParams o) throws JMSException {
		if (logger.isInfoEnabled()) {
			logger.info("Start send object message");
		}
		// We create a simple ObjectMessage and send it:
		ObjectMessage message = session.createObjectMessage();
		message.setObject(o);
		producer.send(message);
		if (logger.isInfoEnabled()) {
			logger.info("End   send object message");
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CalculatorActionServlet() {
		super();

	}

	public void setCalculator(CalculatorLocal calculator) {
		this.calculator = calculator;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProces(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProces(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doProces(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		String a = request.getParameter("a");
		String op = request.getParameter("operation");
		String b = request.getParameter("b");

		CalculatorParams cp = new CalculatorParams();
		cp.setA(a);
		cp.setB(b);
		cp.setOperation(op);

		try {
			sendObjectMessage(cp);
		} catch (Exception e) {
			logger.error("Fout bij verzenden parameters naar queue: ", e);
		}

		out.println("<HTML>");
		out.println("<HEAD><TITLE>Hello World</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<BIG>Hello World</BIG>");

		out.println("<div id=\"content\">");
		out.println("Received a: " + a + " operation: " + op + " b: " + b
				+ "<br />");

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
				out.println("Resultaat: " + Integer.toString(result) + "<br />");
				if (logger.isInfoEnabled()) {
					logger.info("Start add operation");
				}
			} else if (op.equals("-")) {
				if (logger.isInfoEnabled()) {
					logger.info("Start subtract operation");
				}
				result = calculator.subtract(ia, ib);
				out.println("Resultaat: " + Integer.toString(result) + "<br />");
				if (logger.isInfoEnabled()) {
					logger.info("Start subtract operation");
				}
			} else if (op.equals("*")) {
				if (logger.isInfoEnabled()) {
					logger.info("Start multiply operation");
				}
				result = calculator.multiply(ia, ib);
				out.println("Resultaat: " + Integer.toString(result) + "<br />");
				if (logger.isInfoEnabled()) {
					logger.info("Start multiply operation");
				}
			} else if (op.equals("/")) {
				if (logger.isInfoEnabled()) {
					logger.info("Start divide operation");
				}
				dresult = calculator.divide(ia, ib);
				out.println("Resultaat: " + Double.toString(dresult) + "<br />");
				if (logger.isInfoEnabled()) {
					logger.info("Start divide operation");
				}
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("Geen geldige combinatie gevonden");
				}
				out.println("Geen geldige operatie <br/>");
				if (logger.isInfoEnabled()) {
					logger.info("Start multiply operation");
				}
				ia = 4;
				ib = 7;
				result = calculator.multiply(ia, ib);
				out.println("Resultaat: " + Integer.toString(result) + "<br />");
				if (logger.isInfoEnabled()) {
					logger.info("Start multiply operation");
				}
			}

		} catch (Exception e) {
			logger.error("Error in execution: ", e);
			out.println("Error occured: " + e.getMessage() + "<br />");
		}
		out.println("</div>");
		out.println("</BODY></HTML>");
	}
}
