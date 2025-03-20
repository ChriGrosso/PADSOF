package invoice;
 
import java.util.Arrays;
import java.util.List;
import es.uam.eps.padsof.invoices.*;

public class MainInvoiceGeneration {
	public static void main(String[] args) throws NonExistentFileException, UnsupportedImageTypeException {
		InvoiceSystem.createInvoice( 
				new Invoice (),			    
				"./tmp/" // Output folder, it must exist
			  );    
	}
}