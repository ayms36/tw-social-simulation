package social.agents;

import java.util.HashMap;
import java.util.Random;

import social.model.PersonAddres;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

/**
 * 
 * Recievies messages and sends answers answers
 * 
 * @author jar
 */

public class PersonDB extends Agent {
	
	public static String getPersonMaster = "getPersonMaster";
	public static String getRandomPersons = "getRandomPersons";
	public static String addPerson = "addPerson";
	
	public static String PresonDBAID = "PresonDBAID";
	

	HashMap<String, AID> db = new HashMap<String, AID>();

	Random r = new Random(System.nanoTime());

	private class ListenBehavior extends CyclicBehaviour {
		@Override
		public void action() {

			ACLMessage message = myAgent.receive();

			Object data;
			try {
				data = message.getContentObject();

				if (message.getOntology() == addPerson
						& data instanceof PersonAddres) {
					addPerson((PersonAddres) data);

					return;
				}

				if (message.getOntology() == getPersonMaster) {

					AID manager = getPersonMaster(message.getContent());

					ACLMessage response = new ACLMessage(ACLMessage.INFORM);
					response.setOntology(getPersonMaster);
					response.setContent(message.getContent());
					response.setSender(myAgent.getAID());
					response.addReceiver(message.getSender());
					response.setContentObject(manager);
					myAgent.send(response);

					return;
				}

				if (message.getOntology() == getRandomPersons) {
					
					PersonAddres[] addresses = getRandomPersons(new Integer(message.getContent()));
					
				
					ACLMessage response = new ACLMessage(ACLMessage.INFORM);
					response.setOntology(getRandomPersons);
					response.setSender(myAgent.getAID());
					response.addReceiver(message.getSender());
					response.setContentObject(addresses);
					myAgent.send(response);
					
					return;
				}
				
				System.out.println("Unknow message!");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	void addPerson(PersonAddres addres) {
		db.put(addres.getPersonId(), addres.getManagerAID());
	}

	public AID getPersonMaster(String personID) {
		return db.get(personID);
	}

	public PersonAddres[] getRandomPersons(int count) {
		PersonAddres[] persons = new PersonAddres[count];

		int pos = 0;

		for (String personId : db.keySet()) {
			if (r.nextBoolean()) {
				persons[pos] = new PersonAddres(personId, db.get(personId));
				pos++;
				if (pos >= persons.length) {
					break;
				}
			}
		}

		return persons;
	}

	@Override
	protected void setup() {
		super.setup();

		addBehaviour(new ListenBehavior());
	}

}
