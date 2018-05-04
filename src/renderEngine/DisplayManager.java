package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import javax.swing.*;
import java.awt.*;

/**
 * 4/22/2018
 * Kyle Bankus (kcb2464)
 * <purpose>
 */
public class DisplayManager {

   private static final int WIDTH = 1280;
   private static final int HEIGHT = 720;
   private static final int FPS_CAP = 120;

   private static long lastFrameTime;
   private static float delta;

   public static void createDisplay() {

//      Canvas displaySurface = new Canvas();
//      JFrame frame = new JFrame();
//      frame.setSize(WIDTH, HEIGHT);
//      frame.add(displaySurface);
//      frame.setVisible(true);
//      frame.add(new JButton("gui button test"));
//      displaySurface.setSize(WIDTH + 100, HEIGHT + 100);
//      frame.setLocationRelativeTo(null);

      ContextAttribs attribs = new ContextAttribs(3, 3).
                                   withForwardCompatible(true).
                                   withProfileCore(true);

      try {
         //Display.setParent(displaySurface);
         //Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
         Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
         Display.create(new PixelFormat().withSamples(8), attribs);
         Display.setTitle("Ready.. Game!");
         GL11.glEnable(GL13.GL_MULTISAMPLE);
      }
      catch (LWJGLException e) {
         e.printStackTrace();
      }
      GL11.glViewport(0, 0, WIDTH, HEIGHT);
      lastFrameTime = getCurrentTime();
   }

   public static void updateDisplay() {
      Display.sync(FPS_CAP);
      Display.update();
      long currentFrameTime = getCurrentTime();
      delta = (currentFrameTime - lastFrameTime) / 1000.0f;
      lastFrameTime = currentFrameTime;
   }

   public static float getFrameTimeSeconds() {
      return delta;
   }

   public static void closeDisplay() {
      Display.destroy();
   }

   private static long getCurrentTime() {
      return Sys.getTime()*1000/Sys.getTimerResolution();
   }
}
