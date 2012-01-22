package gui;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String name;
	private String idea;
	private int latitude;
	private int longitude;
	private List<Person> friends;
	
	public Person(String name, String idea, int latitude, int longitude){
		this.name = name;
		this.idea=idea;
		this.latitude=latitude;
		this.longitude=longitude;
		friends = new ArrayList<Person>();
	}
	
	public String toString(){
		String begin = "Name: "+name+ "\nIdea: "+ idea + "\nLatitude: "+ latitude +"\nLongitude: "+ longitude;
		String friendsString="";
		for (Person p :friends){
			friendsString=friendsString + p.name+" ";
		}
		return begin + friendsString;
	}
	
	
	
}
