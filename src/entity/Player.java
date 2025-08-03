package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
  GamePanel gamePanel;
  KeyHandler keyHandler;

  public final int playerX, playerY;

  public Player(GamePanel gamePanel, KeyHandler keyHandler) {
    this.gamePanel = gamePanel;
    this.keyHandler = keyHandler;
    this.playerX = (gamePanel.screenWidth - gamePanel.tileSize)/2;
    this.playerY = (gamePanel.screenHeight - gamePanel.tileSize)/2;
    this.setDefaultValues();
    this.getPlayerImage();

    // the collision area of the player
    this.solidArea = new Rectangle();
    this.solidArea.x = gamePanel.tileSize * 1/4;
    this.solidArea.y = gamePanel.tileSize * 1/4;
    this.solidArea.width = gamePanel.tileSize * 3/4;
    this.solidArea.height = gamePanel.tileSize * 3/4;
  }

  public void setDefaultValues() {
    this.worldX = gamePanel.tileSize * 23;
    this.worldY = gamePanel.tileSize * 21;
    this.speed = 4;
    this.direction = Direction.DOWN;
  }

  public void update() {
    // Check if any movement key is pressed
    boolean isMoving = keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed;
    
    // Player is not moving - set to standing position (sprite 1)
    if (!isMoving) {
      this.spriteNum = 1;
      this.frameCounter = 0;
      return;
    }

    // Change the player movement direction
    if (keyHandler.upPressed) {
      this.direction = Direction.UP;
    }
    if (keyHandler.downPressed) {
      this.direction = Direction.DOWN;
    }
    if (keyHandler.leftPressed) {
      this.direction = Direction.LEFT;
    }
    if (keyHandler.rightPressed) {
      this.direction = Direction.RIGHT;
    }

    // Move the player if it is not colliding with any solid tile
    boolean isPlayerColliding = gamePanel.collisionChecker.check(this);
    if (!isPlayerColliding) {
      switch (this.direction) {
        case UP:
          this.worldY -= this.speed;
          break;
        case DOWN:
          this.worldY += this.speed;
          break;
        case LEFT:
          this.worldX -= this.speed;
          break;
        case RIGHT:
          this.worldX += this.speed;
          break;
        default:
          break;
      }

      // Animate walking
      this.frameCounter++;
      if (this.frameCounter > 10) { // After every 10 frames (at 60 FPS, about 0.166 seconds), toggle spriteNum to switch the character's image and create a walking animation
        this.spriteNum = this.spriteNum == 1 ? 2 : 1;

        this.frameCounter = 0;
      }
    }
  }

  public void getPlayerImage() {
    try {
      this.up1 = ImageIO.read(new File("resources/player/boy_up_1.png"));
      this.up2 = ImageIO.read(new File("resources/player/boy_up_2.png"));
      this.down1 = ImageIO.read(new File("resources/player/boy_down_1.png"));
      this.down2 = ImageIO.read(new File("resources/player/boy_down_2.png"));
      this.right1 = ImageIO.read(new File("resources/player/boy_right_1.png"));
      this.right2 = ImageIO.read(new File("resources/player/boy_right_2.png"));
      this.left1 = ImageIO.read(new File("resources/player/boy_left_1.png"));
      this.left2 = ImageIO.read(new File("resources/player/boy_left_2.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D graphics2d) {
    switch (this.direction) {
      case UP:
        this.currentImage = this.spriteNum == 1 ? this.up1 : this.up2;
        break;
      case DOWN:
        this.currentImage = this.spriteNum == 1 ? this.down1 : this.down2;
        break;
      case LEFT:
        this.currentImage = this.spriteNum == 1 ? this.left1 : this.left2;
        break;
      case RIGHT:
        this.currentImage = this.spriteNum == 1 ? this.right1 : this.right2;
        break;
      default:
        this.currentImage = this.spriteNum == 1 ? this.down1 : this.down2;
        break;
    }

    graphics2d.drawImage(this.currentImage, this.playerX, this.playerY, gamePanel.tileSize, gamePanel.tileSize, null);
  }
}