package invoice;
 
import java.util.Arrays;
import java.util.List;
import es.uam.eps.padsof.invoices.*;

class Invoice implements IInvoiceInfo { // Just an example implementation 
	public String getCompanyName() { return "This is the company name"; }
	public String getCompanyLogo () { return "./resources/logo.jpg"; } // jpg, gif and png formats are supported	
	public String getAirline() { return "Iberia"; }
	public String getInvoiceIdentifier() { return "INV000213"; }
	public String getInvoiceDate() { return "March 1st 2025"; }
	public double getBasePrice() { return 500; }
	public double getSurcharge() { return 250; }
	public List<IResourceUsageInfo> getResourcePrices () { 
		return Arrays.asList( 
				new UsageCost ("Finger f22", 10.35, "50 minutes", 10.35),
				new UsageCost ("Hangar h02", 150.3, "2 hours 30 minutes", 450.9),
				new UsageCost ("Finger f24", 12.35, "50 minutes", 12.35)
		);
	}
	public double getPrice() { return 1223.6; }
}