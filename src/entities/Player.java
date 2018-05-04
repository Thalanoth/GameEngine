package entities;

import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import terrains.Terrain;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 4/23/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class Player extends Entity {

   private static final float RUN_SPEED = 200;
   private static final float TURN_SPEED = 160;
   public static final float GRAVITY = -400;
   private static final float JUMP_POWER = 100;

   private float currentSpeed = 0;
   private float currentTurnSpeed = 0;
   private float upwardsSpeed = 0;

   private boolean isInAir = false;

   private static final float TERRAIN_HEIGHT = 0;


   public Player() {
   }

   public Player(TexturedModel model, Vector3f position, float rotX,
                 float rotY, float rotZ, float scale) {
      super(model, position, rotX, rotY, rotZ, scale);
   }

   public float getCurrentSpeed() {
      return currentSpeed;
   }

   public void move(Terrain terrain) {
      checkInputs();
      super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
      float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
      float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
      float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
      super.increasePosition(dx, 0, dz);
      upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
      super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
      float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
      if (super.getPosition().y < terrainHeight) {
         upwardsSpeed = 0;
         isInAir = false;
         super.getPosition().y = terrainHeight;
      }
   }

   private void jump() {
      if (!isInAir) {
         this.upwardsSpeed = JUMP_POWER;
         isInAir = true;
      }
   }

   public void checkInputs() {
      if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
         this.currentSpeed = RUN_SPEED;
      } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
         this.currentSpeed = -RUN_SPEED;
      } else {
         this.currentSpeed = 0;
      }

      if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
         this.currentTurnSpeed = -TURN_SPEED;
      } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
         this.currentTurnSpeed = TURN_SPEED;
      } else {
         this.currentTurnSpeed = 0;
      }

      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
         jump();
      }

   }
}
