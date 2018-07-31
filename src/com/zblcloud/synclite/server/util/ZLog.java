package com.zblcloud.synclite.server.util;

public class ZLog {

    private static final String DEFAULT_TAG = "ZLog";

    public static void i(String tag, String msg) {
        System.out.println(tag + ":" + msg);
    }

    public static void w(String tag, String msg) {
        System.out.println(tag + ":" + msg);
    }

    public static void e(String tag, String msg) {
        System.out.println(tag + ":" + msg);
    }

    public static void v(String tag, String msg) {
        System.out.println(tag + ":" + msg);
    }

    public static void d(String tag, String msg) {
        System.out.println(tag + ":" + msg);
    }

    public static void i(String msg) {
        System.out.println(msg);
    }

    public static void w(String msg) {
        System.out.println(msg);
    }

    public static void e(String msg) {
        System.out.println(msg);
    }

    public static void v(String msg) {
        System.out.println(msg);
    }

    public static void d(String msg) {
        System.out.println(msg);
    }
}
