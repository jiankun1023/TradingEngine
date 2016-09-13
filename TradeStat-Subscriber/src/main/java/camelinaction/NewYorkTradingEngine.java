package camelinaction;

public class NewYorkTradingEngine implements TradingEngine{
	
	private CompositePortfolio ny;
	protected String name;
	protected ReportingEngine reporter;
	
	public NewYorkTradingEngine() {
		this.name = "New_York_Trading";
		/*
		 *  portfilio set up
		 * */
		this.setPortfolio();
	}
	
	private void setPortfolio() {
		//root
		ny = new CompositePortfolio(name);
		//node
		CompositePortfolio cp1 = new CompositePortfolio("ORCL_Min");
		CompositePortfolio cp2 = new CompositePortfolio("MSFT_Mean");
		//leaf
		cp1.add(new StockStats("ORCL", "bidMin"));
		cp1.add(new StockStats("ORCL", "askMin"));
		cp2.add(new StockStats("MSFT", "bidMean"));
		cp2.add(new StockStats("MSFT", "askMean"));
		//bind to the root
		ny.add(cp1);
		ny.add(cp2);
	}
	
	@Override
	public void update(String msg) {
		// TODO Auto-generated method stub
		ny.update(msg);
	}

	@Override
	public String report() {
		// TODO Auto-generated method stub
		reporter = ReportingEngine.getInstance();
		return reporter.report(ny);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
