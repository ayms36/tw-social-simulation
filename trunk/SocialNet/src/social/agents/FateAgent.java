package social.agents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import social.model.FriendInfo;
import social.model.LocalAgentInfo;
import social.model.Person;
import social.model.StaticValues;
import social.util.NameGenerator;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class FateAgent extends Agent {

	static Random random = new Random(System.nanoTime());

	Map<String, AID> persons = new HashMap<String, AID>();

	List<LocalAgentInfo> infos = new ArrayList<LocalAgentInfo>();

	List<String> ideas = new ArrayList<String>();

	Integer maxPerson = 20;
	
	AID guiAddress;
	
	int maxIdeasSize = 10;

	class FateBehavoir extends CyclicBehaviour {
		
		

		@Override
		public void action() {
			
			if(maxIdeasSize > ideas.size()){
				addIdea(NameGenerator.generateNewIdeaName());
			}

			int status = random.nextInt(10);

			switch (status) {
			case 1:
				newPersonRequest();
				break;
			case 2:
				addNewFriendRequest();
				break;
			case 3:
				removeFriendRequest();
				break;
			default:
				break;
			}
			try {
				TimeUnit.MILLISECONDS.sleep(random
						.nextInt(StaticValues.maxSleepTime) + StaticValues.minSleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void newPersonRequest() {

		if (maxPerson > persons.size()) {

			Person person = new Person();

			LocalAgentInfo info = infos.get(random.nextInt(infos.size()));

			person.setLatituide(info.getRandomLat());
			person.setLogitiude(info.getRandomLng());
			person.setParentAID(info.getAid());
			try {
				person.setId(NameGenerator.generateNewPersonName());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			person.setIdeaId(ideas.get(random.nextInt(ideas.size())));
			person.setIdeaSurance(StaticValues.startSurance);
			try {
				ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
				message.addReceiver(info.getAid());
				message.setContentObject(person);
				message.setOntology(LocalAgent.addPerson);

				System.out.println(info.getAid().getName());

				send(message);

				persons.put(person.getId(), person.getParentAID());

				System.out.println("Prson " + person.getId() + " added");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void addNewFriendRequest() {
		try {
			if (persons.size() > 0) {

				Set<String> keys = persons.keySet();
				int posTo = random.nextInt(keys.size() - 1);
				int posWho = random.nextInt(keys.size() - 1);

				String keyTo = null, keyWho = null;
				int i = 0;
				for (String string : keys) {

					if (i == posTo) {
						keyTo = string;
					}
					if (i == posWho) {
						keyWho = string;
					}
					i++;
				}

				FriendInfo info = new FriendInfo(keyTo);
				info.setId(keyWho);
				info.setIdeaId("");
				info.setIdeaSurence(0);
				info.setParentAID(persons.get(keyWho));
				
				System.out.println("nfr: " + keyTo + " " + keyWho + " !!!!!!!!");

				ACLMessage message = new ACLMessage(ACLMessage.INFORM);
				message.setOntology(LocalAgent.addFriend);
				message.setContentObject(info);
				message.addReceiver(persons.get(keyTo));

				send(message);

				System.out.println("New friend request sends");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeFriendRequest() {

	}
	
	public void addIdea(String ideaID){
		
		ideas.add(ideaID);
		ACLMessage toGui = new ACLMessage(ACLMessage.INFORM);
		toGui.addReceiver(guiAddress);
		toGui.setContent(ideaID);
		toGui.setOntology(GuiAgent.addIdea);
		send(toGui);
		
		System.out.println("Idea created");
	}

	public FateAgent() {		
		guiAddress = new AID(GuiAgent.GuiAgentName, false);
		

		
		AID local = new AID("europa", false);
		LocalAgentInfo info = new LocalAgentInfo();
		info.setAid(local);
		info.setLat(100);
		info.setLng(100);
		info.setLatR(10);
		info.setLngR(10);

		infos.add(info);

		addBehaviour(new FateBehavoir());

		System.out.println("Fate ready!");
	}

}
