package camelinaction;
/*
 * CIn order to calculate the various statistics for bids and asks
 **/
public interface Strategy {
	public double doOperation(double suqare, double min, int totalNumof, double price);
	
}
