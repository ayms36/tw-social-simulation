package social.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.concurrent.TimeUnit;

import social.model.PersonAddres;

public class MediaAgent extends Agent {

	int peopleInOneCycle = 5;

	int sleepTime = 100;

	int infunece = 10;

	String idea = null;

	AID dbAddres;
	
	private class MediaBehaviour extends CyclicBehaviour {

		@Override
		public void action() {

			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.setOntology(PersonDB.getRandomPersons);
			request.setSender(myAgent.getAID());
			request.addReceiver(dbAddres);
			myAgent.send(request);
			
			
			ACLMessage message = myAgent.receive();
			
			try {
				
				Object data = message.getContentObject();
				
				if (message.getOntology() == PersonDB.getRandomPersons
						& (message.getContentObject() instanceof PersonAddres[])) {
					
					PersonAddres[] addresses = (PersonAddres[]) data;
					for (PersonAddres personAddres : addresses) {
						sendMessageTo(personAddres.getManagerAID(), personAddres.getPersonId());
					}
					
					TimeUnit.MILLISECONDS.sleep(sleepTime);
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	
	public MediaAgent() {
		super();
		
		dbAddres = new AID(PersonDB.PresonDBAID, true);
		
		addBehaviour(new MediaBehaviour());
		
	}
	
	
	public int getPeopleInOneCycle() {
		return peopleInOneCycle;
	};

	public int getSleepTime() {
		return sleepTime;
	};

	/**
	 * 
	 * @param sleepTime
	 *            in millisecond
	 */
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void setPeopleInOneCycle(int peopleInOneCycle) {
		this.peopleInOneCycle = peopleInOneCycle;
	}

	public int getInfunece() {
		return infunece;
	}

	public void setInfunece(int infunece) {
		this.infunece = infunece;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public void sendMessageTo(AID agent, String personId) {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		
		message.addReceiver(agent);
		message.setContent(personId+":"+idea+":"+infunece);
		message.setSender(getAID());
		message.setOntology(LocalAgent.mediaInfuence);
		send(message);
	}

}
