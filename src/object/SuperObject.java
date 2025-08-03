package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

// Parent class for all the objects
public class SuperObject {
  public BufferedImage image;
  public String name;
  public boolean collosion = false;
  public int worldX, worldY;

  public void draw(Graphics2D graphics2d, GamePanel gamePanel) {
    int screenX = this.worldX - gamePanel.player.worldX + gamePanel.player.playerX;
    int screenY = this.worldY - gamePanel.player.worldY + gamePanel.player.playerY;

    if (
      worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.playerX &&
      worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.playerX &&
      worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.playerY &&
      worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.playerY
    ) {
      graphics2d.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
  }
}
