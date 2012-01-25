package social.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.BufferedReader;
import java.io.FileReader;
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

public class FateAgent extends Agent {


	private static final long serialVersionUID = -7146759194526514370L;

	static Random random = new Random(System.nanoTime());

	Map<String, AID> persons = new HashMap<String, AID>();

	List<LocalAgentInfo> infos = new ArrayList<LocalAgentInfo>();

	List<String> ideas = new ArrayList<String>();

	Integer maxPerson = 20;

	AID guiAddress;

	int maxIdeasSize = 10;

	class FateBehavoir extends CyclicBehaviour {
		
		private static final long serialVersionUID = 6407507627369369041L;

		@Override
		public void action() {

			int status = random.nextInt(10);

			try {
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
				case 4:
					ideaInfuence();
				case 5:
					ideaInfuence();
				
				default:
					break;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				TimeUnit.MILLISECONDS.sleep(random
						.nextInt(StaticValues.maxSleepTime)
						+ StaticValues.minSleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void newPersonRequest() {
		
		if(persons.size() > maxPerson){
			return;
		}

		Person person = new Person();

		if (infos.size() < 1) {
			return;
		}
		

		LocalAgentInfo info = infos.get(random.nextInt(infos.size()));

		person.setLatituide(info.getRandomLat());
		person.setLogitiude(info.getRandomLng());
		person.setParentAID(info.getAid());

		try {
			person.setId(NameGenerator.generateNewPersonName());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		person.setIdeaId(ideas.get(random.nextInt(ideas.size())));
		person.setIdeaSurance(StaticValues.startSurance);

		try {
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(info.getAid());
			message.setContentObject(person);
			message.setOntology(LocalAgent.addPerson);

			send(message);

			persons.put(person.getId(), person.getParentAID());
			
			System.out.println("Created" + person.getParentAID());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addNewFriendRequest() {
		try {
			if (persons.size() > 0) {

				Set<String> keys = persons.keySet();
				int posTo = random.nextInt(keys.size());
				int posWho = random.nextInt(keys.size());

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

				if (keyTo.equals(keyWho)) {
					return;
				}

				FriendInfo info = new FriendInfo(keyTo);
				info.setId(keyWho);
				info.setIdeaId("");
				info.setIdeaSurence(0);
				info.setParentAID(persons.get(keyWho));

				ACLMessage message = new ACLMessage(ACLMessage.INFORM);
				message.setOntology(LocalAgent.addFriend);
				message.setContentObject(info);
				message.addReceiver(persons.get(keyTo));

				send(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeFriendRequest() {

	}
	
	
	
	public void ideaInfuence() {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		
		int pos = random.nextInt(persons.size());
		int i = 0;
		String id = "";
		
		for (String p : persons.keySet()) {
			if(pos == i){
				id = p;
				break;
			}
			i++;
		}

		if(id == null){
			return;
		}
		
		pos = random.nextInt(ideas.size());
		String idea = "";
		i = 0;
		for (String p : ideas) {
			if(pos == i){
				idea = p;
				break;
			}
			i++;
		}
		
		message.addReceiver(persons.get(id));
		message.setContent(id + ":" + idea + ":" + random.nextInt(StaticValues.maxInfuence));
		message.setOntology(LocalAgent.mediaInfuence);
		send(message);
		
		System.out.println("Infuence sends");
	}

	public void addIdea(String ideaID) {

		ideas.add(ideaID);
		ACLMessage toGui = new ACLMessage(ACLMessage.INFORM);
		toGui.addReceiver(guiAddress);
		toGui.setContent(ideaID);
		toGui.setOntology(GuiAgent.addIdea);
		send(toGui);

		System.out.println("Idea" + ideaID + " created");
	}

	public FateAgent() {
		guiAddress = new AID(GuiAgent.GuiAgentName, false);
		
		// run db

		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"agent.conf"));
			String line;


			while ((line = reader.readLine()) != null) {
				try {
					String[] dataS = line.split(":");

					AID local = new AID(dataS[0], false);
					LocalAgentInfo info = new LocalAgentInfo();
					info.setAid(local);
					info.setLat(new Integer(dataS[1]));
					info.setLng(new Integer(dataS[3]));
					info.setLatR(new Integer(dataS[2]));
					info.setLngR(new Integer(dataS[4]));

					infos.add(info);

					System.out.println("create LocalAgent: " + dataS[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"idea.conf"));
			String line;



			while ((line = reader.readLine()) != null) {
				try {
					
					String[] dataS = line.split(":");

					addIdea(dataS[0]);

					System.out.println("create MediaAgent:" + dataS[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		addBehaviour(new FateBehavoir());

		System.out.println("Fate ready!");
	}

}
