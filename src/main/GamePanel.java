package main;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class GamePanel extends JPanel implements Runnable {
  final int originalTileSize = 16; // 16x16 tile -> but we will scale it up
  final int scale = 3;
  public final int tileSize = originalTileSize * scale; // 48x48 tile

  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;

  public final int screenWidth = tileSize * maxScreenCol;
  public final int screenHeight = tileSize * maxScreenRow;

  final int fps = 60; // fps for the game

  // World configs
  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50;

  public final int worldWidth = maxWorldCol * tileSize;
  public final int worldHeight = maxWorldRow * tileSize;

  Thread gameThread;
  final KeyHandler keyHandler = new KeyHandler();
  public Player player = new Player(this, this.keyHandler);
  TileManager tileManager = new TileManager(this);
  public CollisionChecker collisionChecker = new CollisionChecker(this);
  public AssetSetter assetSetter = new AssetSetter(this);

  // In game objects
  public SuperObject obj[] = new SuperObject[10]; // 10 objects will be spawned at a time

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
    this.setUpGame();
    this.startGameThread();
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  public void setUpGame() {
    assetSetter.setObjects();
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

    this.tileManager.draw(graphics2d);

    for (SuperObject object : obj) {
      if (object != null) {
        object.draw(graphics2d, this);
      }
    }
    
    this.player.draw(graphics2d);

    graphics2d.dispose();
  }
}
