package social.agents;

import javax.swing.JFrame;


import gui.GUIFrame;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class GuiAgent extends Agent {

	public static String changeIdea = "chageIdea";
	public static String addFirend = "addFirend";
	public static String removeFriend = "removeFriend";
	public static String addPerson = "addPerson";
	public static String addIdea = "addIdea";	

	private GUIFrame frame;
	private class ListenBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage msg = myAgent.blockingReceive();

			System.out.println("mam:" + msg.getOntology() + " "
					+ msg.getSender().getName());

			
			try {
				if (msg.getOntology() == changeIdea) {
					String[] data = msg.getContent().split(":");
					changePeopleIdea(data[0], data[1]);
					return;
				}
				
				if (msg.getOntology() == addFirend) {
					String[] data = msg.getContent().split(":");
					addFriend(data[0], data[1]);
					return;
				}
				
				if (msg.getOntology() == removeFriend) {
					String[] data = msg.getContent().split(":");
					removeFriend(data[0], data[1]);
					return;
				}
				

				if (msg.getOntology() == addPerson) {
					String[] data = msg.getContent().split(":");
					addPeople(data[0], data[1], new Integer(data[2]), new Integer(data[3]));
					return;
				}

				if (msg.getOntology() == addIdea) {
					addIdea(msg.getContent());
					return;
				}
				System.out.println("Unknown message!");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	protected void setup() {
		super.setup();

		addBehaviour(new ListenBehaviour());
		
		init();
	}
	
	public void init(){
		frame = new GUIFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void changePeopleIdea(String peopleId, String ideaId) {
		frame.changePeopleIdea(peopleId,ideaId);
	}

	public void addFriend(String toID, String whoID){
		frame.addFriend(toID, whoID);
	}
	
	public void removeFriend(String toID, String whoID){
		frame.removeFriend(toID,whoID);
	}
	
	public void addPeople(String id, String ideaID, int latitude, int longitude){
		frame.addPeople(id,ideaID,latitude,longitude);
	}
	
	public void addIdea(String ideaID){
		frame.addIdea(ideaID);
	}
}
