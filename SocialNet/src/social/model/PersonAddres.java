package social.model;

import jade.core.AID;

public class PersonAddres {
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
