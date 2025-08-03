package main;

import entity.Entity;
import object.SuperObject;

public class CollisionChecker {
  public GamePanel gamePanel;

  public CollisionChecker(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  // This function will be used to check the collision of Player, NPCs and other characters
  public void checkTile(Entity entity) {
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

    // Check if entity is completely outside world bounds
    if (
      entityLeftCol < 0 ||
      entityRightCol >= gamePanel.maxWorldCol || 
      entityTopRow < 0 ||
      entityBottomRow >= gamePanel.maxWorldRow
    ) {
      entity.collisionOn = true;
      return;
    }

    // We only have to check the collision of only two corners at a time
    int tileNum1 = 0, tileNum2 = 0;

    switch (entity.direction) {
      case UP:
        entityTopRow = (entityTop - entity.speed) / gamePanel.tileSize; // (next entityTopRow) predicting the tile that the entity is going to hit after taking one step
        // Check bounds before accessing array
        if (entityTopRow < 0 || entityLeftCol < 0 || entityRightCol >= gamePanel.maxWorldCol) {
          entity.collisionOn = true;
          return;
        }
        tileNum1 = gamePanel.tileManager.tileMap[entityLeftCol][entityTopRow]; // top left corner
        tileNum2 = gamePanel.tileManager.tileMap[entityRightCol][entityTopRow]; // top right corner
        break;
      case DOWN:
        entityBottomRow = (entityBottom + entity.speed) / gamePanel.tileSize;
        // Check bounds before accessing array
        if (entityBottomRow >= gamePanel.maxWorldRow || entityLeftCol < 0 || entityRightCol >= gamePanel.maxWorldCol) {
          entity.collisionOn = true;
          return;
        }
        tileNum1 = gamePanel.tileManager.tileMap[entityLeftCol][entityBottomRow]; // bottom left corner
        tileNum2 = gamePanel.tileManager.tileMap[entityRightCol][entityBottomRow]; // bottom right corner
        break;
      case LEFT:
        entityLeftCol = (entityLeft - entity.speed) / gamePanel.tileSize;
        // Check bounds before accessing array
        if (entityLeftCol < 0 || entityTopRow < 0 || entityBottomRow >= gamePanel.maxWorldRow) {
          entity.collisionOn = true;
          return;
        }
        tileNum1 = gamePanel.tileManager.tileMap[entityLeftCol][entityTopRow]; // bottom left corner
        tileNum2 = gamePanel.tileManager.tileMap[entityLeftCol][entityBottomRow]; // bottom right corner
        break;
      case RIGHT:
        entityRightCol = (entityRight + entity.speed) / gamePanel.tileSize;
        // Check bounds before accessing array
        if (entityRightCol >= gamePanel.maxWorldCol || entityTopRow < 0 || entityBottomRow >= gamePanel.maxWorldRow) {
          entity.collisionOn = true;
          return;
        }
        tileNum1 = gamePanel.tileManager.tileMap[entityRightCol][entityTopRow]; // bottom left corner
        tileNum2 = gamePanel.tileManager.tileMap[entityRightCol][entityBottomRow]; // bottom right corner
        break;
      default:
        break;
    }

    entity.collisionOn = gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision;
  }

  public int checkObject(Entity entity, boolean isPlayer) {
    for (int i = 0; i < gamePanel.obj.length; i++) {
      SuperObject object = gamePanel.obj[i];
      if (object == null) continue;

      // Get entities solid area position
      entity.solidArea.x += entity.worldX;
      entity.solidArea.y += entity.worldY;

      // Get objects solid area position
      object.solidArea.x += object.worldX;
      object.solidArea.y += object.worldY;

      switch (entity.direction) {
        case UP:
          entity.solidArea.y -= entity.speed;
          break;
        case DOWN:
          entity.solidArea.y += entity.speed;
          break;
        case LEFT:
          entity.solidArea.x -= entity.speed;
          break;
        case RIGHT:
          entity.solidArea.x += entity.speed;
          break;
        default:
          break;
      }

      if (entity.solidArea.intersects(object.solidArea)) {
        entity.collisionOn = object.collosion;
        if (isPlayer) return i;
      }

      // Reset to default solid areas
      entity.solidArea.x = entity.solidAreaDefaultX;
      entity.solidArea.y = entity.solidAreaDefaultY;
      object.solidArea.x = object.solidAreaDefaultX;
      object.solidArea.y = object.solidAreaDefaultY;
    }

    return 999; // invalid idx for objects
  }
}