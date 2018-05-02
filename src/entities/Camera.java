package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * 4/23/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class Camera {

   private float distanceFromPlayer = 50;
   private float angleAroundPlayer = 0;

   private Vector3f position = new Vector3f(0, 0, 0);
   private float pitch = 20;
   private float yaw;
   private float roll;

   private Player player;

   public Camera(Player player) {
      this.player = player;
   }

   public void move() {
      calculateZoom();
      calculatePitch();
      calculateAngleAroundPlayer();
      float horizontalDistance = calculateHorizontalDistance();
      float verticalDistance = calculateVerticalDistance();
      calculateCameraPosition(horizontalDistance, verticalDistance);
      this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
      yaw%=360;
   }

   public void invertPitch() {
      this.pitch = -pitch;
   }

   public Vector3f getPosition() {
      return position;
   }

   public float getPitch() {
      return pitch;
   }

   public float getYaw() {
      return yaw;
   }

   public float getRoll() {
      return roll;
   }

   private void calculateCameraPosition(float hDistance, float vDistance) {
      float theta = player.getRotY() + angleAroundPlayer;
      float offsetX = (float) (hDistance * Math.sin(Math.toRadians(theta)));
      float offsetZ = (float) (hDistance * Math.cos(Math.toRadians(theta)));
      position.x = player.getPosition().x - offsetX;
      position.z = player.getPosition().z - offsetZ;
      position.y = player.getPosition().y + 5 + vDistance;
   }

   private float calculateHorizontalDistance() {
      float hDistance = (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
      if (hDistance < 0) hDistance = 0;
      return hDistance;
   }

   private float calculateVerticalDistance() {
      float vDistance = (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
      if (vDistance < 0) vDistance = 0;
      return vDistance;
   }

   private void calculateZoom() {
      float zoomLevel = Mouse.getDWheel() * 0.09f;
      distanceFromPlayer -= zoomLevel;
      if (distanceFromPlayer < 5.0f) distanceFromPlayer = 5.0f;
      if (distanceFromPlayer > 300.0f) distanceFromPlayer = 300.0f;
   }

   private void calculatePitch() {
      if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
         float pitchChange = Mouse.getDY() * 0.1f;
         pitch -= pitchChange;
         if (pitch < 0) pitch = 0;
         else if (pitch > 90) pitch = 90;
      }
   }

   private void calculateAngleAroundPlayer() {
      if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
         float angleChange = Mouse.getDX() * 0.1f;
         angleAroundPlayer -= angleChange;
      }
   }
}
