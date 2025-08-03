package object;

import java.io.File;

import javax.imageio.ImageIO;

public class Door extends SuperObject {
  public Door() {
    this.name = "Door";
    try {
      this.image = ImageIO.read(new File("resources/objects/door.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    this.collosion = true;
  }
}
