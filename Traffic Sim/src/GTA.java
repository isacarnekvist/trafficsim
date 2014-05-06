import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.Font;
import java.util.Random;
import java.util.LinkedList;
import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class GTA {
	
	private final int fps = 60;		// Frames per second
	private final int ppm = 10;		// Pixels per meter, also set in Vehicle.java, pro-programmer style ;)
	private final int width = 1080;
	private final int height = 600;
	private final int NUM_CAR_MODELS = 7;
	private LinkedList<Vehicle> vehicles;

	public static void main(String[] args){
		new GTA();
	}

	private GTA(){
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Simulator");
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Display error");
			System.exit(0);
		}
		
		// Info text setup
		TrueTypeFont infoText = new TrueTypeFont(new Font("Verdana", Font.BOLD, 16), true);
		
		// OpenGL setup
		glClearColor(0f, 0f, 0f, 1);
		glEnable(GL_TEXTURE_2D);
		glOrtho(0, width, height, 0, 1, -1);
		glEnable(GL_BLEND); 
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glShadeModel(GL_SMOOTH);       
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);
		
		Sprite road = new Sprite("res/road.png", 128, 128);
		Random rand = new Random();

        vehicles = new LinkedList<Vehicle>();

        RoadBlock roadBlock = new RoadBlock(3*width/3/ppm);

		for(int i = 0; i < 7; i++) {
            Vehicle v = new Vehicle(rand.nextInt(7), -i * 100, null);
            v.personality = Personality.standardDriver(v);
			vehicles.addFirst(v);
		}

		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);

            int nVehicles = vehicles.size();
			
			// ROAD!
			for (int i = 0; i < width; i += 64) {
				road.draw(i, 236);
			}
			
            Vehicle n = roadBlock;
			for (Iterator<Vehicle> it = vehicles.iterator(); it.hasNext();) {
                Vehicle v = it.next();
                // Delete vehicles that stand still next to road block
				if ((Math.abs(v.pos - roadBlock.pos + v.getLength()) < 25.0/ppm) && (Math.abs(v.accel) < 0.1) && (Math.abs(v.vel) < 0.1)) {
                    System.out.println(String.format("Remove vehicle %s", v));
					it.remove();
				} else {
                    v.simulate(1.0/fps, n);
                }
                n = v;
			}

			// Create new cars
			while (nVehicles > vehicles.size()) {
				Vehicle v = new Vehicle(rand.nextInt(NUM_CAR_MODELS), 0, null);
                v.pos = -v.getLength();
                v.personality = Personality.standardDriver(v);
                vehicles.addLast(v);
			}

            // Calculate and draw info, cars
            double currentWattage = 0;
            for (Vehicle v : vehicles) {
                currentWattage += v.getCurrentWattage();
                v.draw();
                infoText.drawString((int)(v.pos*ppm), 300-v.sprite.getHeight()/2, String.format("%.2f km/h", v.vel*3.6), Color.yellow);
            }
            infoText.drawString(10, 10, String.format("CURRENT WATTAGE: %.3f W", currentWattage), Color.yellow);
            Color.white.bind();

            Display.update();
			Display.sync(fps);
		}
		Display.destroy();
	}

}
