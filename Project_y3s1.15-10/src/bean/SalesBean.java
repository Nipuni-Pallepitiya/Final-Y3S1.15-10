package bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Sale")
@XmlType(propOrder = {
		"invoiceId",
		"purchaseDate",
		"totalUnits",
		"netAmount",
		"discountTax",
		"totalAmount",
		"paymentType",
		"orderStatus"
})
public class SalesBean {

	private String invoiceId;
	private String purchaseDate;
	private String totalUnits;
	private String netAmount;
	private String discountTax;
	private String totalAmount;
	private String paymentType;
	private String orderStatus;
	
	public SalesBean() {
		
	}
	
	//public SalesBean(String invoiceId, String purchaseDate, String totalUnits, String netAmount, String discountTax, String totalAmount,
	//		String paymentType, String orderStatus) {
	//	super();
	//	this.invoiceId = invoiceId;
	//	this.purchaseDate = purchaseDate;
	//	this.totalUnits = totalUnits;
	//	this.netAmount = netAmount;
	//	this.discountTax = discountTax;
	//	this.totalAmount = totalAmount;
	//	this.paymentType = paymentType;
	//	this.orderStatus = orderStatus;
	//}

	@XmlAttribute
	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	@XmlElement
	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	@XmlElement
	public String getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(String totalUnits) {
		this.totalUnits = totalUnits;
	}

	@XmlElement
	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	@XmlElement
	public String getDiscountTax() {
		return discountTax;
	}

	public void setDiscountTax(String discountTax) {
		this.discountTax = discountTax;
	}

	@XmlElement
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@XmlElement
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@XmlElement
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	
}

