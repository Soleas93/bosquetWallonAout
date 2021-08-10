package be.mathias.bosquetWallon.model.logic;

import java.time.LocalDate;
import java.time.LocalTime;

public class Representation {
	private int id = 0;
	private LocalDate date;
	private LocalTime beginHour;
	private LocalTime endHour;
	private Show show;
	
	public Representation(int id, LocalDate date, LocalTime beginHour, LocalTime endHour, Show show) {
		setId(id);
		setDate(date);
		setBeginHour(beginHour);
		setEndHour(endHour);
		setShow(show);
	}
	
	public Representation(LocalDate date, LocalTime beginHour, LocalTime endHour, Show show) {
		this(0, date, beginHour, endHour, show);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		if(this.id == 0 && id > 0)
			this.id = id;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		//TODO : concurrence
		//TODO : dans limite planning ?
		this.date = date;
	}


	public LocalTime getBeginHour() {
		return beginHour;
	}


	public void setBeginHour(LocalTime beginHour) {
		//TODO : concurrence
		//TODO : dans limite planning ?
		this.beginHour = beginHour;
	}


	public LocalTime getEndHour() {
		return endHour;
	}


	public void setEndHour(LocalTime endHour) {
		//TODO : concurrence
		//TODO : dans limite planning ?
		this.endHour = endHour;
	}


	public void setShow(Show show) {
		if(this.show == null)
			this.show = show;
	}


	public Show getShow() {
		return this.show;
	}
}
