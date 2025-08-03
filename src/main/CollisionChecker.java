package main;

import entity.Entity;

public class CollisionChecker {
  public GamePanel gamePanel;

  public CollisionChecker(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  // This function will be used to check the collision of Player, NPCs and other characters
  public boolean check(Entity entity) {
    // Co-ordinates of the colliding part of the entity
    int entityLeft = entity.worldX + entity.solidArea.x;
    int entityRight = entityLeft + entity.solidArea.width;
    int entityTop = entity.worldY + entity.solidArea.y;
    int entityBottom = entityTop + entity.solidArea.height;

    // Row and Col nums of the entity
    int entityLeftCol = entityLeft / gamePanel.tileSize;
    int entityRightCol = entityRight / gamePanel.tileSize;
    int entityTopRow = entityTop / gamePanel.tileSize;
    int entityBottomRow = entityBottom / gamePanel.tileSize;

    // We only have to check the collision of only two corners at a time
    int tileNum1 = 0, tileNum2 = 0;

    switch (entity.direction) {
      case UP:
        entityTopRow = (entityTop - entity.speed) / gamePanel.tileSize; // (next entityTopRow) predicting the tile that the entity is going to hit after taking one step
        tileNum1 = gamePanel.tileManager.tileMap[entityLeftCol][entityTopRow]; // top left corner
        tileNum2 = gamePanel.tileManager.tileMap[entityRightCol][entityTopRow]; // top right corner
        break;
      case DOWN:
        entityBottomRow = (entityBottom + entity.speed) / gamePanel.tileSize;
        tileNum1 = gamePanel.tileManager.tileMap[entityLeftCol][entityBottomRow]; // bottom left corner
        tileNum2 = gamePanel.tileManager.tileMap[entityRightCol][entityBottomRow]; // bottom right corner
        break;
      case LEFT:
        entityLeftCol = (entityLeft - entity.speed) / gamePanel.tileSize;
        tileNum1 = gamePanel.tileManager.tileMap[entityLeftCol][entityTopRow]; // bottom left corner
        tileNum2 = gamePanel.tileManager.tileMap[entityLeftCol][entityBottomRow]; // bottom right corner
        break;
      case RIGHT:
        entityRightCol = (entityRight + entity.speed) / gamePanel.tileSize;
        tileNum1 = gamePanel.tileManager.tileMap[entityRightCol][entityTopRow]; // bottom left corner
        tileNum2 = gamePanel.tileManager.tileMap[entityRightCol][entityBottomRow]; // bottom right corner
        break;
      default:
        break;
    }

    return gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision;
  }
}