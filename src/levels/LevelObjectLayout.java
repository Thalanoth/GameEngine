package levels;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 4/30/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class LevelObjectLayout {

   public LevelObjectLayout() {
   }

   public List<Vector2f> getObjectMapLocations(String objectMap, int r, int g, int b) {
      BufferedImage image = null;
      try {
         image = ImageIO.read(new File("res/maps/"+objectMap+".png"));
      }
      catch (IOException e) {
         e.printStackTrace();
      }
      int mapSize = image.getHeight();

      List<Vector2f> coordVectors = new ArrayList<>();

      for (int x = 0; x < mapSize; x++) {
         for (int z = 0; z < mapSize; z++) {
            Vector3f color = getPixelColor(x, z, image);
            if (color.x == r && color.y == g && color.z == b) {
               coordVectors.add(new Vector2f(x, z));
            }
         }
      }
      return coordVectors;
   }

   private Vector3f getPixelColor(int x, int z, BufferedImage image) {
      if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
         return null;
      }
      int pixel = image.getRGB(x, z);
      int red = (pixel >> 16) & 0xff;
      int green = (pixel >> 8) & 0xff;
      int blue = (pixel) & 0xff;
      Vector3f color = new Vector3f(red, green, blue);
      return color;
   }
}
