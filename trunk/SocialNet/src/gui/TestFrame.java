package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class TestFrame {

	private static GUIFrame frame;

	public static void main(String[] args) throws InterruptedException {
		TestGUI agent = new TestGUI();
		agent.init();
		agent.addIdea("idea");
		agent.addIdea("lol");
		agent.addPeople("1", "idea", 30, 40);
		agent.addPeople("2","lol",20,20);
		agent.addPeople("3", "idea", 80, 40);
		agent.addPeople("4","lol",120,20);
		agent.addFriend("1", "2");
		agent.addFriend("1", "3");
		agent.addFriend("1", "4");
		agent.addFriend("3", "4");


		
		System.out.println("Ideas " + agent.getIdeas());
		System.out.println("People " + agent.getPeople());
		Thread.currentThread().sleep(3000);
		agent.removeFriend("1", "2");
		agent.addPeople("Jan", "iha", 100, 20);
		agent.addPeople("Pawel","los",70,150);
		agent.addPeople("Romek", "idea", 10, 80);
		agent.addPeople("Tomek","lol",120,20);
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Ideas " + agent.getIdeas());
		System.out.println("People " + agent.getPeople());
	}
}
