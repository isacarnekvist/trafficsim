import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.util.Random;
import java.util.LinkedList;
import java.util.Iterator;

public class GTA {
	
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
			Display.setTitle("Hello!");
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Display error");
			System.exit(0);
		}
		
		// OpenGL setup
		glClearColor(0f, 0f, 0f, 1);
		glEnable(GL_TEXTURE_2D);
		glOrtho(0, width, height, 0, 1, -1);
		glEnable(GL_BLEND); 
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Sprite road = new Sprite("res/road.png", 128, 128);
		Random rand = new Random();
		
		vehicles = new LinkedList<Vehicle>();
		vehicles.addLast(new Vehicle(rand.nextInt(NUM_CAR_MODELS), 0, new Personality(30, 5, 70)));
		
		for(int i = 0; i < 9; i++) {
			vehicles.addFirst(new Vehicle(rand.nextInt(7), vehicles.getFirst().pos+150, new Personality(30, 5, 70)));
		}
		
		while(!Display.isCloseRequested()){
			glClear(GL_COLOR_BUFFER_BIT);
			
			for(int i = 0; i < width; i += 64) {
				road.draw(i, 236);
			}
			
			boolean removedVehicle = false;
			Iterator<Vehicle> it = vehicles.iterator();
			while (it.hasNext()) {
				Vehicle v = it.next();
				if(v.pos > width) {	// Delete if outside of screen
					it.remove();
					removedVehicle = true;
				} else {
					v.pos += 2;
					v.draw();
				}
			}
			
			if(removedVehicle) {
				Vehicle v = new Vehicle(rand.nextInt(NUM_CAR_MODELS),
						vehicles.getLast().pos - 150,
						new Personality(30, 5, 70));
				vehicles.addLast(v);
			}
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}

}
