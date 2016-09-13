package camelinaction;

public class OperationVariance implements Strategy {
	public double doOperation(double square, double min, int totalNumof, double price){
		return square / totalNumof - Math.pow(price / totalNumof, 2);
	}
}
