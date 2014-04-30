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
		vehicles.addLast(new Vehicle(rand.nextInt(NUM_CAR_MODELS), 0, new Personality(30, 5, 70)));
		
		for(int i = 0; i < 7; i++) {
			vehicles.addFirst(new Vehicle(rand.nextInt(7), vehicles.getFirst().pos+20, new Personality(30, 5, 70)));
		}
		
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			
			// ROAD!
			for(int i = 0; i < width; i += 64) {
				road.draw(i, 236);
			}
			
			// Notify cars of distances
			Vehicle nextCar = null;
			for(Vehicle v : vehicles) {
				if(nextCar != null) {
					v.setNextCarData(nextCar.pos - v.pos - v.getLength(), // Distance to next
							nextCar.vel, nextCar.accel);
				} else { // Let last car be in front of first, otherwise weird performance, better solution?
					Vehicle firstCar = vehicles.getLast();
					v.setNextCarData(width/ppm - v.pos - v.getLength() + firstCar.pos,
							firstCar.vel, firstCar.accel);
				}
				nextCar = v;
			}
			
			// Calculate and draw info
			double currentWattage = 0;
			for (Vehicle v : vehicles) {
				currentWattage += v.getCurrentWattage();
			}
			infoText.drawString(10, 10, "CURRENT WATTAGE: " + (int)currentWattage, Color.yellow);
			Color.white.bind();
			
			// Draw or delete if outside screen
			boolean removedVehicle = false;
			Iterator<Vehicle> it = vehicles.iterator();
			while (it.hasNext()) {
				Vehicle v = it.next();
				if(v.pos > width/ppm) {	// Delete if outside of screen
					it.remove();
					removedVehicle = true;
				} else {
					v.draw(1000/fps);
				}
			}
			
			// Create new cars
			if(removedVehicle) {
				Vehicle v = new Vehicle(rand.nextInt(NUM_CAR_MODELS),
						0,
						new Personality(30, 5, 70));
				
				double newPos = vehicles.getLast().pos - v.getLength() - 1;
				if (newPos + v.getLength() > 0) {
					v.pos = -v.getLength();
				} else {
					v.pos = newPos;
				}
				vehicles.addLast(v);
			}
			
			Display.update();
			Display.sync(fps);
		}
		Display.destroy();
	}

}
