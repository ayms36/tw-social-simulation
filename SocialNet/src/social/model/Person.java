package social.model;

import jade.core.AID;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sound.midi.MidiDevice.Info;

import sun.java2d.Surface;

public class Person implements Serializable {

	private static final long serialVersionUID = 7909829490700726808L;

	static Random random = new Random(System.nanoTime());

	String id;

	String ideaId;

	Double ideaSurance;

	Integer latituide, logitiude;

	Map<String, FriendInfo> friends = new HashMap<String, FriendInfo>();

	AID parentAID;

	public String recalculateIdea() {
		return recalculateIdea(this.ideaId, 0.0);
	}

	public String updateFriend(String id, String ideaID, Integer suraa) {

		if (friends.containsKey(id)) {
			FriendInfo info = friends.get(id);
			info.setIdeaId(ideaID);
			info.setIdeaSurence(suraa);

			return recalculateIdea();

		}
		return null;

	}

	public String recalculateIdea(String ideaId, Double infuance) {

		System.out.println("Recalculating!");

		Map<String, Double> ideas = new HashMap<String, Double>();

		ideas.put(ideaId, infuance + 0.0);

		for (FriendInfo friendInfo : friends.values()) {
			if (friendInfo != null) {

				Double tmp = 0.0;

				System.out.println("mam:" + friendInfo.getId() + " "
						+ friendInfo.getIdeaSurence() + " "
						+ friendInfo.getInfuence() + " "
						+ friendInfo.getIdeaId());

				if (friendInfo.getIdeaId() != null
						& ideas.containsKey(friendInfo.getIdeaId())) {
					tmp = ideas.get(friendInfo.getIdeaId());
				}

				tmp += friendInfo.getIdeaSurence() * 1.0
						/ friendInfo.getInfuence();
				ideas.put(friendInfo.getIdeaId(), tmp);
			}
		}

		String newIdea = ideaId;
		Double newSurance = infuance;

		for (String idea : ideas.keySet()) {

			System.out.println("idea" + idea + " " + ideas.get(idea));

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
			info.setInfuence(random.nextInt(StaticValues.maxInfuence + 1));
			friends.put(info.getId(), info);
			return true;
		}
		return false;
	}

	public String statusChange(String friendID, String ideaID, int surance) {

		FriendInfo info = friends.get(friendID);
		if (info != null) {
			
			System.out.println("!QQQQQQQQQQQQQQQQQQ");

			info.setIdeaId(ideaID);
			info.setIdeaSurence(surance);
			info.setInfuence(random.nextInt(StaticValues.maxInfuence));

			recalculateIdea();
		}

		return null;
	}

	public void removeFried(String personID) {
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
		return ideaSurance.intValue();
	}

	public void setIdeaSurance(Integer ideaSurance) {
		this.ideaSurance = ideaSurance.doubleValue();
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
