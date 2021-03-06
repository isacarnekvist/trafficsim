import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;

public class UI {
	
	private JFrame frame;
	private double meterswide;
	private ArrayList<Integer> positions;
	private Graphics g;
	
	public UI(double meterswide) {
		this.meterswide = meterswide;
		frame = new JFrame();
		frame.setBounds(200, 100, 800, 600);
		frame.setVisible(true);
        frame.add(new JPanel());
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setDoubleBuffered(true);
		this.g = panel.getGraphics();
		positions = new ArrayList<Integer>();
	}
	
	/**
	 * Adds car at position xPos
	 * @param xPos
	 * @return false if outside of window
	 */
	public boolean addCarAt(int xPos) {
		positions.add(xPos);
		
		if (xPos > meterswide) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Clears screen, draws all cars. Removes all positions of cars.
	 */
	public void refresh() {
		g.clearRect(0, 0, 800, 600);
		for(int i : positions) {
			g.drawRect(i, 300, 3, 3);
		}
		
		positions.clear();
	}

}
