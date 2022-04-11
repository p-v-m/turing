package turing;

public interface OperationBuilder {

	public default void addOperation(Operation op) {}
	public default void deleteOperation(int numOp) {}
}
