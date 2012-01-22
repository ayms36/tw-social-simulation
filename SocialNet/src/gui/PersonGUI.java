package gui;

import java.util.ArrayList;
import java.util.List;

public class PersonGUI {
	private String name;
	private IdeaGUI idea;
	private int latitude;
	private int longitude;
	private List<PersonGUI> friends;

	public PersonGUI(String name, String idea, int latitude, int longitude) {
		this.name = name;
		this.idea = new IdeaGUI(idea);
		this.latitude = latitude;
		this.longitude = longitude;
		friends = new ArrayList<PersonGUI>();
	}

	public String toString() {
		String begin = "Name: " + name + "\nIdea: " + idea + "\nLatitude: "
				+ latitude + "\nLongitude: " + longitude;
		String friendsString = "\nFriends: ";
		for (PersonGUI p : friends) {
			friendsString = friendsString + p.name + " ";
		}
		return begin + friendsString;
	}

	public void removeFriend(String personID) {
		int index = -1;
		for (int i = 0; i < friends.size(); i++) {
			if (personID.equals(friends.get(i).getName())) {
				index = i;
			}
		}
		friends.remove(index);
		

	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
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

	public IdeaGUI getIdea() {
		return idea;
	}

	public void setIdea(IdeaGUI idea) {
		this.idea = idea;
	}

	public List<PersonGUI> getFriends() {
		return friends;
	}

	public void setFriends(List<PersonGUI> friends) {
		this.friends = friends;
	}

}
