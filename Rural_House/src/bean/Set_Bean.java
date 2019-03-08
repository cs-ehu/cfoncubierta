package bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import bean.Facade_Bean;
import businessLogic.ApplicationFacadeInterfaceWS;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;

public class Set_Bean {

	private RuralHouse houseCode;
	private int price;
	private Date firstDay;
	private Date lastDay;
	String error;
	boolean showError;
	private List<RuralHouse> houses;

	private ApplicationFacadeInterfaceWS facadeBL;

	public Set_Bean() {
		facadeBL = Facade_Bean.getBusinessLogic();
		houses = facadeBL.getAllRuralHouses();
	}

	public RuralHouse getHouseCode() {
		return houseCode;
	}

	public void setHouseCode(RuralHouse houseCode) {
		this.houseCode = houseCode;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(Date firstDay) {
		this.firstDay = firstDay;
	}

	public Date getLastDay() {
		return lastDay;
	}

	public void setLastDay(Date lastDay) {
		this.lastDay = lastDay;
	}

	public List<RuralHouse> getHouses() {
		return houses;
	}

	public void setHouses(List<RuralHouse> houses) {
		this.houses = houses;
	}

	public void onDateSelect(SelectEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
	}

	public String createOffer() {
		if ((firstDay != null) || (lastDay != null) && (firstDay.compareTo(lastDay) < 0) && (price > 0)) {
			try {
				if (facadeBL.coincide(houseCode, firstDay, lastDay) == false) {
					facadeBL.createOffer(houseCode, firstDay, lastDay, price);
					error = "Offer create";
					showError = true;
					borrar();
				} else {
					error = "Overlapping Offer";
					showError = true;
				}
			} catch (OverlappingOfferExists e) {
				error = "Overlapping Offer";
				showError = true;
			} catch (BadDates e) {
				error = "Bad Dates: firstDay < lastDay";
				showError = true;
			} catch (Exception e) {
				error = "Error";
				showError = true;
			}
			return null;
		} else if (price <= 0) {
			error = "Price > 0";
			showError = true;
			return null;
		} else {
			error = "Dates!=null";
			showError = true;
			return null;
		}

	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isShowError() {
		return showError;
	}

	public void setShowError(boolean showError) {
		this.showError = showError;
	}

	public void errorFalse() {
		showError = false;
	}

	public void borrar() {
		price = 0;
		firstDay = null;
		lastDay = null;
	}

}
