package guis;

import org.lwjgl.util.vector.Vector2f;

/**
 * 4/24/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class GuiTexture {

   private int      texture;
   private Vector2f position;
   private Vector2f scale;

   public GuiTexture(int texture, Vector2f position, Vector2f scale) {
      this.texture = texture;
      this.position = position;
      this.scale = scale;
   }

   public int getTexture() {
      return texture;
   }

   public Vector2f getPosition() {
      return position;
   }

   public Vector2f getScale() {
      return scale;
   }
}
