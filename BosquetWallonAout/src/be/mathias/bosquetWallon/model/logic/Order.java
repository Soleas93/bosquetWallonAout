package be.mathias.bosquetWallon.model.logic;

import java.util.ArrayList;
import java.util.List;

public class Order {
	public static enum PaymentMethod{
		Visa,
		Paypal,
		Sepa
	}
	
	public static enum DeliveryMethod{
		OnSite, //Sur place
		Post,	//Envoi Prior
		SecuredPost, //Envoi sécurisé (+10€)
	}
	
	private int id;
	private PaymentMethod paymentMethod;
	private DeliveryMethod deliveryMethod;
	private double total = 0;
	private List<Ticket> ticketList = new ArrayList<Ticket>();
}
