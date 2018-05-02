package textures;

import java.nio.ByteBuffer;

/**
 * 4/24/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class TextureData {

   private int width;
   private int height;
   private ByteBuffer buffer;

   public TextureData(ByteBuffer buffer, int width, int height) {
      this.buffer = buffer;
      this.width = width;
      this.height = height;
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
   }

   public ByteBuffer getBuffer() {
      return buffer;
   }
}
