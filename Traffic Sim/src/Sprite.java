import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import static org.lwjgl.opengl.GL11.*;

public class Sprite {
	
	private Texture texture;
	private int fileWidth;
	private int imgHeight;
	private int imgWidth;
	
	/**
	 * Constructor of self-drawing image.
	 * @param filename Must be of size 2^n*2^n and type *.png
	 * @param imgHeight Height of image within file, i.e <= height of file
	 * @param imgWidth Widht of image within file, i.e <= width of file
	 */
	public Sprite(String filename, int imgHeight, int imgWidth) {
		texture = loadTexture(filename);
		fileWidth = texture.getImageWidth();
		this.imgHeight = imgHeight;
		this.imgWidth = imgWidth;
	}
	
	private Texture loadTexture(String filename) {
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File(filename)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);			
		}
		
		return null;
	}
	
	/**
	 * @param posX x-coordinate of upper left corner
	 * @param posY y-coordinate of upper left corner
	 */
	public void draw(int posX, int posY) {
		texture.bind();
		glBegin(GL_QUADS);
		{
			glTexCoord2d(0, 0);
			glVertex2d(posX, posY);
			glTexCoord2d(0, (double)imgHeight/fileWidth);
			glVertex2d(posX, posY+imgHeight);
			glTexCoord2d((double)imgWidth/fileWidth, (double)imgHeight/fileWidth);
			glVertex2d(posX+imgWidth, posY+imgHeight);
			glTexCoord2d((double)imgWidth/fileWidth, 0);
			glVertex2d(posX+imgWidth, posY);
		}
		glEnd();
	}
	
	public int getWidth() {
		return imgWidth;
	}
	
	public int getHeight() {
		return imgHeight;
	}
	
}
