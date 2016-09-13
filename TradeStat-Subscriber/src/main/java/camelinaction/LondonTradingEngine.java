package camelinaction;

public class LondonTradingEngine implements TradingEngine{
	
	private CompositePortfolio london;
	protected String name;
	protected ReportingEngine reporter;
	
	public LondonTradingEngine() {
		this.name = "London_Trading";
		/*
		 *  portfolio set up
		 * */
		this.setPortfolio();
	}
	
	private void setPortfolio() {
		//root
		london = new CompositePortfolio(name);
		//node
		CompositePortfolio cp1 = new CompositePortfolio("IBM_Varience");
		CompositePortfolio cp2 = new CompositePortfolio("ORCL_Mean");

		//leaf
		cp1.add(new StockStats("IBM", "bidVarience"));
		cp1.add(new StockStats("IBM", "askVariance"));
		cp2.add(new StockStats("ORCL", "bidMean"));
		cp2.add(new StockStats("ORCL", "askMean"));

		//bind to the root
		london.add(cp1);
		london.add(cp2);
	}
	
	@Override
	public void update(String msg) {
		// TODO Auto-generated method stub
		london.update(msg);
	}

	@Override
	public String report() {
		// TODO Auto-generated method stub
		reporter = ReportingEngine.getInstance();
		return reporter.report(london);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
