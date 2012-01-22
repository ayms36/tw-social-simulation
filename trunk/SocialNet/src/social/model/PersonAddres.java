package social.model;

import java.io.Serializable;

import jade.core.AID;

public class PersonAddres  implements Serializable{

	
	private static final long serialVersionUID = 270179208877056610L;
	
	String personId;
	AID managerAID;
	
	public AID getManagerAID() {
		return managerAID;
	}
	
	public String getPersonId() {
		return personId;
	};
	
	public PersonAddres(String personId, AID managerID) {
		this.personId = personId;
		this.managerAID = managerID;
	}
}