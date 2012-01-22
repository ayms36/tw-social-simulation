package social.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import social.model.PersonAddres;
import social.model.StaticValues;

public class MediaAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3344327432395909508L;

	int peopleInOneCycle = 5;

	int sleepTime = 100;

	int infunece = 10;

	String idea = null;

	AID dbAddres;

	static Random random = new Random(System.nanoTime());

	private class MediaBehaviour extends CyclicBehaviour {

		private static final long serialVersionUID = -4918662739985451266L;

		@Override
		public void action() {
			
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.setOntology(PersonDB.getRandomPersons);
			request.addReceiver(dbAddres);
			request.setContent(peopleInOneCycle+"");
			myAgent.send(request);


			
			ACLMessage message = myAgent.blockingReceive();

		
			
			System.out.println("mam:" + message.getOntology() + " "
					+ message.getSender().getName());

			
			try {


				if (message.getOntology().equals(PersonDB.getRandomPersons)
						& (message.getContentObject() instanceof PersonAddres)) {

					PersonAddres data = (PersonAddres) message.getContentObject();

					

						sendMessageTo(data.getManagerAID(),
								data.getPersonId());
					

					TimeUnit.MILLISECONDS.sleep(sleepTime
							+ random.nextInt(StaticValues.maxSleepTime));

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public MediaAgent() {
		this("idea", 12);
	}

	public MediaAgent(String ideaID, int infuence) {
		super();

		this.idea = ideaID;
		this.infunece = infuence;

		dbAddres = new AID(PersonDB.PresonDBAID, false);

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
		message.setContent(personId + ":" + idea + ":" + infunece);
		message.setOntology(LocalAgent.mediaInfuence);
		send(message);
		
		System.out.println("Infuence sends");
	}

}
