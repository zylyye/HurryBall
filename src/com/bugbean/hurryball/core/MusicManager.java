package com.bugbean.hurryball.core;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.ArrayList;
import java.util.List;

/**
 * 音乐管理器类
 */
public class MusicManager {

    private static MusicManager sMusicManager;

    /**
     * 存储音乐资源的数组
     */
    private List<AudioClip> auus;

    /**
     * 存储音乐列表的数组
     */
    private String[] musicList = {
            "stereo_madness.wav"
    };


    private MusicManager() {
        auus = new ArrayList<>(3);
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
        auus.get(index).play();
    }

    /**
     * 停止指定音乐
     */
    public void stop(int index) {
        auus.get(index).stop();
    }

    /**
     * 向添加音乐
     */
    public void add(String musicPath) {
        auus.add(Applet.newAudioClip(Manifest.getResourceURL(musicPath)));
    }
}
