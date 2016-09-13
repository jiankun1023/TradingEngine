package camelinaction;

/**
 *  Composite Pattern: Leaf
 * 
 * */
public class StockStats implements Portfolio {
	
	private String name;
	private String type;
	private double value;
	
	// set stock name(MSFT, IBM, ORCL) and value type(MEAN MIN VARIENCE)
	public StockStats(String name, String type) {
		this.name = name;
		this.type = type;
	}
	

	@Override
	public void update(String msg) {
		// TODO Auto-generated method stub
		String[] parts = msg.split("\t");
		for (String s: parts) {
			System.out.println(s);
		}
		if (name.equalsIgnoreCase(parts[0])) {
			if(type.equals("bidVarience")){
                value = Double.valueOf(parts[1].replaceAll("[^\\d.]", ""));
            } else if(type.equals("askVariance")){
                value = Double.valueOf(parts[2].replaceAll("[^\\d.]", ""));
            } else if(type.equals("bidMin")){
                value = Double.valueOf(parts[3].replaceAll("[^\\d.]", ""));
            } else if(type.equals("askMin")){
                value = Double.valueOf(parts[4].replaceAll("[^\\d.]", ""));
            } else if(type.equals("bidMean")){
                value = Double.valueOf(parts[5].replaceAll("[^\\d.]", ""));
            } else if(type.equals("askMean")){
                value = Double.valueOf(parts[6].replaceAll("[^\\d.]", ""));
            }
		}
			
	}

	@Override
	public String print() {
		// TODO Auto-generated method stub
		return name + "_" + type + ": " + value + "\n";
	}

}
