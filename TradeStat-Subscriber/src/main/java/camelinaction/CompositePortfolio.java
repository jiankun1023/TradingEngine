package camelinaction;

import java.util.*;

/**
 *   Composite Pattern: Composite
 * */
public class CompositePortfolio implements Portfolio{
	
	private String name;
	private List<Portfolio> portfolios = new ArrayList<>();
	
	public CompositePortfolio(String name) {
		this.name = name;
	}
	
	public void add(Portfolio portfolio) {
		this.portfolios.add(portfolio);
	}
	
	public void remove(Portfolio portfolio) {
		this.portfolios.remove(portfolio);
	}
	
	@Override
	public void update(String msg) {
		// TODO Auto-generated method stub
		for (Portfolio p : portfolios) {
			p.update(msg);
		}
	}

	@Override
	public String print() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(name + ":\n");
		
		for(Portfolio p : portfolios) {
			sb.append(p.print());
		}
		
		return sb.toString();
	}
	

}
