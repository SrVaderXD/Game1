package com.HLF.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	private AudioClip clip;
	
	public static final Sound backGround = new Sound("/backGround.wav");
	public static final Sound hurt = new Sound("/hurt.wav");
	public static final Sound enemyDamage = new Sound("/enemyDamage.wav");
	public static final Sound shurikenSound = new Sound("/shurikenSound.wav");
	public static final Sound shurikenWall = new Sound("/shurikenWall.wav");
	public static final Sound collectItem = new Sound("/collectItem.wav");
	public static final Sound collectShuri = new Sound("/collectShuri.wav");
	public static final Sound portal = new Sound("/portal.wav");
	
	private Sound(String name) {
		
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch(Throwable e) {}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch(Throwable e) {}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		} catch(Throwable e) {}
	}
}
