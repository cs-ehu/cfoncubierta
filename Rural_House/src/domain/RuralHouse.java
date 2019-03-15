package domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;


public class RuralHouse
{
	private Integer houseNumber;
	private String description;
	private String city; 
	public Set<Offer> offers;
	
	public Set<Offer> getOffers() 
	{
		return offers;
	}

	public void setOffers(Set<Offer> offers) 
	{
		this.offers = offers;
	}

	public RuralHouse() 
	{

	}

	public RuralHouse(String description, String city) 
	{
		this.description = description;
		this.city = city;
		offers=new HashSet<Offer>();
	}

	public Integer getHouseNumber() 
	{
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) 
	{
		this.houseNumber = houseNumber;
	}

	public String getDescription() 
	{
		return description;
	}
	
	public void setDescription(String description) 
	{
		this.description=description;
	}

	
	public String getCity() 
	{
		return city;
	}
	
	public void setCity(String city) 
	{
		this.city=city;
	}

	/**
	 * This method creates an offer with a house number, first day, last day and price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */
	public Offer createOffer(Date firstDay, Date lastDay, float price)  
	{
        //System.out.println("LLAMADA RuralHouse createOffer, offerNumber="+" firstDay="+firstDay+" lastDay="+lastDay+" price="+price);
        Offer off=new Offer(firstDay,lastDay,price,this);
        offers.add(off);
        return off;
	}

	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + houseNumber.hashCode();
		return result;
	}

	@Override
	public String toString() 
	{
		return this.houseNumber + ": " + this.city;
	}

	@Override
	public boolean equals(Object obj) 
	{
		RuralHouse other = (RuralHouse) obj;
		if (this == obj)
		  return true;
		if (obj == null)
		  return false;
		if (getClass() != obj.getClass())
		  return false;
//		if (houseNumber != other.houseNumber) // NO COMPARAR AS� ya que houseNumber NO ES "int" sino objeto de "java.lang.Integer"
		if (!houseNumber.equals(other.houseNumber))
		  return false;
   	    return true;
	}
	
	/**
	 * This method obtains available offers for a concrete house in a certain period 
	 * 
	 * @param houseNumber, the house number where the offers must be obtained 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return a HashSet of offers(Offer class)  available  in this period
	 */
	public Vector<Offer> getOffers( Date firstDay,  Date lastDay) 
	{
		Vector<Offer> availableOffers=new Vector<Offer>();
		Iterator<Offer> e=offers.iterator();
		Offer offer;
		while (e.hasNext())
		{
			offer=e.next();
			if ( ((offer.getFirstDay().compareTo(firstDay) >= 0) && (offer.getLastDay().compareTo(lastDay) <= 0)) && (offer.getBook().equals("NO"))) 
			{
					availableOffers.add(offer);
			}
		}
		return availableOffers;
	}
	
	
	/**
	 * This method obtains the first offer that overlaps with the provided dates
	 * 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there is no overlapping offer
	 */

	public Offer overlapsWith( Date firstDay,  Date lastDay) 
	{
		Iterator<Offer> e = offers.iterator();
		Offer offer=null;
		while (e.hasNext())
		{
			offer=e.next();
			if ( (offer.getFirstDay().compareTo(lastDay)<0) && (offer.getLastDay().compareTo(firstDay)>0))
				return offer;
		}
		return null;
	}
	
}
