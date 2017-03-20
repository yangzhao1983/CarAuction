package zy.auction;

/**
 * Store car info.
 * 
 * @author kaiser_zhao
 *
 */
public class Car {

	public Car(){
		
	}
	
	private String url;
	private String name;
	private boolean isSold;
	private String price;
	private String signDate;
	private String actionN;
	public String getActionN() {
		return actionN;
	}
	public void setActionN(String actionN) {
		this.actionN = actionN;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSold() {
		return isSold;
	}
	public void setSold(boolean isSold) {
		this.isSold = isSold;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
}
