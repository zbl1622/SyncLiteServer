package com.zblcloud.synclite.server;

import com.zblcloud.synclite.server.util.ZLog;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.UUID;

public class SyncliteServer {
    private static final String TAG = "SyncliteServer";
    //"synclite"
    private byte[] keyArray = new byte[]{(byte) 115, (byte) 121, (byte) 110, (byte) 99, (byte) 108, (byte) 105, (byte) 116, (byte) 101};
    private String keyString = new String(keyArray);
    private static final String GROUP_IP = "224.0.0.1";
    private MulticastSocket multicastSocket;
    byte[] buffer = new byte[1024];
    private String serverID;

    public SyncliteServer() {
        ZLog.i(keyString);
        serverID = UUID.randomUUID().toString();
    }

    public void start() {
        try {
            multicastSocket = new MulticastSocket(7301);//服务端组播接收端口
            InetAddress group = InetAddress.getByName(GROUP_IP);
            multicastSocket.joinGroup(group);
            //开始监听
            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(datagramPacket); // 接收数据，进入阻塞状态

                byte[] message = new byte[datagramPacket.getLength()]; // 从buffer中截取收到的数据
                System.arraycopy(buffer, 0, message, 0, datagramPacket.getLength());
                String msg = new String(message).trim();
                ZLog.w(TAG, datagramPacket.getAddress().toString());
                ZLog.w(TAG, msg);
                if (keyString.equals(msg)) {
                    response(datagramPacket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response(DatagramPacket datagramPacket) {
        try {
            String clientHost = datagramPacket.getAddress().toString().replace("/", "");
            JSONObject msgJson = new JSONObject();
            msgJson.put("client_host", clientHost);
            msgJson.put("key", keyString);
            msgJson.put("name", TAG);
            msgJson.put("id", serverID);
            String jsonString = msgJson.toString();
            byte[] data = jsonString.getBytes();
            InetAddress group = InetAddress.getByName(GROUP_IP);
            DatagramPacket sendDP = new DatagramPacket(data, data.length, group, 7302);//客户端监听接收端口
            multicastSocket.send(sendDP);
            ZLog.w(TAG, "send: " + jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
