package be.mathias.bosquetWallon.model.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

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
	
	private int id = 0;
	private PaymentMethod paymentMethod;
	private DeliveryMethod deliveryMethod;
	private double total = 0;
	private List<Ticket> ticketList;
	
	//db only
	private int parentId = 0;
	
	
	public Order(int id, PaymentMethod paymentMethod, DeliveryMethod deliveryMethod, double total, @Nullable List<Ticket> ticketList) {
		setId(id);
		setPaymentMethod(paymentMethod);
		setDeliveryMethod(deliveryMethod);
		setTotal(total);
		setTicketList(ticketList);
	}
	
	public Order(PaymentMethod paymentMethod, DeliveryMethod deliveryMethod, double total) {
		this(0, paymentMethod, deliveryMethod, total, null);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		if(this.id == 0 && id > 0)
			this.id = id;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public void setParentId(int spectatorId) {
		this.parentId = spectatorId;
	}


	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}


	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}


	public double getTotal() {
		return total;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public List<Ticket> getTicketList() {
		return ticketList;
	}


	private void setTicketList(@Nullable List<Ticket> ticketList) {
		if(ticketList != null)
			this.ticketList = ticketList;
		else
			this.ticketList = new ArrayList<Ticket>();
	}
	
	public void addTicket(Ticket ticket) {
		if(ticket != null)
			this.ticketList.add(ticket);
	}
	
	public void addTickets(@NonNull Collection<? extends Ticket> tickets) {
		this.ticketList.addAll(tickets);
	}
	
	public void removeTicket(int index) {
		this.ticketList.remove(index);
	}
}
