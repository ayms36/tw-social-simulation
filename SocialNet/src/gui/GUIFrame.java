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

import social.util.NameGenerator;

public class GUIFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String title = "Social Net";

	private List<PersonGUI> people = new ArrayList<PersonGUI>();
	private List<IdeaGUI> ideas = new ArrayList<IdeaGUI>();
	private JComboBox idee;


	public GUIFrame() {
		this.setTitle(title);
		this.setLayout(new BorderLayout());
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		this.setSize(toolkit.getScreenSize());
		GraphPanel gp = new GraphPanel();
		JTabbedPane tp = new JTabbedPane();
		JPanel addPerson = new AddPersonPanel();
		addPerson.setName("Add Person");
		JPanel addIdea = new AddIdeaPanel();
		addIdea.setName("Add Idea");
		JPanel addFriend = new JPanel();
		addFriend.setName("Add Friend");
		JPanel removePerson = new JPanel();
		removePerson.setName("Remove Person");
		JPanel changeIdea = new JPanel();
		changeIdea.setName("Change Idea");
		JPanel removeFriend = new JPanel();
		removeFriend.setName("Remove Friend");
		tp.add(addFriend);
		tp.add(addIdea);
		tp.add(addPerson);
		tp.add(removeFriend);
		tp.add(changeIdea);
		tp.add(removePerson);

		this.add(gp, BorderLayout.CENTER);
		this.add(tp, BorderLayout.LINE_END);
	}

	class GraphPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int[] data = { 21, 14, 18, 03, 86, 88, 74, 87, 54, 77, 61, 55, 48, 60,
				49, 36, 38, 27, 20, 18 };
		final int PAD = 20;

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			int w = getWidth();
			int h = getHeight();
			// Draw ordinate.
			// Draw abcissa.
			double xInc = (double) (w - 2 * PAD) / (data.length - 1);
			double scale = (double) (h - 2 * PAD) / getMax();
			// Mark data points.

			for (int i = 0; i < data.length; i++) {
				double x = PAD + i * xInc;
				double y = h - PAD - scale * data[i];
				g2.setPaint(Color.ORANGE);
				g2.fill(new Ellipse2D.Double(x - 16, y - 16, 32, 32));
				g2.setPaint(Color.BLACK);
				g2.drawString("1", (int) x - 2, (int) y + 3);
			}
		}

		private int getMax() {
			int max = -Integer.MAX_VALUE;
			for (int i = 0; i < data.length; i++) {
				if (data[i] > max)
					max = data[i];
			}
			return max;
		}
	}

	private class AddPersonPanel extends JPanel {

		private JTextField nameField;
		private JTextField latitudeField;
		private JTextField longitudeField;
		private JComboBox idee;
		private JLabel resultLabel;

		public AddPersonPanel() {
			GridLayout gl = new GridLayout();

			gl.setColumns(1);
			gl.setRows(40);
			this.setLayout(gl);
			this.add(new JLabel("Name:"));
			nameField = new JTextField("");
			this.add(nameField);
			JButton generateNamesButton = new JButton("GenerateName");
			generateNamesButton.addActionListener(new GenerateNamesListener());
			this.add(generateNamesButton);
			this.add(new JLabel("Idea:"));
			idee = new JComboBox();

			renewIdee();

			latitudeField = new JTextField();
			longitudeField = new JTextField();
			this.add(idee);
			this.add(new JLabel("Latitude:"));
			this.add(latitudeField);
			this.add(new JLabel("Longitude:"));
			this.add(longitudeField);
			JButton addPersonButton = new JButton("Add Person");
			addPersonButton.addActionListener(new AddPersonListener());
			this.add(addPersonButton);

			resultLabel = new JLabel("");
			this.add(resultLabel);
			this.add(new JLabel(""));

		}

		private class GenerateNamesListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					nameField.setText(NameGenerator.generateNewPersonName());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}

		private class AddPersonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (idee.getSelectedItem() != null && nameField.getText() != ""
						& latitudeField.getText() != ""
						& longitudeField.getText() != "") {
					PersonGUI p = new PersonGUI(nameField.getText(), idee
							.getSelectedItem().toString(),
							Integer.parseInt(latitudeField.getText()),
							Integer.parseInt(longitudeField.getText()));
					people.add(p);
					resultLabel.setForeground(Color.green);
					resultLabel.setText("Person " + p + " added");

				}
				else{
					resultLabel.setForeground(Color.RED);
					resultLabel.setText("Some fields are empty");
				}
			}

		}

	}
	
	private class AddIdeaPanel extends JPanel {

		private JTextField nameField;
		private JLabel resultLabel;

		public AddIdeaPanel() {
			GridLayout gl = new GridLayout();

			gl.setColumns(1);
			gl.setRows(40);
			this.setLayout(gl);
			this.add(new JLabel("Name:"));
			nameField = new JTextField("");
			this.add(nameField);
			JButton generateNamesButton = new JButton("GenerateName");
			generateNamesButton.addActionListener(new GenerateNamesListener());
			this.add(generateNamesButton);

			JButton addIdeaButton = new JButton("Add Idea");
			addIdeaButton.addActionListener(new AddIdeaListener());
			this.add(addIdeaButton);

			resultLabel = new JLabel("");
			this.add(resultLabel);
			this.add(new JLabel(""));

		}

		private class GenerateNamesListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setText(NameGenerator.generateNewIdeaName());
			}

		}

		private class AddIdeaListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText() != ""){
					IdeaGUI idea = new IdeaGUI(nameField.getText());
					ideas.add(idea);
					resultLabel.setForeground(Color.green);
					resultLabel.setText("Idea " + idea + " added");
					//renewIdee();
					System.out.println(ideas);
				}
				else{
					resultLabel.setForeground(Color.RED);
					resultLabel.setText("Some fields are empty");
				}
			}

		}

	}
	private void renewIdee(){
		if(idee!=null)
			idee.removeAll();
		for (IdeaGUI idea : ideas) {
			idee.addItem(idea);
		}
		//idee.repaint();

	}
	public void removeFriend(String toID, String whoID) {

		for(int i=0; i<people.size();i++){
			if (toID.equals(people.get(i).getName())){
				people.get(i).removeFriend(whoID);
			}

		}
			
		
	}
	public void addFriend(String toID, String whoID) {
		int index=(Integer) null;
		int friendIndex = (Integer) null;
		for(int i=0; i<people.size();i++){
			if (toID.equals(people.get(i).getName())){
				index = i;
			}
			if (whoID.equals(people.get(i).getName()))
				friendIndex=i;
		}

		people.get(index).addFriend(people.get(friendIndex));
		
		
	}
	public void changePeopleIdea(String peopleId, String ideaId) {
		for( PersonGUI p :people ){
			if(p.getName().equals(peopleId)){
				p.setIdea(ideaId);			}
		}
	}
	public void addPeople(String id, String ideaID, int latitude, int longitude) {
		people.add(new PersonGUI(id,ideaID,latitude,longitude));
	}
	public void addIdea(String ideaID) {
		ideas.add(new IdeaGUI(ideaID));
	}

}
