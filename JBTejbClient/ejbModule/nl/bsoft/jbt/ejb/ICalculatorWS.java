package nl.bsoft.jbt.ejb;

public interface ICalculatorWS {

	int add(int a, int b);
	int subtract(int a, int b);
	int multiply(int a, int b);
	double divide(int a, int b)  throws RuntimeException;
}
