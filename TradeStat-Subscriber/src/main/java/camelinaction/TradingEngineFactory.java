package camelinaction;

public class TradingEngineFactory {
	
	public TradingEngine getEngine(String location) {
		if (location.equalsIgnoreCase("london")) {
			return new LondonTradingEngine();
		} else if (location.equalsIgnoreCase("tokyo")) {
			return new TokyoTradingEngine();
		} else if (location.equalsIgnoreCase("newyork")) {
			return new NewYorkTradingEngine();
		}
		
		return null;
	}
}
