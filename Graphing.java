import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graphing extends JPanel {
	private double[] times;
	private JFrame frame;
	private final int WIDTH = 900;
	private final int HEIGHT = 600;
	private final int MAX_LINES = 8;
	private double min;
	private double max;
	private NumberFormat formatter;

	public Graphing(String[] args) {
		/** Args is array of times, so -1 is impossible. */
		max = -1;
		min = Integer.MAX_VALUE;
		times = new double[args.length];
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
		// frame.setBackground(Color.decode("0x432E94"));
		frame.setSize(new Dimension(WIDTH, 625));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.decode("0x005C69"));
	}

	@Override
	public void paint(Graphics g) {
		// the y value for each line.
		int y = HEIGHT / (MAX_LINES + 2);

		// space between 0 (top) and the bottom line
		int verticalOffset = ((HEIGHT / (MAX_LINES + 2)) * (MAX_LINES + 1)) - 30;
		/** draw the y axis lines */
		g.setColor(Color.decode("0xEACD90"));
		for (int i = 1; i < MAX_LINES + 1; i++) {
			g.drawLine(0, (y * i) - 30, 100, (y * i) - 30);
			g.drawString(formatter.format((max / MAX_LINES) * (MAX_LINES + 1 - i)), 5, (y * i) - 32);
		}
		
		/** draw the x axis lines */
		for (int i = 0; i < times.length; i++) {
			g.drawLine((int) (((WIDTH - 100) / times.length) * (i)) + 100, 
					verticalOffset, 
					(int) (((WIDTH - 100) / times.length) * (i)) + 100, 
					HEIGHT);
			
			//TODO: get info for a second set of data indicating the num of elements.formatter.format(i + 1))
			g.drawString(String.format("%6.3e", Integer.toString(i + 1)), (int) (((WIDTH - 100) / times.length) * (i)) + 75, verticalOffset + 15);
		}

		// X and Y axis
		g.setColor(Color.decode("0xd90e5c"));
		g.drawLine(0, verticalOffset, WIDTH, verticalOffset);
		g.drawLine(100, 0, 100, HEIGHT);
		
		/* Draw the coordinates.
		 * Math: x is workable graph (WIDTH - 100) / number of elements * i, then add the 100 offset back in.
		 * y is workable graph (verticalOffset) - the ratio (value / maxValue) * (verticalOffset - 30)
		 * last part is (verticalOffset - 30) because the top line is the max value, and is at a y of 30.
		 */
		for (int i = 1; i < times.length; i++) {
			System.out.println(100 + (WIDTH / (times.length - i)) + ", " + 
					(int) ((times[i - 1] / max) * (HEIGHT - verticalOffset)) + ", " + 
					(100 + (WIDTH / (times.length - i))) + ", " + 
					((int) (times[i] / max) * (HEIGHT - verticalOffset)));

			g.drawLine((int) (((WIDTH - 100) / times.length) * (i - 1)) + 100,
					(int) (verticalOffset - ((times[i-1] / max) * (verticalOffset - 30))), 
					(int) (((WIDTH - 100) / times.length) * (i)) + 100,
					(int) (verticalOffset - ((times[i] / max) * (verticalOffset - 30))));
		}
		
	}

	public static void main(String[] args) {
		new Graphing(args);

	}
}
