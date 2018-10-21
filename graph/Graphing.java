package graph;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Graphing extends JPanel {
	private ArrayList<ArrayList<Double>> times;
	private final int GRAPH_WIDTH = 850;
	private final int GRAPH_HEIGHT = 600;
	private final int MAX_LINES = 9;
	private double min;
	private double max;
	private Color axisColor;
	private Color gridLineColor;
	private Color stringColor;
	private Color[] lineColors;
	private ArrayList<JLabel> colorLabels;
	private static final Stroke GRAPH_STROKE = new BasicStroke(1f);
	private final int POINT_SIZE = 4;
	private int size = 0;
	

	public Graphing(ArrayList<String> args) {
		/** Args is array of times, so -1 is impossible. */
		max = -1;
		min = Integer.MAX_VALUE;
		times = new ArrayList<ArrayList<Double>>(args.size());
		
		axisColor = new Color(0, 0, 0, 255);
		gridLineColor = new Color(0, 0, 0, 50);
		stringColor = new Color(0, 0, 0, 255);
		
		lineColors = new Color[5];
		lineColors[0] = new Color(178,34,34, 255); //red
		lineColors[1] = new Color(50,205,50, 255); //green
		lineColors[2] = new Color(30,144,255, 255); //blue
		lineColors[3] = new Color(75,0,130, 255); //indigo
		lineColors[4] = new Color(255,223,0, 255); //gold
		
		colorLabels = new ArrayList<JLabel>();

		try {
			if (args.get(0).equals("python") || args.get(0).equals("c") || args.get(0).equals("java")) {
				times.add(new ArrayList<Double>());
				colorLabels.add(new JLabel(args.get(0)));
				colorLabels.add(new JLabel());
				colorLabels.get(1).setOpaque(true);
				colorLabels.get(1).setBackground(lineColors[0]);
				colorLabels.get(1).setPreferredSize(new Dimension(10, 10));
				
				for (int i = 2; i < args.size(); i++) {
					times.get(0).add(Double.parseDouble(args.get(i)));
					if (times.get(0).get(i - 2) > max)
						max = times.get(0).get(i - 2);
					if (times.get(0).get(i - 2) < min)
						min = times.get(0).get(i - 2);
				}
				size = times.get(0).size();
				
			} else if (args.get(0).equals("all")) {
				if (args.get(1).equals("merge") ||
						args.get(1).equals("quick") || 
						args.get(1).equals("bubble") ||
						args.get(1).equals("insertion") ||
						args.get(1).equals("heap")) {
					int count = -1;
					for (int i = 2; i < args.size(); i++) {
						if (args.get(i).equals("python") || args.get(i).equals("c") || args.get(i).equals("java")) {
							times.add(new ArrayList<Double>());
							count++;
							colorLabels.add(new JLabel(args.get(i)));
							colorLabels.add(new JLabel());
							colorLabels.get((2 * count) + 1).setOpaque(true);
							colorLabels.get((2 * count) + 1).setBackground(lineColors[count]);
							colorLabels.get((2 * count) + 1).setPreferredSize(new Dimension(10, 10));
							continue;
						}
						if (args.get(i).equals("end"))
							continue;
						double temp = Double.parseDouble(args.get(i));
						times.get(count).add(temp);
						if (temp > max)
							max = temp;
						if (temp < min)
							min = temp;
					}
					
				} else if (args.get(1).equals("java") ||
						args.get(1).equals("c") || 
						args.get(1).equals("python")) {
					int count = -1;
					for (int i = 2; i < args.size(); i++) {
						if (args.get(i).equals("merge") ||
								args.get(i).equals("quick") || 
								args.get(i).equals("bubble") ||
								args.get(i).equals("insertion") ||
								args.get(i).equals("heap")) {
							times.add(new ArrayList<Double>());
							count++;
							colorLabels.add(new JLabel(args.get(i)));
							colorLabels.add(new JLabel());
							colorLabels.get((2 * count) + 1).setOpaque(true);
							colorLabels.get((2 * count) + 1).setBackground(lineColors[count]);
							colorLabels.get((2 * count) + 1).setPreferredSize(new Dimension(10, 10));
							continue;
						}
						if (args.get(i).equals("end"))
							continue;
						double temp = Double.parseDouble(args.get(i));
						times.get(count).add(temp);
						if (temp > max)
							max = temp;
						if (temp < min)
							min = temp;
					}
				}
				size = times.get(0).size();
			} 
				

		} catch (NumberFormatException e) {
			System.out.println("Arguments not numbers.");
			return;
		}

		for (JLabel label : colorLabels)
			add(label);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;
	        
		// the y value for each line.
		double y = GRAPH_HEIGHT / (MAX_LINES + 1);
		// space between 0 (top) and the top line
		int verticalOffset = 30;
		// space between 0 (top) and the bottom line
		int workableYarea = GRAPH_HEIGHT - (verticalOffset * 2);
		// space between 0 (left) and the first line
		int horizontalOffset = 50;
		// space between 0 (left) and the last line
		int workableXarea = (int) (((GRAPH_WIDTH - (horizontalOffset * 2)) / size) * (size));
		
		//Set graph to white background
		g2.setColor(Color.WHITE);
        g2.fillRect(horizontalOffset, verticalOffset, workableXarea, workableYarea);
		g2.setStroke(GRAPH_STROKE);
		
		/** draw the y axis lines */
		for (int i = 0; i < MAX_LINES + 1; i++) {
			g2.setColor(stringColor);
			g2.drawLine(horizontalOffset, 
					(int)(y * i) - verticalOffset, 
					horizontalOffset + 7, 
					(int) (y * i) - verticalOffset);
			g2.drawString(formatVal(((max / MAX_LINES) * (MAX_LINES + 1 - i))), 
					2, 
					(int)(y * i) - 25);
			
			g2.setColor(gridLineColor);
			g2.drawLine(horizontalOffset, 
					(int)(y * i) - verticalOffset, 
					workableXarea + horizontalOffset, 
					(int)(y * i) - verticalOffset);
		}
		
		/** draw the x axis lines */
		for (int i = 0; i <= size; i++) {
			g2.setColor(stringColor);
			g2.drawLine((int) ((workableXarea / size) * (i)) + horizontalOffset, 
					workableYarea - 7 + verticalOffset, 
					(int) ((workableXarea / size) * (i)) + horizontalOffset, 
					workableYarea + verticalOffset);
			
			//TODO: get info for a second set of data indicating the num of elements.formatter.format(i + 1))
			g2.drawString(formatVal(120912), 
					(int) ((workableXarea / size) * (i)) + horizontalOffset - (horizontalOffset / 2), 
					workableYarea + 15 + verticalOffset);
			
			g2.setColor(gridLineColor);
			g2.drawLine((int) ((workableXarea / size) * (i)) + horizontalOffset, 
					verticalOffset, 
					(int) ((workableXarea / size) * (i)) + horizontalOffset, 
					workableYarea + verticalOffset);
			
		}

		// X and Y axis
		g2.setColor(axisColor);
		g2.drawLine(horizontalOffset, workableYarea + verticalOffset, workableXarea + horizontalOffset, workableYarea + verticalOffset); //x
		g2.drawLine(horizontalOffset, verticalOffset, horizontalOffset, workableYarea + verticalOffset); //y
		
		
		/* Draw the coordinates.
		 * Math: x is workable graph (WIDTH - horizontalOffset) / number of elements * i, then add the horizontalOffset offset back in.
		 * y is workable graph (verticalOffset) - the ratio (value / maxValue) * (verticalOffset - verticalOffset)
		 * last part is (verticalOffset - verticalOffset) because the top line is the max value, and is at a y of verticalOffset.
		 */
		for (int list = 0; list < times.size(); list++) {
			g2.setColor(lineColors[list]);
			for (int i = 1; i < size; i++) {
				g2.drawLine((int) (((workableXarea) / size) * (i - 1)) + horizontalOffset,
						(int) ((workableYarea + verticalOffset) - (times.get(list).get(i-1) / max) * (workableYarea)),
						(int) (((workableXarea) / size) * (i)) + horizontalOffset,
						(int) ((workableYarea + verticalOffset) - (times.get(list).get(i) / max) * (workableYarea)));
				
				g2.fillOval((int) (((workableXarea) / size) * (i - 1)) + horizontalOffset - (POINT_SIZE / 2),
						(int) ((workableYarea + verticalOffset) - (times.get(list).get(i-1) / max) * (workableYarea)) - (POINT_SIZE / 2),
						POINT_SIZE, 
						POINT_SIZE);
			}
			
			g2.fillOval((int) (((workableXarea) / size) * (size - 1)) + horizontalOffset - (POINT_SIZE / 2),
					(int) ((workableYarea + verticalOffset) - (times.get(list).get(times.get(list).size() - 1) / max) * (workableYarea)) - (POINT_SIZE / 2),
					POINT_SIZE, 
					POINT_SIZE);
		}
		
	}
	
	private static String formatVal(double value) {
		String temp = Double.toString(value);
		int integerPlaces = temp.indexOf('.');
		if (integerPlaces < 5)
			return new DecimalFormat("#,###.00").format(value);
		return new DecimalFormat("0.00E0").format(value);
	}

//	public static void main(String[] args) {
//		new Graphing(args);
//
//	}
}
