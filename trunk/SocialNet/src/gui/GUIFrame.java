package gui;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

	public Map<String,IdeaGUI> getIdeas() {
		return ideas;
	}

	public void setIdeas(Map<String,IdeaGUI> ideas) {
		this.ideas = ideas;
	}

	private Map<String,IdeaGUI> ideas = new HashMap<String, IdeaGUI>();

	public GUIFrame() {
		this.setTitle(title);
		this.setLayout(new BorderLayout());
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.setSize(toolkit.getScreenSize().width,
				toolkit.getScreenSize().height - 50);
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
			g2.clearRect(0, 0, w, h);
			double xInc = (double) (w - 2 * PAD) /(getLatitudeMax() -getLatitudeMin());
			double scale = (double) (h - 2 * PAD) / (getLongitudeMax() - getLongitudeMin());

			// Drawing people
			for (int i = 0; i < people.size(); i++) {
				double x = transformX(xInc, people.get(i).getLatitude()-getLatitudeMin());
				double y = transformY(scale, people.get(i).getLongitude()-getLongitudeMin());
				g2.setPaint(ideas.get(people.get(i).getIdea()).getC());

				for (PersonGUI p : people.get(i).getFriends()) {
					Line2D line = new Line2D.Double(x, y, transformX(xInc,
							p.getLatitude()-getLatitudeMin()), transformY(scale,
							p.getLongitude()-getLongitudeMin()));
					g2.setStroke(new BasicStroke(4));
					g2.draw(line);
					g2.fill(line);

					// }
				}

			}
			for (int i = 0; i < people.size(); i++) {
				double x = transformX(xInc, people.get(i).getLatitude()-getLatitudeMin());
				double y = transformY(scale, people.get(i).getLongitude()-getLongitudeMin());
				g2.setPaint(ideas.get(people.get(i).getIdea()).getC());
				g2.fill(new Ellipse2D.Double(x - 40, y - 16, 80, 32));
				g2.setPaint(Color.BLACK);
				g2.drawString(people.get(i).getName(), (int) x - 20, (int) y + 3);
			}

		}



		private double transformX(double xInc, double x) {
			return PAD + xInc * x;

		}

		private double transformY(double scale, double y) {
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
		
		private int getLatitudeMin() {
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < people.size(); i++) {
				if (people.get(i).getLatitude() < min)
					min = people.get(i).getLatitude();
			}
			return min;
		}
		private int getLongitudeMin() {
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < people.size(); i++) {
				if (people.get(i).getLongitude() < min)
					min = people.get(i).getLongitude();
			}

			return min;
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
		repaint();

	}

	public void changePeopleIdea(String peopleId, String ideaId) {
		for (PersonGUI p : people) {
			if (p.getName().equals(peopleId)) {
				p.setIdea(ideaId);
				addIfNotContains(ideaId);
			}
		}
		repaint();
	}

	public void addPeople(String id, String ideaID, int latitude, int longitude) {
		people.add(new PersonGUI(id, ideaID, latitude, longitude));
		addIfNotContains(ideaID);
		repaint();
	}

	public void addIdea(String ideaID) {
		addIfNotContains(ideaID);
		repaint();
	}
	
	private void addIfNotContains(String ideaId){
		if( !ideas.containsKey(ideaId)){
			ideas.put(ideaId, new IdeaGUI());
		}
	}

}
