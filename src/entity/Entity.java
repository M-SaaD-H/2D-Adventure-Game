package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
  public int worldX, worldY;
  public int speed;

  public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
  public BufferedImage currentImage;
  public enum Direction {
    UP, DOWN, LEFT, RIGHT
  }
  public Direction direction;

  // To animate the player moving
  public int frameCounter = 0; // we will use this to cout the frames (60FPS) and then change the spriteNum to animate the character
  public int spriteNum = 1;

  // for collision of character
  public Rectangle solidArea;
  public boolean collisionOn;
  public int solidAreaDefaultX, solidAreaDefaultY;
}
