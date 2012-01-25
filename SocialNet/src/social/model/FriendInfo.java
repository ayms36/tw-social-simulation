package social.model;

import java.io.Serializable;

import jade.core.AID;

public class FriendInfo implements Serializable{

	private static final long serialVersionUID = -6399463456418609220L;
	
	
	String ideaId;
	String id;
	Integer ideaSurence = 1;
	AID parentAID;
	Integer infuence = 1;
	String toId;
	
	private FriendInfo(){
		
	}
	
	public FriendInfo(String to) {
		toId = to;
	}
	
	public String getToId() {
		return toId;
	};
	
	public FriendInfo(Person person) {

		ideaId = person.getIdeaId();
		id = person.getId();
		ideaSurence = person.getIdeaSurance();
		parentAID = person.getParentAID();
	
	}	
	public String getIdeaId() {
		return ideaId;
	}
	public void setIdeaId(String ideaId) {
		this.ideaId = ideaId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getIdeaSurence() {
		return ideaSurence;
	}
	public void setIdeaSurence(Integer ideaSurence) {
		this.ideaSurence = ideaSurence;
	}
	public AID getParentAID() {
		return parentAID;
	}
	public void setParentAID(AID parentAID) {
		this.parentAID = parentAID;
	}
	public Integer getInfuence() {
		return infuence;
	}
	public void setInfuence(Integer infuence) {
		this.infuence = infuence;
	}
}
