import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.Font;
import java.util.LinkedList;
import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class GTA {

    static final boolean vSync = false;
    static final int fps = 60;		// Frames per second
    static final int ppm = 10;		// Pixels per meter
    static final int width = 1080;
    static final int height = 600;

    private LinkedList<Vehicle> vehicles;

    public static void main(String[] args){
            new GTA();
    }

    private GTA(){
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle("Simulator");
            Display.setVSyncEnabled(vSync);
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

        vehicles = new LinkedList<Vehicle>();
        int nVehicles = 11;

        // A roadblock that is one half width off-screen, makes the cars halt
        RoadBlock roadBlock = new RoadBlock((width-150)/ppm);

        long timePrev = System.nanoTime();

        while(!Display.isCloseRequested()){
            long time = System.nanoTime() - timePrev;
            timePrev += time;

            glClear(GL_COLOR_BUFFER_BIT);

                        // ROAD!
                        for (int i = 0; i < width; i += 64) {
                                road.draw(i, 236);
                        }
                        
            Vehicle n = roadBlock;
            for (Iterator<Vehicle> it = vehicles.iterator(); it.hasNext();) {
                Vehicle v = it.next();
                // Delete vehicles that stand still next to road block
                if ((v.distanceTo(roadBlock) < 4.0) && (v.engineOutput() < 0.01)) {
                    System.out.println(String.format("Remove vehicle %s", v));
                    it.remove();
                } else {
                    // Approximate a smoother simulation by integrating "in the middle" of
                    // each time segment. See Riemann sums for theory.
                    v.simulate(time/2e9, n);
                    v.simulate(time/2e9, n);
                }
                n = v;
            }

            // Create new cars
            while (nVehicles > vehicles.size()) {
                Vehicle v = CarFactory.random().produceVehicle();
                v.personality = Personality.standardDriver(v);
                v.vel = 1*3.6; // 1 km/h
                if (vehicles.size() > 0 && vehicles.getLast().pos < 0) {
                    v.pos = vehicles.getLast().pos - v.getLength() - 2.0;
                } else {
                    v.pos = -width/2.0 - v.getLength();
                }
                vehicles.addLast(v);
                System.out.println(String.format("Spawn vehicle %s", v));
            }

            // Calculate and draw info, cars
            Color.white.bind();
            double enginePower = 0;
            for (Vehicle v : vehicles) {
                enginePower += v.getEnginePower();
                v.draw();
                infoText.drawString((int)(v.pos*ppm), 300-v.sprite.getHeight()/2, String.format("%.2f km/h", v.vel/3.6), Color.white);
            }
            infoText.drawString(10, 10, String.format("TOTAL ENGINE POWER: %.3f W", enginePower), Color.yellow);
            Color.white.bind();

            Display.update();
            if (vSync) {
                Display.sync(fps);
            }
        }
        Display.destroy();
    }

}
