package gui;

import java.util.ArrayList;
import java.util.List;

import social.model.StaticValues;

public class PersonGUI {
	private String name;
	private String idea;
	private int latitude;
	private int longitude;
	private List<PersonGUI> friends;

	public PersonGUI(String name, String idea, int latitude, int longitude) {
		this.name = name;
		this.idea = idea;
		this.latitude = latitude;
		this.longitude = longitude;
		friends = new ArrayList<PersonGUI>();
	}

	public String toString() {
		String begin = "Name: " + name + "\nIdea: " + idea + "\nLatitude: "
				+ latitude + "\nLongitude: " + longitude;
		String friendsString = "";
		for (PersonGUI p : friends) {
			friendsString = friendsString + p.name + " ";
		}
		return begin + friendsString;
	}

	public void removeFriend(String personID) {
		friends.remove(personID);
	}

	public void addFriend(PersonGUI p) {
		if (!friends.contains(p)) {
			friends.add(p);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public List<PersonGUI> getFriends() {
		return friends;
	}

	public void setFriends(List<PersonGUI> friends) {
		this.friends = friends;
	}


}
