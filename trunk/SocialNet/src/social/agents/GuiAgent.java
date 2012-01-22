package social.agents;

import javax.swing.JFrame;


import gui.GUIFrame;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class GuiAgent extends Agent {

	public static String changeIdea = "chageIdea";
	public static String addFirend = "addFirend";
	public static String removeFirend = "removeFirend";
	public static String addPerson = "addPerson";
	public static String addIdea = "addIdea";	

	private class ListenBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage msg = myAgent.receive();

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
				
				if (msg.getOntology() == removeFirend) {
					String[] data = msg.getContent().split(":");
					removeFried(data[0], data[1]);
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
		GUIFrame frame = new GUIFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void changePeopleIdea(String peopleId, String ideaId) {
		// TODO
	}

	public void addFriend(String toID, String whoID){
		//TODO
	}
	
	public void removeFried(String toID, String whoID){
		//TODO
	}
	
	public void addPeople(String id, String ideaID, int latitiude, int logitiude){
		//TODO
	}
	
	public void addIdea(String ideaID){
		//TODO		
	}
}
