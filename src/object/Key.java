package object;

import java.io.File;

import javax.imageio.ImageIO;

public class Key extends SuperObject {
  public Key() {
    this.name = "Key";
    try {
      this.image = ImageIO.read(new File("resources/objects/key.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
