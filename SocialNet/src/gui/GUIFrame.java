package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.sun.corba.se.impl.orbutil.RepIdDelegator;

import social.util.NameGenerator;

public class GUIFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String title = "Social Net";

	private List<PersonGUI> people = new ArrayList<PersonGUI>();

	public List<PersonGUI> getPeople() {
		return people;
	}

	public void setPeople(List<PersonGUI> people) {
		this.people = people;
	}

	public List<IdeaGUI> getIdeas() {
		return ideas;
	}

	public void setIdeas(List<IdeaGUI> ideas) {
		this.ideas = ideas;
	}

	private List<IdeaGUI> ideas = new ArrayList<IdeaGUI>();

	public GUIFrame() {
		this.setTitle(title);
		this.setLayout(new BorderLayout());
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.setSize(toolkit.getScreenSize().width,toolkit.getScreenSize().height-50);
		GraphPanel gp = new GraphPanel();

		this.add(gp, BorderLayout.CENTER);

	}

	class GraphPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		final int PAD = 40;

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			int w = getWidth();
			int h = getHeight();
			g2.clearRect(0, 0, w,h);
			double xInc = (double) (w - 2 * PAD) / getLatitudeMax();
			double scale = (double) (h - 2 * PAD) / getLongitudeMax();

			// Drawing people
			for (int i = 0; i < people.size(); i++) {
				double x = transformX(xInc,people.get(i).getLatitude());
				double y = transformY(scale,people.get(i).getLongitude());
				g2.setPaint(people.get(i).getIdea().getC());
				g2.fill(new Ellipse2D.Double(x - 50, y - 16, 100, 32));
				for (PersonGUI p : people.get(i).getFriends()) {
					if(indexInPeople(p.getName())>indexInPeople(people.get(i).getName())){
					Line2D line = new Line2D.Double(x,y,transformX(xInc,p.getLatitude()),transformY(scale,p.getLongitude()));
					g2.draw(line);
				}}
				g2.setPaint(Color.BLACK);
				g2.drawString(people.get(i).getName(), (int) x - 2, (int) y + 3);
			}

		}
		
		private int indexInPeople(String personGUI) {
				for(int i =0; i<people.size();i++)
					if(personGUI.equals(people.get(i).getName()))
						return i;
			return -1;
		}

		private double transformX(double xInc, double x){
			return PAD + xInc * x;

		}
		
		private double transformY(double scale, double y){
			return PAD + scale * y;

		}

		private int getLatitudeMax() {
			int max = -Integer.MAX_VALUE;
			for (int i = 0; i < people.size(); i++) {
				if (people.get(i).getLatitude() > max)
					max = people.get(i).getLatitude();
			}
			return max;
		}

		private int getLongitudeMax() {
			int max = -Integer.MAX_VALUE;
			for (int i = 0; i < people.size(); i++) {
				if (people.get(i).getLongitude() > max)
					max = people.get(i).getLongitude();
			}

			return max;
		}
	}

	public void removeFriend(String toID, String whoID) {

		for (int i = 0; i < people.size(); i++) {
			if (toID.equals(people.get(i).getName())) {
				people.get(i).removeFriend(whoID);
			}
			if (whoID.equals(people.get(i).getName())) {
				people.get(i).removeFriend(toID);
			}
		}
		repaint();

	}

	public void addFriend(String toID, String whoID) {
		int index = -1;
		int friendIndex = -1;
		for (int i = 0; i < people.size(); i++) {
			if (toID.equals(people.get(i).getName())) {
				index = i;
			}
			if (whoID.equals(people.get(i).getName()))
				friendIndex = i;
		}

		people.get(index).addFriend(people.get(friendIndex));
		people.get(friendIndex).addFriend(people.get(index));

	}

	public void changePeopleIdea(String peopleId, String ideaId) {
		for (PersonGUI p : people) {
			if (p.getName().equals(peopleId)) {
				p.setIdea(new IdeaGUI(ideaId));
			}
		}
	}

	public void addPeople(String id, String ideaID, int latitude, int longitude) {
		people.add(new PersonGUI(id, ideaID, latitude, longitude));
	}

	public void addIdea(String ideaID) {
		ideas.add(new IdeaGUI(ideaID));
	}

}
