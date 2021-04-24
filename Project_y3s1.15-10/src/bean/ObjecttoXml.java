package bean;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.SalesService;
import model.Sales;
import bean.SalesBean;

public class ObjecttoXml {
	
	private static final String FILE_NAME = "total_sales.xml";

	public static void main(String[] args){
		
		SalesBean sbean = new SalesBean();
		
		sbean.setInvoiceId("invoiceId");
		sbean.setPurchaseDate("purchaseDate");
		sbean.setTotalUnits("totalUnits");
		sbean.setTotalAmount("totalAmount");
		sbean.setDiscountTax("discountTax");
		sbean.setNetAmount("netAmount");
		sbean.setPaymentType("paymentType");
		sbean.setOrderStatus("orderStatus");
		
		jaxbObjecttoXml(sbean);

	}
	
	private static void jaxbObjecttoXml(SalesBean sbean) {
		try {
			JAXBContext context = JAXBContext.newInstance(SalesBean.class);
			Marshaller marshaller = context.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			marshaller.marshal(sbean, System.out);
			
			marshaller.marshal(sbean, new File(FILE_NAME));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
