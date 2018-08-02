package com.zblcloud.synclite.server;

public class Main {

    public static void main(String[] args) {
        new Thread(() -> {
            SyncliteServer syncliteServer = new SyncliteServer();
            syncliteServer.start();
        }).start();

//        new Thread(() -> {
//        }).start();
        SyncliteFtpServer syncliteFtpServer = new SyncliteFtpServer();
        syncliteFtpServer.start();
    }
}
