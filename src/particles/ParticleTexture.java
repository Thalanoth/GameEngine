package particles;

/**
 * 4/28/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class ParticleTexture {

   private int textureID;
   private int numberOfRows;

   public ParticleTexture(int textureID, int numberOfRows) {
      this.textureID = textureID;
      this.numberOfRows = numberOfRows;
   }

   protected int getTextureID() {
      return textureID;
   }

   protected int getNumberOfRows() {
      return numberOfRows;
   }
}
