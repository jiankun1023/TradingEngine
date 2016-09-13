package camelinaction;

public class TokyoTradingEngine implements TradingEngine{

	private CompositePortfolio tokyo;
	protected String name;
	protected ReportingEngine reporter;
	
	public TokyoTradingEngine() {
		this.name = "Tokyo_Trading";
		/*
		 *  portfolio set up
		 * */
		this.setPortfolio();
	}
	
	private void setPortfolio() {
		//root
		tokyo = new CompositePortfolio(name);
		//node
		CompositePortfolio cp1 = new CompositePortfolio("IBM_Varience");
		CompositePortfolio cp2 = new CompositePortfolio("ORCL_Mean");
		CompositePortfolio cp3 = new CompositePortfolio("MSFT_Min");
		//leaf
		cp1.add(new StockStats("IBM", "bidVarience"));
		cp1.add(new StockStats("IBM", "askVariance"));
		cp2.add(new StockStats("ORCL", "bidMean"));
		cp2.add(new StockStats("ORCL", "askMean"));
		cp3.add(new StockStats("MSFT", "bidMin"));
		cp3.add(new StockStats("MSFT", "askMin"));
		//bind to the root
		tokyo.add(cp1);
		tokyo.add(cp2);
		tokyo.add(cp3);
	}
	
	@Override
	public void update(String msg) {
		// TODO Auto-generated method stub
		tokyo.update(msg);
	}

	@Override
	public String report() {
		// TODO Auto-generated method stub
		reporter = ReportingEngine.getInstance();
		return reporter.report(tokyo);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}


}
