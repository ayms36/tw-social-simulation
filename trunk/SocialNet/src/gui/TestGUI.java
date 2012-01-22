package gui;

import java.util.List;

import javax.swing.JFrame;

public class TestGUI {
	
	private GUIFrame frame;

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
	public List<IdeaGUI> getIdeas(){
		return frame.getIdeas();
	}
	public List<PersonGUI> getPeople(){
		return frame.getPeople();
	}

}
