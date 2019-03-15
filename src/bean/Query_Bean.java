package bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.ApplicationFacadeInterfaceWS;
import domain.Offer;
import domain.RuralHouse;
import bean.Facade_Bean;

public class Query_Bean 
{

	Date day;
	String numNights;
	RuralHouse houseCode;
	List<Offer> offers;
	List<RuralHouse> houses;
	Offer bookOffer;
	String error;
	boolean showError;

	public Offer getBookOffer() 
	{
		return bookOffer;
	}

	public void setBookOffer(Offer bookOffer) 
	{
		this.bookOffer = bookOffer;
	}

	public static ApplicationFacadeInterfaceWS facadeBL;

	public Query_Bean() 
	{
		facadeBL = Facade_Bean.getBusinessLogic();
		houses = facadeBL.getAllRuralHouses();
	}

	public Date getDay() 
	{
		return day;
	}

	public void setDay(Date day) 
	{
		this.day = day;
	}

	public String getNumNights() 
	{
		return numNights;
	}

	public void setNumNights(String numNights) 
	{
		this.numNights = numNights;
	}

	public RuralHouse getHouseCode() 
	{
		return houseCode;
	}

	public void setHouseCode(RuralHouse houseCode) 
	{
		this.houseCode = houseCode;
	}

	public List<Offer> getOffers() 
	{
		return offers;
	}

	public void setOffers(List<Offer> offers) 
	{
		this.offers = offers;
	}

	public List<RuralHouse> getHouses() 
	{
		return houses;
	}

	public void setHouses(List<RuralHouse> houses)
	{
		this.houses = houses;
	}

	public void onDateSelect(SelectEvent event) 
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
	}

	public void searchOffers() 
	{
		if (day != null) 
		{
			try 
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(day);
				int numDays = Integer.parseInt(numNights);
				calendar.add(Calendar.DATE, numDays);
				Date lastDay = calendar.getTime();
				offers = facadeBL.getOffers(houseCode, day, lastDay);
				error = "Offer searched";
				showError = true;
			} 
			catch (NumberFormatException e) 
			{
				error = "Number of nights incorrect";
				showError = true;
			}
		} 
		else 
		{
			error = "First Day != null";
			showError = true;
		}
	}

	public void book() 
	{
		try 
		{
			facadeBL.bookOffer(bookOffer);
			searchOffers();
			error = "Offer Booked";
			showError = true;
		} 
		catch (Exception ex) 
		{
			error = "No Offer Selected";
			showError = true;
		}
	}

	public String getError() 
	{
		return error;
	}

	public void setError(String error) 
	{
		this.error = error;
	}

	public boolean isShowError() 
	{
		return showError;
	}

	public void setShowError(boolean showError) 
	{
		this.showError = showError;
	}

	public void ErrorFalse() 
	{
		showError = false;
	}

	public void borrar() 
	{
		day = null;
		numNights = "";
		offers = null;
	}

}