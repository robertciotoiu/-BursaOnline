package resource;

public class BuyerInterests {
	String company = "";
	double minPrice = 0;
	double maxPrice = 0;
	long maxAllowedOffertAge = 0;

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public void setCompany(String companyName) {
		this.company = companyName;
	}
	
	public void setMaxAllowedOffertAge(long maxAllowedOffertAge)
	{
		this.maxAllowedOffertAge = maxAllowedOffertAge;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public String getCompany() {
		return company;
	}

	public long getMaxAllowedOffertAge() {
		return maxAllowedOffertAge;
	}
}
