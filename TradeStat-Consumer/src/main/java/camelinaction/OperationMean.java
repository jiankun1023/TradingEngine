package camelinaction;

public class OperationMean implements Strategy{
	public double doOperation(double square, double min, int totalNumof, double price){
		return price / totalNumof;
	}
}
