package social.agents;

import java.util.Map;

import social.model.FriendInfo;
import social.model.Person;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class LocalAgent extends Agent {

	public static String addFriend = "addFriend";
	public static String removeFriend = "removeFriend";
	public static String mediaInfuence = "mediaInfuence";
	public static String statusChange = "statusChange";

	Map<String, Person> persons;

	private class AgentBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage message = myAgent.receive();

			try {
				if (message.getOntology() == addFriend) {

					String personId = message.getContent();

					if (persons.containsKey(personId)) {
						Object data = message.getContentObject();

						if (data instanceof FriendInfo) {
							FriendInfo info = (FriendInfo) data;
							Person person = persons.get(personId);
							if (person.newFriendRequest(info)) {
								ACLMessage response = new ACLMessage(
										ACLMessage.AGREE);
								response.addReceiver(info.getParentAID());
								response.setSender(myAgent.getAID());
								response.setOntology(addFriend);
								response.setContent(personId);

								FriendInfo newInfo = new FriendInfo(person);
								response.setContentObject(newInfo);

								myAgent.send(response);
							}
						} else {
							System.out.println("bad data");
						}
					} else {
						System.out.println("no such agent:" + personId);
					}
					return;
				}

				if (message.getOntology() == removeFriend) {
					
					
					if(message.getPerformative() == ACLMessage.AGREE){
						String[] data = message.getContent().split(":");
						
						if (persons.containsKey(data[0])) {
							Person person = persons.get(data[0]);
							
							person.removeFried(data[1]);
						}
						else{
							System.out.println("no such friend");
						}
					}
					else{
						String personId = message.getContent();
						
						if(persons.containsKey(personId)){
							Person person = persons.get(personId);
							FriendInfo info = person.removeFriend();
							
							if(info != null){
								ACLMessage replay = new ACLMessage(ACLMessage.AGREE);
								
								replay.addReceiver(info.getParentAID());
								replay.setSender(myAgent.getAID());
								replay.setOntology(removeFriend);
								replay.setContent(info.getId()+":"+personId);
								
								myAgent.send(replay);
							}
						}
						
					}
					
					return;
				}
				
				
				if(message.getOntology() == mediaInfuence){
					
					String data[] = message.getContent().split(":");
					
					Person person = persons.get(data[0]);
					if(person.recalculateIdea(data[1], new Integer(data[2])) != null){
						for (FriendInfo info : person.getFriends().values()) {
							sendStatusChangeMessage(person, info);
						}
					}
					
					return;
				}
				
				if(message.getOntology() == statusChange){
					
					
					String[] data = message.getContent().split(":");
					Person person  = persons.get(data[0]);
					if(person.statusChange(data[1], data[2], new Integer(data[3])) != null){
						for (FriendInfo info : person.getFriends().values()) {
							sendStatusChangeMessage(person, info);
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	
	public void sendStatusChangeMessage(Person person, FriendInfo info){
		
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.addReceiver(person.getParentAID());
		message.setSender(getAID());
		message.setOntology(statusChange);
		message.setContent(info.getId()+":"+person.getId()+":"+person.getIdeaId()+":"+person.getIdeaSurance());
		
		send(message);
	}

}
