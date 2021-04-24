package bean;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;



public class XmltoJavaObject {

	private static final String FILE_NAME = "total_sales.xml";
	
	public static void main(String[] args) {
		SalesBean salesfile = jaxbXMLtoObject();
		
		System.out.println(salesfile.toString());
	}
	
	private static SalesBean jaxbXMLtoObject() {
		try {
			JAXBContext context = JAXBContext.newInstance(SalesBean.class);
			Unmarshaller umarshaller = context.createUnmarshaller();
			
			SalesBean sbean = (SalesBean) umarshaller.unmarshal(new File(FILE_NAME));
			
			return sbean;
		}catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
