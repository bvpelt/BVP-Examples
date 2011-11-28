package nl.bsoft.jbt.util;

import java.io.Serializable;


public class CalculatorParams implements ICalculatorParams, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8756596071581298303L;
	
	private String a;
	private String b;
	private String operation;
	
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
}
