package com.bugbean.hurryball.core;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;

/**
 * 音乐管理器类
 */
public class MusicManager {

    public static MusicManager sMusicManager;

    /**
     * 存储音乐资源的数组
     */
    private AudioClip[] auus;

    /**
     * 存储音乐列表的数组
     */
    private String[] musicList = {
            "stereo_madness.wav"
    };

    private int size;

    private MusicManager() {
        auus = new AudioClip[musicList.length];
        for (int i = 0; i < musicList.length; i++) {
            add("sounds/" + musicList[i]);
        }
    }

    public static MusicManager getMusicManager() {
        if (sMusicManager == null) {
            sMusicManager = new MusicManager();
        }
        return sMusicManager;
    }

    /**
     * 播放指定音乐
     */
    public void play(int index) {
        auus[index].play();
    }

    /**
     * 停止指定音乐
     */
    public void stop(int index) {
        auus[index].stop();
    }

    /**
     * 向数组中添加音乐
     */
    public void add(String musicPath) {
        File file = new File(musicPath);
        auus[size++] = Applet.newAudioClip(Manifest.getResourceURL(musicPath));
    }
}
