package object;

import java.io.File;

import javax.imageio.ImageIO;

public class Chest extends SuperObject {
  public Chest() {
    this.name = "Chest";
    try {
      this.image = ImageIO.read(new File("resources/objects/chest.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
