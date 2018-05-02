package levels;

import entities.Camera;
import entities.Player;
import models.TexturedModel;
import objConverter.ModelData;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import terrains.Terrain;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

/**
 * 4/30/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class Level {

   private ModelData dataObj = new ModelData();
   Loader loader = new Loader();
   Player player;

   public TerrainTexture backgroundTexture;
   public TerrainTexture       rTexture;
   public TerrainTexture       gTexture;
   public TerrainTexture       bTexture;
   public TerrainTexturePack texturePack;
   public TerrainTexture     blendMap;
   public Terrain terrain;


   public Level() {
   }


   public Player createPlayer(String objFile, Vector3f position, float x, float y, float z, float scale) {
      TexturedModel playerModel = dataObj.getTexturedModelData("objFile", "selfCharacterAttemptUV");
      player = new Player(playerModel, position, x, y, z, scale);
      return player;
   }


   public void levelOne() {
      this.backgroundTexture = new TerrainTexture(loader.loadTexture("grassy", "textures"));
      this.rTexture          = new TerrainTexture(loader.loadTexture("dirt", "textures"));
      this.gTexture          = new TerrainTexture(loader.loadTexture("pinkFlowers", "textures"));
      this.bTexture          = new TerrainTexture(loader.loadTexture("path", "textures"));

      this.texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
      this.blendMap    = new TerrainTexture(loader.loadTexture("blendMap3", "maps"));

      this.terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");

      TexturedModel lowPolyTree = dataObj.getTexturedModelData("lowPolyTree", "lowPolyTree");
      TexturedModel grass       = dataObj.getTexturedModelData("grassModel", "grassTexture");
      grass.getTexture().setHasTransparency(true);
      grass.getTexture().setUseFakeLighting(true);
      TexturedModel flower = dataObj.getTexturedModelData("grassModel", "flower");
      flower.getTexture().setHasTransparency(true);
      flower.getTexture().setUseFakeLighting(true);
      TexturedModel fern = dataObj.getTexturedModelData("fern", "fern");
      fern.getTexture().setHasTransparency(true);
      TexturedModel tree = dataObj.getTexturedModelData("tree", "tree");
   }

}
