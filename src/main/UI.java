package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.Key;

public class UI {
  GamePanel gamePanel;
  Font font;
  BufferedImage keyImage;

  public boolean messageOn = false;
  public String message = "";

  public UI(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
    this.font = new Font("Arial", Font.PLAIN, 24);
    
    Key key = new Key();
    this.keyImage = key.image;
  }

  public void draw(Graphics2D graphics2d) {
    graphics2d.setFont(this.font);
    graphics2d.setColor(Color.white);

    if (gamePanel.gameEnd) {
      String text = "You found the treasure";
      int textLength = (int) graphics2d.getFontMetrics().getStringBounds(text, graphics2d).getWidth();
      int x = (gamePanel.screenWidth - textLength) / 2;
      int y = gamePanel.screenHeight / 2 - gamePanel.tileSize * 3;
      graphics2d.drawString(text, x, y);
    }

    graphics2d.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, gamePanel.tileSize * 2/3, gamePanel.tileSize * 2/3, null);
    graphics2d.drawString("X " + gamePanel.player.keyCount, 60, 52);

    if (!this.messageOn) return;

    graphics2d.drawString(this.message, gamePanel.tileSize / 2, gamePanel.tileSize * 5);
  }

  public void showMessage(String message) {
    this.messageOn = true;
    this.message = message;

    setTimeout(() -> {
      this.messageOn = false;
    }, 2000);
  }

  public void setTimeout(Runnable runnable, int delay) {
    new Thread(() -> {
      try {
        Thread.sleep(delay);
        runnable.run();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }
}
