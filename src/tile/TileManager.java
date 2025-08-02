package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
  GamePanel gamePanel;
  Tile[] tiles;
  int[][] tileMap;

  public TileManager(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
    this.tiles = new Tile[10]; // 10 types of tiles
    this.getTileImage();
    this.tileMap = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
    this.loadMap("resources/maps/map01.txt");
  }

  public void loadMap(String filePath) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));

      int col = 0, row = 0;

      while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
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
      tiles[0] = new Tile();
      tiles[0].image = ImageIO.read(new File("resources/tiles/grass.png"));
      
      tiles[1] = new Tile();
      tiles[1].image = ImageIO.read(new File("resources/tiles/wall.png"));

      tiles[2] = new Tile();
      tiles[2].image = ImageIO.read(new File("resources/tiles/water.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D graphics2d) {
    int col = 0, row = 0;

    while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
      int tileNum = tileMap[col][row];
      graphics2d.drawImage(tiles[tileNum].image, col * gamePanel.tileSize, row * gamePanel.tileSize, gamePanel.tileSize, gamePanel.tileSize, null);

      col++;

      if (col >= gamePanel.maxScreenCol) {
        col = 0;
        row++;
      }
    }
  }
}
