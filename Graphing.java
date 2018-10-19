package TestingPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphTest extends JPanel {
	private double[] times;
	private JFrame frame;
	private final int WIDTH = 900;
	private final int HEIGHT = 600;
	private final int MAX_LINES = 8;
	private double min;
	private double max;
	private NumberFormat formatter;

	public GraphTest(String[] args) {
	  /** Args is array of times, so -1 is impossible. */
	  max = -1;
	  min = Integer.MAX_VALUE;
	  times = new double [args.length];
	  formatter = new DecimalFormat("#,###.00");
	  double temp = Double.parseDouble(args[0]);
	  
	  try {
	   for (int i = 0; i < args.length; i++) {
	    times[i] = Double.parseDouble(args[i]);
	    if (times[i] > max)
	     max = times[i];
	    if (times[i] > min)
	     min = times[i];
	   }
	  } catch (NumberFormatException e) {
	   System.out.println("Arguments not numbers.");
	   return;
	  }
	  
	  frame = new JFrame();
	  frame.getContentPane().add(this);
	//  frame.setBackground(Color.decode("0x432E94"));
	  frame.setSize(new Dimension (WIDTH, 600));
	  frame.setLocationRelativeTo(null);
	  frame.setVisible(true);
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setBackground(Color.decode("0x005C69"));
	  repaint();
	 }

	@Override
	public void paint(Graphics g) {
		int y = HEIGHT / 10;
		int verticalOffset = ((HEIGHT / (MAX_LINES + 2)) * (MAX_LINES + 1)) - 30;
		/** draw the lines */
		g.setColor(Color.decode("0xEACD90"));
		for (int i = 1; i < MAX_LINES + 1; i++) {
			g.drawLine(0, 
					(y * i) - 30, 
					100, 
					(y * i) - 30);
			g.drawString(formatter.format((max / MAX_LINES) * (MAX_LINES + 1 - i)), 10, (y * i) - 32);
		}
		
		g.setColor(Color.decode("0xd90e5c"));
		g.drawLine(0, 
				verticalOffset, 
				WIDTH, 
				verticalOffset);
		g.drawLine(100, 0, 100, HEIGHT);
		
		for (int i = 1; i < times.length; i++) {
			g.drawLine(100 + (WIDTH/(times.length - i)), 
					(int) ((times[i-1] / HEIGHT) - verticalOffset),
					100 + (WIDTH/(times.length - i)),
					(int) ((times[i] / HEIGHT) - verticalOffset));
		}
	}

	public static void main(String[] args) {
		new GraphTest(args);

	}
}
