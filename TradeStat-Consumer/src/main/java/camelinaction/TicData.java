package camelinaction;

public class TicData {
	private String company;
	private Strategy strategy;
	private double minBid;
	private double minAsk;
	private int totalNumOfBid;
	private int totalNumofAsk;
	private double priceOfBid;
	private double priceOfAsk;
	private double squareBid;
	private double squareAsk;
	
	public TicData(String company) {
		this.company = company;
		minBid = Double.MAX_VALUE;
		minAsk = Double.MAX_VALUE;
	}
	
	   public void ticMsgProcess(String message) {
	        String[] parts = message.split("\t");
	        double bidPrice = Double.parseDouble(parts[1]);
	        int bidNum =  Integer.valueOf(parts[2].replaceAll("[^\\d.]", ""));
	        double askPrice = Double.parseDouble(parts[3]);
	        int askNum = Integer.valueOf(parts[4].replaceAll("[^\\d.]", ""));
	        

	        minBid = Math.min(minBid, bidPrice); 
	        minAsk = Math.min(minAsk, askPrice);
	        
	        this.totalNumOfBid += bidNum;
	        this.totalNumofAsk += askNum;
	        
	        priceOfBid += bidPrice * bidNum;
	        priceOfAsk += askPrice * askNum;
	        
	        squareBid += Math.pow(bidPrice, 2) * bidNum;
	        squareAsk += Math.pow(askPrice, 2) * askNum;
	        
	    }
	
	public String getName() {
		return company;
	}
	
	/*
	 * 	Use the Strategy
	 * */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public double getBidPrice() {
		return strategy.doOperation(squareBid, minBid, totalNumOfBid, priceOfBid);
	}
	
	public double getAskPrice() {
		return strategy.doOperation(squareAsk, minAsk, totalNumofAsk, priceOfAsk);
	}
	
}
