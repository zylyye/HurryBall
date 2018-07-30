package com.bugbean.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerManager {
    private int port = 8989;
    private static ServerSocket mServerSocket;
    private String charset = "UTF-8";
    private List<HandlerThread> mHandlerThreads;

    public ServerManager() throws IOException {
        mHandlerThreads = new ArrayList<>(5);
        if (mServerSocket == null) {
            mServerSocket = new ServerSocket(port);
        }
    }

    private class HandlerThread implements Runnable {
        private int mId;
        private Socket mSocket;
        private Scanner mScanner;
        private PrintWriter mPrintWriter;

        public HandlerThread(Socket socket,int id) {
            mId = id;
            mSocket = socket;
        }
        @Override
        public void run() {
            try {
                InputStream inStream = mSocket.getInputStream();
                OutputStream outStream = mSocket.getOutputStream();
                mScanner = new Scanner(inStream, charset);
                mPrintWriter = new PrintWriter(new OutputStreamWriter(outStream, charset), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
