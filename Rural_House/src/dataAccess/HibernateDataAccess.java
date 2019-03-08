package dataAccess;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;

import domain.Offer;
import domain.RuralHouse;

import exceptions.OverlappingOfferExists;
import modelo.dominio.HibernateUtil;

public class HibernateDataAccess {

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Integer houseCode = ruralHouse.getHouseNumber();
			List<RuralHouse> query = session.createQuery("from RuralHouse where houseNumber='" + houseCode + "'")
					.list();
			RuralHouse rh = query.get(0);
			Offer o = rh.createOffer(firstDay, lastDay, price);
			session.save(o);
			session.getTransaction().commit();
			return o;
		} catch (Exception e) {
			return null;
		}

	}

	public Vector<RuralHouse> getAllRuralHouses() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Vector<RuralHouse> allRuralHouses = new Vector<RuralHouse>();
		@SuppressWarnings("unchecked")
		List<RuralHouse> query = session.createQuery("from RuralHouse").list();
		session.getTransaction().commit();
		Iterator<RuralHouse> i = query.iterator();
		while (i.hasNext()) {
			allRuralHouses.add(i.next());
		}
		return allRuralHouses;
	}

	public Vector<Offer> getOffers(RuralHouse rh, Date firstDay, Date lastDay) {
		Integer houseCode = rh.getHouseNumber();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Vector<Offer> allOffers = new Vector<Offer>();
		List<RuralHouse> query = session.createQuery("from RuralHouse where houseNumber='" + houseCode + "'").list();
		RuralHouse rh1 = (RuralHouse) query.get(0);
		allOffers = rh1.getOffers(firstDay, lastDay);
		return allOffers;
	}

	public boolean existsOverlappingOffer(RuralHouse rh, Date firstDay, Date lastDay) throws OverlappingOfferExists {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			Integer houseCode = rh.getHouseNumber();
			List<RuralHouse> query = session.createQuery("from RuralHouse where houseNumber='" + houseCode + "'")
					.list();
			RuralHouse rhn = query.get(0);
			if (rhn.overlapsWith(firstDay, lastDay) != null)
				return true;
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	public void bookOffer(Offer o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		int offerNumber = o.getOfferNumber();
		session.createQuery("update Offer set book = 'YES' where offerNumber = '" + offerNumber + "'").executeUpdate();
		session.getTransaction().commit();
	}

	public boolean coincide(RuralHouse houseCode, Date firstDay, Date lastDay) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List<Offer> query = session.createQuery("from Offer where houseNumber='" + houseCode + "'").list();
		int cont = 0;
		for (int i = 0; i < query.size(); i++) {
			if (((firstDay.compareTo(query.get(i).getFirstDay()) >= 0)
					&& (lastDay.compareTo(query.get(i).getLastDay()) <= 0))
					|| ((firstDay.compareTo(query.get(i).getFirstDay()) <= 0)
							&& (lastDay.compareTo(query.get(i).getLastDay()) >= 0))
					|| ((firstDay.compareTo(query.get(i).getFirstDay()) >= 0)
							&& (firstDay.compareTo(query.get(i).getLastDay()) <= 0))) {
				cont++;
			}
		}
		if (cont > 0) {
			session.getTransaction().commit();
			return true;
		} else {
			session.getTransaction().commit();
			return false;
		}
	}
}