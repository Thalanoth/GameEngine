package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import levels.Level;
import levels.LevelObjectLayout;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.*;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 4/22/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class MainGameLoop {

   public static void main(String[] args) {

      DisplayManager.createDisplay();
      Loader loader = new Loader();
      TextMaster.init(loader);

      ModelData dataObj = new ModelData();

      List<Entity> entities = new ArrayList<Entity>();
      List<Terrain> terrains = new ArrayList<Terrain>();
      List<Entity> normalMapEntities = new ArrayList<Entity>();
      List<Light> lights = new ArrayList<Light>();
      List<WaterTile> waters = new ArrayList<WaterTile>();
      List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();

      LevelObjectLayout level = new LevelObjectLayout();

      TexturedModel playerModel = dataObj.getTexturedModelData("selfCharacterAttempt", "selfCharacterAttemptUV");
      Player player = new Player(playerModel, new Vector3f(512, 5, 0), 0, 180, 0, 1.0f);
      Camera camera = new Camera(player);
      entities.add(player);

      MasterRenderer renderer = new MasterRenderer(loader, camera);
      ParticleMaster.init(loader, renderer.getProjectionMatrix());


      TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2", "textures"));
      TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt", "textures"));
      TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers", "textures"));
      TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path", "textures"));


      TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
      TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap4", "maps"));


      Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMapFlatScaleTest");
      terrains.add(terrain);

      ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fernTextureAtlas", "textures"));
      fernTextureAtlas.setNumberOfRows(2);

      TexturedModel lowPolyTree = dataObj.getTexturedModelData("lowPolyTree", "lowPolyTree");
      TexturedModel grass = dataObj.getTexturedModelData("grassModel", "grassTexture");
      grass.getTexture().setHasTransparency(true);
      grass.getTexture().setUseFakeLighting(true);
      TexturedModel flower = dataObj.getTexturedModelData("grassModel", "flower");
      flower.getTexture().setHasTransparency(true);
      flower.getTexture().setUseFakeLighting(true);
      TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
      //TexturedModel fern = dataObj.getTexturedModelData("fern", "fern");
      fern.getTexture().setHasTransparency(true);
      TexturedModel tree = dataObj.getTexturedModelData("tree", "tree");

      TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
                                                    new ModelTexture(loader.loadTexture("barrel", "normals")));
      barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal", "normals"));
      barrelModel.getTexture().setShineDamper(10);
      barrelModel.getTexture().setReflectivity(0.5f);

      Random random = new Random();


      List<Vector2f> coordVectors = level.getObjectMapLocations("objectMapTest", 150, 215, 30);
      for (int i = 0; i < coordVectors.size(); i++) {
         float x = (coordVectors.get(i).x) * 8f;
         float z = (coordVectors.get(i).y) * -8f;
         float y = terrain.getHeightOfTerrain(x, z);
         entities.add(new Entity(lowPolyTree, new Vector3f(x, y, z), 0, 0, 0, 1.9f));
      }

      coordVectors = level.getObjectMapLocations("objectMapTest", 35, 255, 60);
      for (int i = 0; i < coordVectors.size(); i++) {
         float x = (coordVectors.get(i).x);
         float z = (coordVectors.get(i).y);
         float y = terrain.getHeightOfTerrain(x, z);
         entities.add(new Entity(tree, new Vector3f(x, y, z), 0, 0, 0, 1.9f));
      }

      coordVectors = level.getObjectMapLocations("objectMapTest", 220, 250, 70);
      for (int i = 0; i < coordVectors.size(); i++) {
         float x = (coordVectors.get(i).x) * 8f;
         float z = (coordVectors.get(i).y) * -8f;
         float y = terrain.getHeightOfTerrain(x, z);
         entities.add(new Entity(fern, 3, new Vector3f(x, y, z), 0, 0, 0, 1.0f));
      }

      coordVectors = level.getObjectMapLocations("objectMapTest", 155, 95, 30);
      for (int i = 0; i < coordVectors.size(); i++) {
         float x = (coordVectors.get(i).x) * 8f;
         float z = (coordVectors.get(i).y) * -8f;
         float y = terrain.getHeightOfTerrain(x, z);
         entities.add(new Entity(grass, new Vector3f(x, y, z), 0, 0, 0, 1.9f));
      }
      coordVectors = level.getObjectMapLocations("objectMapTest", 250, 60, 215);
      for (int i = 0; i < coordVectors.size(); i++) {
         float x = (coordVectors.get(i).x) * 6.25f;
         float z = (coordVectors.get(i).y) * -6.25f;
         float y = terrain.getHeightOfTerrain(x, z);
         entities.add(new Entity(flower, new Vector3f(x, y, z), 0, 0, 0, 1.9f));
      }

      coordVectors = level.getObjectMapLocations("objectMapTest", 70, 50, 50);
      for (int i = 0; i < coordVectors.size(); i++) {
         float x = (coordVectors.get(i).x) * 8f;
         float z = (coordVectors.get(i).y) * -8f;
         float y = terrain.getHeightOfTerrain(x, z);
         normalMapEntities.add(new Entity(barrelModel, new Vector3f(x, y+10.2f, z), 0, 0, 0, 1.9f));
      }


      Light sun = new Light (new Vector3f(200000, 400000, -200000), new Vector3f(1.3f, 1.3f, 1.3f));
      lights.add(sun);


//      GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(), new Vector2f(0.5f, 0.5f),
//                                             new Vector2f(0.5f, 0.5f));


      GuiTexture healthBar = new GuiTexture(loader.loadTexture("healthBar", "gui"), new Vector2f(-0.7f, -0.9f),
                                                                     new Vector2f(0.25f, 0.1f));
      guiTextures.add(healthBar);
      //guiTextures.add(shadowMap);
      GuiRenderer guiRenderer = new GuiRenderer(loader);

      MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix());

      WaterFrameBuffers buffers = new WaterFrameBuffers();
      WaterShader waterShader = new WaterShader();
      WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
      WaterTile water = new WaterTile(75, -75, 0);
      waters.add(water);

      ParticleMaster.renderParticles(camera);

      FontType font = new FontType(loader.loadTexture(("candara"), "fonts"), new File("res/fonts/candara.fnt"));
      GUIText text = new GUIText("testing, testing, 1, 2, 3 lol!", 3, font, new Vector2f(0.0f, 0.9f), 1f, true);
      text.setColor(0.8f, 0.9f, 0.1f);

      ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("alphapizza", "particles"), 1);

      ParticleSystem system = new ParticleSystem(particleTexture, 40, 10, 0.01f, 6, 1.0f);
      system.setLifeError(0.0f);
      system.setDirection(new Vector3f(0, 1, 0), 0.1f);
      system.setSpeedError(0.0f);
      system.setScaleError(0.0f);
      system.randomizeRotation();

      Fbo multisampleFbo = new Fbo(Display.getWidth(), Display.getHeight());
      Fbo outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
      Fbo outputFbo2 = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
      PostProcessing.init(loader);


      while(!Display.isCloseRequested()) {


         player.move(terrain);
         camera.move();
         picker.update();

         system.generateParticles(player.getPosition());

         ParticleMaster.update(camera);

         renderer.renderShadowMap(entities, sun);

         GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

         buffers.bindReflectionFrameBuffer();
         float distance = 2 * (camera.getPosition().y - water.getHeight());
         camera.getPosition().y -= distance;
         camera.invertPitch();
         renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,
                              new Vector4f(0, 1, 0, -water.getHeight()));
         camera.getPosition().y += distance;
         camera.invertPitch();

         buffers.bindRefractionFrameBuffer();
         renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,
                              new Vector4f(0, -1, 0, water.getHeight()));

         GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
         buffers.unbindCurrentFrameBuffer();

         multisampleFbo.bindFrameBuffer();
         renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,
                              new Vector4f(0, 1, 0, 30));
         waterRenderer.render(waters, camera, sun);
         ParticleMaster.renderParticles(camera);
         multisampleFbo.unbindFrameBuffer();
         multisampleFbo.resolveToFBO(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
         multisampleFbo.resolveToFBO(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
         PostProcessing.doPostProcessing(outputFbo.getColorTexture(), outputFbo2.getColorTexture());

         guiRenderer.render(guiTextures);
         TextMaster.render();

         DisplayManager.updateDisplay();
      }

      PostProcessing.cleanUp();
      outputFbo.cleanUp();
      outputFbo2.cleanUp();
      multisampleFbo.cleanUp();
      ParticleMaster.cleanUp();
      TextMaster.cleanUp();
      buffers.cleanUp();
      waterShader.cleanUp();
      guiRenderer.cleanUp();
      renderer.cleanUp();
      loader.cleanUp();
      DisplayManager.closeDisplay();

   }
}
