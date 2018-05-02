package models;

import textures.ModelTexture;

/**
 * 4/22/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class TexturedModel {

   private RawModel     rawModel;
   private ModelTexture texture;

   public TexturedModel(RawModel model, ModelTexture texture) {
      this.rawModel = model;
      this.texture = texture;
   }

   public RawModel getRawModel() {
      return rawModel;
   }

   public ModelTexture getTexture() {
      return texture;
   }
}
