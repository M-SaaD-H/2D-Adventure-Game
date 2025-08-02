package main;

import javax.swing.JPanel;

import entity.Player;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable {
  final int originalTileSize = 16; // 16x16 tile -> but we will scale it up
  final int scale = 3;
  public final int tileSize = originalTileSize * scale; // 48x48 tile

  final int maxScreenCol = 16;
  final int maxScreenRow = 12;

  final int screenWidth = tileSize * maxScreenCol;
  final int screenHeight = tileSize * maxScreenRow;

  final int fps = 60; // fps for the game

  Thread gameThread;
  final KeyHandler keyHandler = new KeyHandler();
  Player player = new Player(this, this.keyHandler);

  // Initial player position
  int playerX = 100;
  int playerY = 100;
  int playerSpeed = 4;

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
    this.startGameThread();
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double timePerFrame = 1000000000 / fps; // 1000000000 nanosec = 1 sec
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;

    while (gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / timePerFrame;

      if (delta >= 1) {
        this.player.update(); // update the position of the player
        repaint(); // repaint the player
        delta--;
      }

      lastTime = currentTime;
    }
  }

  public void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);

    Graphics2D graphics2d = (Graphics2D) graphics;

    this.player.draw(graphics2d);

    graphics2d.dispose();
  }
}
