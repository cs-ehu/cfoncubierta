package bean;

import businessLogic.ApplicationFacadeInterfaceWS;
import businessLogic.FacadeImplementationWS;

public class Facade_Bean {

	public static final ApplicationFacadeInterfaceWS facadeBl = new FacadeImplementationWS();

	public static ApplicationFacadeInterfaceWS getBusinessLogic() {
		return facadeBl;
	}
}