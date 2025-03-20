package invoice;
 
import java.util.Arrays;
import java.util.List;
import es.uam.eps.padsof.invoices.*;

class UsageCost implements IResourceUsageInfo { // Just an example implementation
	private String description;
	private double hprice;
	private String time;
	private double fprice;
	public UsageCost (String description, double hprice, String time, double fprice) {
		this.description = description;
		this.hprice = hprice;
		this.time = time;
		this.fprice = fprice;
	}
	public String getResourceDescription() { return description; }
	public double getHourlyPrice() { return hprice; }
	public String getUsageTime() { return time; }
	public double getPrice() { return fprice; }
}