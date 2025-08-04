package main;

import java.io.File;
import java.net.URL;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
  Clip clip;
  URL[] soundPath = new URL[10];

  public Sound() {
    try {
      this.soundPath[0] = new File("resources/sounds/BlueBoyAdventure.wav").toURI().toURL();
      this.soundPath[1] = new File("resources/sounds/coin.wav").toURI().toURL();
      this.soundPath[2] = new File("resources/sounds/powerup.wav").toURI().toURL();
      this.soundPath[3] = new File("resources/sounds/fanfare.wav").toURI().toURL();
      this.soundPath[4] = new File("resources/sounds/unlock.wav").toURI().toURL();
    } catch (MalformedURLException e) {
      System.out.println("Error creating sound URLs: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void setFile(int idx) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(this.soundPath[idx]);
      this.clip = AudioSystem.getClip();
      this.clip.open(ais);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void play() {
    this.clip.start();
  }

  public void loop() {
    this.clip.loop(Clip.LOOP_CONTINUOUSLY);
  }
  
  public void pause() {
    this.clip.stop();
  }
}
