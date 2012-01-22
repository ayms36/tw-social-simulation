package social.model;

import jade.core.AID;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Person {

	static Random random = new Random(System.nanoTime());

	String id;

	String ideaId;

	Integer ideaSurance;

	Integer latituide, logitiude;
	
	Map<String, FriendInfo> friends = new HashMap<String, FriendInfo>();

	AID parentAID;

	public String recalculateIdea() {
		return recalculateIdea(this.ideaId, 0);
	}

	public String recalculateIdea(String ideaId, Integer infuance) {

		Map<String, Integer> ideas = new HashMap<String, Integer>();

		ideas.put(ideaId, infuance);

		for (FriendInfo friendInfo : friends.values()) {
			Integer tmp = ideas.containsKey(friendInfo.getIdeaId()) ? 0 : ideas
					.get(friendInfo.ideaId);
			tmp += friendInfo.getIdeaSurence() / friendInfo.getInfuence();
			ideas.put(friendInfo.getIdeaId(), tmp);
		}

		String newIdea = ideaId;
		Integer newSurance = infuance;

		for (String idea : ideas.keySet()) {

			if (ideas.get(idea) > infuance) {
				infuance = ideas.get(idea);
				newIdea = idea;
			}

		}

		this.ideaSurance = newSurance;

		if (newIdea == this.ideaId) {
			return null;
		}

		this.ideaId = newIdea;

		return newIdea;
	}

	public boolean newFriendRequest(FriendInfo info) {

		if (!friends.containsKey(info.getId()) && random.nextBoolean()) {
			info.setInfuence(random.nextInt(StaticValues.maxInfuence));
			friends.put(info.getId(), info);
			return true;
		}
		return false;
	}

	
	public String statusChange(String friendID, String ideaID, int surance){
		
		FriendInfo info = friends.get(friendID);
		if(info != null){
			
			info.setIdeaId(ideaID);
			info.setIdeaSurence(surance);
			info.setInfuence(random.nextInt(StaticValues.maxInfuence));
			
			recalculateIdea();
		}
		
		
		return null;
	}
	
	public void removeFried(String personID){
		friends.remove(personID);
	}
	
	public FriendInfo removeFriend() {
		String toDel = null;

		if (random.nextBoolean()) {
			for (String id : friends.keySet()) {
				if (random.nextInt() == 3) {
					toDel = id;
					break;
				}
			}
		}

		if (toDel != null) {
			return friends.remove(toDel);
		}
		return null;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(String ideaId) {
		this.ideaId = ideaId;
	}

	public Integer getIdeaSurance() {
		return ideaSurance;
	}

	public void setIdeaSurance(Integer ideaSurance) {
		this.ideaSurance = ideaSurance;
	}

	public Integer getLatituide() {
		return latituide;
	}

	public void setLatituide(Integer latituide) {
		this.latituide = latituide;
	}

	public Integer getLogitiude() {
		return logitiude;
	}

	public void setLogitiude(Integer logitiude) {
		this.logitiude = logitiude;
	}

	public Map<String, FriendInfo> getFriends() {
		return friends;
	}

	public void setFriends(Map<String, FriendInfo> friends) {
		this.friends = friends;
	}

	public AID getParentAID() {
		return parentAID;
	}

	public void setParentAID(AID parentAID) {
		this.parentAID = parentAID;
	}



}
