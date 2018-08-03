package com.bugbean.hurryball.kernel;


import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class MusicManager {

    public static MusicManager sMusicManager;

    private AudioClip[] auus;
    private String[] musicList = {
        "stereo_madness.wav"
    };
    private int size;

    private MusicManager() {
        auus = new AudioClip[musicList.length];
        for (int i = 0; i < musicList.length; i++) {
            add("sounds/"+musicList[i]);
        }
    }

    public static MusicManager getMusicManager() {
        if (sMusicManager == null) {
            sMusicManager = new MusicManager();
        }
        return sMusicManager;
    }

    public void play(int index) {
        auus[index].play();
    }

    public void stop(int index) {
        auus[index].stop();
    }

    public void add(String musicPath) {
        File file = new File(musicPath);
        try {
            auus[size++] = Applet.newAudioClip(file.toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
