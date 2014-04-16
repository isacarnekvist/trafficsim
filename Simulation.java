// Simulation!

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Simulation {
    // Width of simulated world in meters
	double width;
	JFrame frame;
    JPanel panel;
	Graphics g;
	ArrayList<Vehicle> vehicles;

    public static void main(String[] args) {
        Simulation s = new Simulation(100, 200);
        s.run();
    }

	public Simulation(double width, int screenWidth) {
		this.width = width;
		frame = new JFrame();
        panel = new JPanel();
		frame.setBounds(0, 0, screenWidth, screenWidth/4);
        panel.setBounds(0, 0, screenWidth, screenWidth/4);
        frame.add(panel);
        panel.setDoubleBuffered(true);
		frame.setVisible(true);
		this.g = panel.getGraphics();
		vehicles = new ArrayList<Vehicle>();
	}
	
    public void run() {
        Dimension size = panel.getSize();
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, size.getWidth(), size.getHeight());

        // create and show my balls
        for (int i = 0; i < 10; i++) {
            double m = (1.0 + Math.random()) * 24;
            Vehicle v = new Vehicle(m, 0, new Personality(1, 2, 3));
            vehicles.add(v);
            System.out.println(v);
        }

        // make them bounce
        double ySpeeds = 1.0;
        long timePrev = System.nanoTime();
        while (ySpeeds > 0) {
            long timeStart = System.nanoTime();
            double t = (timeStart - timePrev)/1e9;
            for (Vehicle v : vehicles) {
                //v.simulate(t);
                drawVehicle(v);
                v.pos += 4*t;
            }
            timePrev = timeStart;
        }
    }

    void drawVehicle(Vehicle v) {
        Dimension size = panel.getSize();
        double scale = size.getWidth()/this.width;
        int width = (int)(v.mass/1e3);
        int height = (int)(v.mass/4e3);
        int x = (int)(v.pos*scale);
        int y = (int)(size.getHeight() - height)/2;
		g.drawRect(x, y, width, height);
    }

}
