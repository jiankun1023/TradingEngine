package camelinaction;

public interface TradingEngine {
	abstract void update(String msg);
	abstract String report();
	abstract String getName();
}

