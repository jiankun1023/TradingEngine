package camelinaction;

public class ReportingEngine {
	private static ReportingEngine engine = null;
	
	private ReportingEngine() {}
	
	public static ReportingEngine getInstance() {
		if (engine == null) {
			engine = new ReportingEngine();
		}
		
		return engine;
	}
	
	public String report(Portfolio portfolio) {
		return portfolio.print();
	}
}
