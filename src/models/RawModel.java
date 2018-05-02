package models;

/**
 * 4/22/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class RawModel {

   private int vaoID;
   private int vertexCount;

   public RawModel(int vaoID, int vertexCount) {
      this.vaoID = vaoID;
      this.vertexCount = vertexCount;
   }

   public int getVaoID() {
      return vaoID;
   }

   public int getVertexCount() {
      return vertexCount;
   }

}
