// Simulation!

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Simulation {
    // Width of simulated world in meters
	double width;
	JFrame frame;
    JPanel panel;
	Graphics g;
	ArrayList<Vehicle> vehicles;
	int foo;

    public static void main(String[] args) {
        Simulation s = new Simulation(100, 500);
        s.run();
    }

	public Simulation(double width, int screenWidth) {
		this.width = width;
		frame = new JFrame();
        panel = new JPanel();
		frame.setSize(new Dimension(screenWidth, 9*screenWidth/16));
        panel.setSize(frame.getSize());
        frame.add(panel);
        panel.setDoubleBuffered(true);
		frame.setVisible(true);
		vehicles = new ArrayList<Vehicle>();
	}
	
    public void run() {
        Dimension size = panel.getSize();
        Rectangle2D bounds = new Rectangle2D.Double(0, 0, size.getWidth(), size.getHeight());

        // create and show my balls
        for (int i = 0; i < 10; i++) {
            double m = (1.0 + Math.random()) * 1e3;
            Vehicle v = new Vehicle(m, 0, new Personality(1, 2, 3));
            v.vel = 1;
            v.pos = 5*i;
            vehicles.add(v);
            System.out.println(v);
        }
        
        frame.createBufferStrategy(2);
        BufferStrategy buffStrat = frame.getBufferStrategy();
        
        // make them bounce
        double ySpeeds = 1.0;
        long timePrev = System.nanoTime();
        while (ySpeeds > 0) {
        	g = buffStrat.getDrawGraphics();
        	g.clearRect(0, 0, panel.getWidth(), panel.getHeight());
            long timeStart = System.nanoTime();
            double t = (timeStart - timePrev)/1e9;
            Vehicle p = null;
            for (Vehicle v : vehicles) {
                // TODO Strategy, acceleration etc
                v.pos += t*v.vel + v.accel*t*t/2.0;
                v.vel += t*v.accel;

                drawVehicle(v);

                if (p != null) {
                    p.accel = v.personality.getAcceleration(p, v);
                }
                p = v;
            }
            g.dispose();
            buffStrat.show();
            long deltaMs = (System.nanoTime() - timeStart)/(long)1e6;
            long wait = 1000/60 - deltaMs;
            if(wait < 0) {
            	wait = 0;
            }
            try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				// TODO Screen sync + fix slow mother f**king car
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
