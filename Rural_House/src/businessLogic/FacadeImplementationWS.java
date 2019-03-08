package businessLogic;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.hibernate.Session;

import dataAccess.HibernateDataAccess;

//import domain.Booking;
import domain.Offer;
import domain.RuralHouse;
import exceptions.BadDates;
import exceptions.OverlappingOfferExists;
import modelo.dominio.HibernateUtil;

@WebService(endpointInterface = "businessLogic.ApplicationFacadeInterfaceWS")
public class FacadeImplementationWS implements ApplicationFacadeInterfaceWS {

	/**
	 * 
	 */

	public FacadeImplementationWS() {

	}

	/**
	 * This method creates an offer with a house number, first day, last day and
	 * price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return the created offer, or null, or an exception
	 */
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price)
			throws OverlappingOfferExists, BadDates {
		HibernateDataAccess dbManager = new HibernateDataAccess();
		Offer o = null;

		if (firstDay.compareTo(lastDay) >= 0) {
			throw new BadDates();
		}

		boolean b = dbManager.existsOverlappingOffer(ruralHouse, firstDay, lastDay);
		if (!b)
			o = dbManager.createOffer(ruralHouse, firstDay, lastDay, price);
		return o;

	}

	public Vector<RuralHouse> getAllRuralHouses() {
		HibernateDataAccess dbManager = new HibernateDataAccess();
		Vector<RuralHouse> ruralHouses = dbManager.getAllRuralHouses();
		return ruralHouses;
	}

	/**
	 * This method obtains the offers of a ruralHouse in the provided dates
	 * 
	 * @param ruralHouse,
	 *            the ruralHouse to inspect
	 * @param firstDay,
	 *            first day in a period range
	 * @param lastDay,
	 *            last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there is
	 *         no overlapping offer
	 */

	@WebMethod
	public Vector<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {
		HibernateDataAccess dbManager = new HibernateDataAccess();
		Vector<Offer> offers = new Vector<Offer>();
		offers = dbManager.getOffers(rh, firstDay, lastDay);
		return offers;
	}

	public void initializeBD() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			@SuppressWarnings("unchecked")
			List<RuralHouse> query = session.createQuery("FROM RuralHouse").list();
			Iterator<RuralHouse> i = query.iterator();
			while (i.hasNext()) {
				RuralHouse rh = i.next();
				session.delete(rh);
			}
			RuralHouse rh1 = new RuralHouse("Ezkioko etxea", "Ezkio");
			RuralHouse rh2 = new RuralHouse("Etxetxikia", "Iruna");
			RuralHouse rh3 = new RuralHouse("Udaletxea", "Bilbo");
			RuralHouse rh4 = new RuralHouse("Gaztetxea", "Renteria");
			session.save(rh1);
			session.save(rh2);
			session.save(rh3);
			session.save(rh4);
			session.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void bookOffer(Offer bookOffer) {
		HibernateDataAccess HDA = new HibernateDataAccess();
		HDA.bookOffer(bookOffer);
	}

	@Override
	public boolean coincide(RuralHouse houseCode, Date firstDay, Date lastDay) {
		HibernateDataAccess HDA = new HibernateDataAccess();
		boolean a = HDA.coincide(houseCode, firstDay, lastDay);
		return a;
	}

}
