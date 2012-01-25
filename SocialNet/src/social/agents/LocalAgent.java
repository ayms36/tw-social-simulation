package social.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.HashMap;
import java.util.Map;

import social.model.FriendInfo;
import social.model.Person;

public class LocalAgent extends Agent {

	public static String addFriend = "addFriend";
	public static String removeFriend = "removeFriend";
	public static String mediaInfuence = "mediaInfuence";
	public static String statusChange = "statusChange";
	public static String addPerson = "addPerson";

	Map<String, Person> persons = new HashMap<String, Person>();

	AID dbAddres;
	AID guiAddress;

	private class AgentBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage message = myAgent.blockingReceive();

			try {

				if (message.getOntology().equals(addFriend)) {

					Object data = message.getContentObject();

					if (data instanceof FriendInfo) {
						FriendInfo info = (FriendInfo) data;
						String personId = info.getToId();

						Person person = persons.get(personId);

						if (person != null && person.newFriendRequest(info)) {

							ACLMessage response = new ACLMessage(
									ACLMessage.AGREE);
							response.addReceiver(info.getParentAID());
							response.setOntology(addFriend);
							response.setContent(personId);

							FriendInfo newInfo = new FriendInfo(person);
							response.setContentObject(newInfo);

							myAgent.send(response);

							ACLMessage toGui = new ACLMessage(ACLMessage.INFORM);
							toGui.addReceiver(guiAddress);
							toGui.setContent(personId + ":" + info.getId());
							toGui.setOntology(GuiAgent.addFirend);
							send(toGui);

							
							if (person.recalculateIdea() != null) {
								for (FriendInfo info1 : person.getFriends().values()) {
									sendStatusChangeMessage(person, info1);
								}

								ACLMessage toGui1 = new ACLMessage(ACLMessage.INFORM);
								toGui1.addReceiver(guiAddress);
								toGui1.setContent(person.getId() + ":"
										+ person.getIdeaId());
								toGui1.setOntology(GuiAgent.changeIdea);
								send(toGui1);

								System.out.println("Person" + person.getId()
										+ " chage his mind to :" + person.getIdeaId());
							}
							
							
							System.out.println("I : " + personId
									+ " added friend: " + info.getId());
						}
					} else {
						System.out.println("bad data");
					}
					return;
				}

				if (message.getOntology().equals(removeFriend)) {

					if (message.getPerformative() == ACLMessage.AGREE) {
						String[] data = message.getContent().split(":");

						if (persons.containsKey(data[0])) {
							Person person = persons.get(data[0]);

							person.removeFried(data[1]);

							System.out.println("friends removed!");

							ACLMessage toGui = new ACLMessage(ACLMessage.INFORM);
							toGui.addReceiver(guiAddress);
							toGui.setContent(data[1] + ":" + person.getId());
							toGui.setOntology(GuiAgent.removeFriend);
							send(toGui);

						} else {
							System.out.println("no such friend");
						}
					} else {
						String personId = message.getContent();

						if (persons.containsKey(personId)) {
							Person person = persons.get(personId);
							FriendInfo info = person.removeFriend();

							if (info != null) {
								ACLMessage replay = new ACLMessage(
										ACLMessage.AGREE);

								replay.addReceiver(info.getParentAID());
								replay.setSender(myAgent.getAID());
								replay.setOntology(removeFriend);
								replay.setContent(info.getId() + ":" + personId);

								myAgent.send(replay);

								System.out.println("friends removed!");
							}
						}

					}

					return;
				}

				if (message.getOntology().equals(mediaInfuence)) {

					String data[] = message.getContent().split(":");

					Person person = persons.get(data[0]);
					if (person.recalculateIdea(data[1], new Double(data[2])) != null) {
						for (FriendInfo info : person.getFriends().values()) {
							sendStatusChangeMessage(person, info);
						}

						ACLMessage toGui = new ACLMessage(ACLMessage.INFORM);
						toGui.addReceiver(guiAddress);
						toGui.setContent(person.getId() + ":"
								+ person.getIdeaId());
						toGui.setOntology(GuiAgent.changeIdea);
						send(toGui);

						System.out.println("Person" + person.getId()
								+ " chage his mind to :" + person.getIdeaId());
					}

					return;
				}

				if (message.getOntology().equals(statusChange)) {

					String[] data = message.getContent().split(":");
					Person person = persons.get(data[0]);
					if (person != null
							&& person.statusChange(data[1], data[2],
									new Integer(data[3])) != null) {
						for (FriendInfo info : person.getFriends().values()) {
							sendStatusChangeMessage(person, info);
						}

						ACLMessage toGui = new ACLMessage(ACLMessage.INFORM);
						toGui.addReceiver(guiAddress);
						toGui.setContent(person.getId() + ":"
								+ person.getIdeaId());
						toGui.setOntology(GuiAgent.changeIdea);
						send(toGui);

						System.out.println("Person" + person.getId()
								+ " chage his mind to :" + person.getIdeaId());
					}
				}

				if (message.getOntology().equals(addPerson)) {

					Person p = (Person) message.getContentObject();
					persons.put(p.getId(), p);

					ACLMessage toGui = new ACLMessage(ACLMessage.INFORM);
					toGui.addReceiver(guiAddress);
					toGui.setContent(p.getId() + ":" + p.getIdeaId() + ":"
							+ p.getLatituide() + ":" + p.getLogitiude());
					toGui.setOntology(GuiAgent.addPerson);
					send(toGui);
					

					System.out.println("Person" + p.getId() + " added to Local");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public LocalAgent() {
		addBehaviour(new AgentBehaviour());

		guiAddress = new AID(GuiAgent.GuiAgentName, false);

	}

	public void sendStatusChangeMessage(Person person, FriendInfo info) {

		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(person.getParentAID());
		message.setSender(getAID());
		message.setOntology(statusChange);
		message.setContent(info.getId() + ":" + person.getId() + ":"
				+ person.getIdeaId() + ":" + person.getIdeaSurance());

		send(message);
	}

}
