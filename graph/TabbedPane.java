package graph;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class TabbedPane extends JTabbedPane {

	public TabbedPane(ArrayList<ArrayList<String>> args) 
	{
		int size = args.size();
		for (int i = 0; i < size; i++)
			add(args.get(i).get(0) + " " + args.get(i).get(1), new Graphing(args.get(i)));
	}
	
	public static void main (String[] args) {
		ArrayList<ArrayList<String>> inputVals = new ArrayList<ArrayList<String>>();
		int currList = -1;
		for (int i = 0; i < args.length; i++)
			System.out.println(args[i]);
		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("all")) {
				inputVals.add(new ArrayList<String>());
				currList++;
				if (args[i+1].equals("merge") ||
						args[i+1].equals("quick") || 
						args[i+1].equals("bubble") ||
						args[i+1].equals("insertion") ||
						args[i+1].equals("heap")) {
					while (!args[i].equals("end")) {
						inputVals.get(currList).add(args[i]);
						i++;
					}
				} else if (args[i+1].equals("java") ||
						args[i+1].equals("c") || 
						args[i+1].equals("python"))
					while (!args[i].equals("end")) {
						inputVals.get(currList).add(args[i]);
						i++;
					}
			} else if (args[i].equals("python") || args[i].equals("c") || args[i].equals("java")) {
				inputVals.add(new ArrayList<String>());
				currList++;
			}
			inputVals.get(currList).add(args[i]);
		}
		
		TabbedPane pane = new TabbedPane(inputVals);
		
		JFrame frame = new JFrame();
		frame = new JFrame();
		frame.getContentPane().add(pane);
		frame.setSize(new Dimension(850, 650));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
