package domain;

import java.util.Date;

public class Offer
{
	private Integer offerNumber;
	private Date firstDay; // Dates are stored as java.util.Date objects instead of java.sql.Date objects
	private Date lastDay;  // because, they are not well stored in db4o as java.util.Date objects
	private float price;   // This is coherent because objects of java.sql.Date are objects of java.util.Date 
	private String book;
	private RuralHouse ruralHouse;
	
	public String getBook() 
	{
		return book;
	}

	public void setBook(String book) 
	{
		this.book = book;
	}

	public Offer()
	{
		
	}
	
	public Offer(Date firstDay, Date lastDay, float price, RuralHouse ruralHouse)
	{
		  this.firstDay = firstDay;
		  this.lastDay = lastDay;
		  this.price = price;
		  this.ruralHouse = ruralHouse;
		  this.book = "NO";
	}
	
	/**
	 * Get the house number of the offer
	 * 
	 * @return the house number
	 */
	public RuralHouse getRuralHouse() 
	{
		return this.ruralHouse;
	}

	/**
	 * Set the house number to a offer
	 * 
	 * @param house number
	 */
	public void setRuralHouse(RuralHouse ruralHouse) 
	{
		this.ruralHouse = ruralHouse;
	}


	/**
	 * Get the offer number
	 * 
	 * @return offer number
	 */
	public int getOfferNumber() 
	{
		return this.offerNumber;
	}

	/**
	 * Get the first day of the offer
	 * 
	 * @return the first day
	 */
	public Date getFirstDay() 
	{
		return this.firstDay;
	}

	/**
	 * Set the first day of the offer
	 * 
	 * @param firstDay
	 *            The first day
	 */
	public void setFirstDay(Date firstDay) 
	{
		this.firstDay = firstDay;
	}

	/**
	 * Get the last day of the offer
	 * 
	 * @return the last day
	 */
	public Date getLastDay() 
	{
		return this.lastDay;
	}

	/**
	 * Set the last day of the offer
	 * 
	 * @param lastDay
	 *            The last day
	 */
	public void setLastDay(Date lastDay) 
	{
		this.lastDay = lastDay;
	}

	/**
	 * Get the price
	 * 
	 * @return price
	 */
	public float getPrice() 
	{
		return this.price;
	}

	/**
	 * Set the price
	 * 
	 * @param price
	 */
	public void setPrice(float price) 
	{
		this.price = price;
	}
	
	public String toString()
	{
		return offerNumber+";"+firstDay.toString()+";"+lastDay.toString()+";"+price;
	}
}