package objConverter;

import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import textures.ModelTexture;

/**
 * 4/23/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class ModelData {

   private float[] vertices;
   private float[] textureCoords;
   private float[] normals;
   private int[]   indices;
   private float   furthestPoint;

   Loader ModelDataLoader = new Loader();

   public ModelData() {
   }

   public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices,
                    float furthestPoint) {
      this.vertices = vertices;
      this.textureCoords = textureCoords;
      this.normals = normals;
      this.indices = indices;
      this.furthestPoint = furthestPoint;
   }

   public float[] getVertices() {
      return vertices;
   }

   public float[] getTextureCoords() {
      return textureCoords;
   }

   public float[] getNormals() {
      return normals;
   }

   public int[] getIndices() {
      return indices;
   }

   public float getFurthestPoint() {
      return furthestPoint;
   }

   public TexturedModel getTexturedModelData(String objName, String texName) {
      ModelData object = (OBJFileLoader.loadOBJ(objName));
      RawModel rawObject = ModelDataLoader.loadToVAO(object.getVertices(),
                                            object.getTextureCoords(),
                                            object.getNormals(),
                                            object.getIndices());
      TexturedModel texturedObject = new TexturedModel(rawObject, new ModelTexture
          (ModelDataLoader.loadTexture(texName, "textures")));
      return texturedObject;
   }
}
