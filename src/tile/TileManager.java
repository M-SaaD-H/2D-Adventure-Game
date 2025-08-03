package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
  GamePanel gamePanel;
  public Tile[] tiles;
  public int[][] tileMap;

  public TileManager(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
    this.tiles = new Tile[10]; // 10 types of tiles
    this.getTileImage();

    // For still world and moving character
    // this.tileMap = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

    // For still character and moving map
    this.tileMap = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

    this.loadMap("resources/maps/world01.txt");
  }

  public void loadMap(String filePath) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));

      int col = 0, row = 0;

      while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
        String[] mapNums = br.readLine().split(" ");

        for (String num : mapNums) {
          tileMap[col][row] = Integer.parseInt(num);
          col++;
        }
        
        col = 0;
        row++;
      }

      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void getTileImage() {
    try {
      // TODO: improve it
      tiles[0] = new Tile();
      tiles[0].image = ImageIO.read(new File("resources/tiles/grass.png"));
      
      tiles[1] = new Tile();
      tiles[1].image = ImageIO.read(new File("resources/tiles/wall.png"));
      tiles[1].collision = true;
      
      tiles[2] = new Tile();
      tiles[2].image = ImageIO.read(new File("resources/tiles/water.png"));
      tiles[2].collision = true;
      
      tiles[3] = new Tile();
      tiles[3].image = ImageIO.read(new File("resources/tiles/earth.png"));
      
      tiles[4] = new Tile();
      tiles[4].image = ImageIO.read(new File("resources/tiles/tree.png"));
      tiles[4].collision = true;

      tiles[5] = new Tile();
      tiles[5].image = ImageIO.read(new File("resources/tiles/sand.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D graphics2d) {
    int worldCol = 0, worldRow = 0;

    while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
      int tileNum = tileMap[worldCol][worldRow];

      int worldX = worldCol * gamePanel.tileSize;
      int worldY = worldRow * gamePanel.tileSize;
      int screenX = worldX - gamePanel.player.worldX + gamePanel.player.playerX;
      int screenY = worldY - gamePanel.player.worldY + gamePanel.player.playerY;
      
      // This if is for performance -> redering only the required part
      if (
        worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.playerX &&
        worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.playerX &&
        worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.playerY &&
        worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.playerY
      ) {
        graphics2d.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
      }

      worldCol++;

      if (worldCol >= gamePanel.maxWorldCol) {
        worldCol = 0;
        worldRow++;
      }
    }
  }
}
