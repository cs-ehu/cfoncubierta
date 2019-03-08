package bean;

import java.util.Vector;

import businessLogic.ApplicationFacadeInterfaceWS;
import businessLogic.FacadeImplementationWS;
import domain.RuralHouse;

public class Initialize {

	public static void main(String[] args) {
		ApplicationFacadeInterfaceWS facadeBl = new FacadeImplementationWS();
		facadeBl.initializeBD();
		Vector<RuralHouse> houses = facadeBl.getAllRuralHouses();
	}

}
