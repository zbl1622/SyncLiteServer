package com.zblcloud.synclite.server;

import com.zblcloud.synclite.server.util.ZLog;
import org.apache.ftpserver.DataConnectionConfiguration;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.ipfilter.SessionFilter;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfiguration;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.apache.mina.filter.firewall.Subnet;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SyncliteFtpServer {
    private static final String TAG = "SyncliteFtpServer";

    public SyncliteFtpServer() {
    }

    public void start() {

        try {
            FtpServerFactory serverFactory = new FtpServerFactory();

            ListenerFactory factory = new ListenerFactory();
            //设置监听端口
            factory.setPort(2121);

            //替换默认监听
            serverFactory.addListener("default", factory.createListener());
            serverFactory.addListener("synclite", new Listener() {
                @Override
                public void start(FtpServerContext ftpServerContext) {
                    ZLog.i(TAG, "Listener:start");
                }

                @Override
                public void stop() {
                    ZLog.i(TAG, "Listener:stop");
                }

                @Override
                public boolean isStopped() {
                    return false;
                }

                @Override
                public void suspend() {
                    ZLog.i(TAG, "Listener:suspend");
                }

                @Override
                public void resume() {
                    ZLog.i(TAG, "Listener:resume");
                }

                @Override
                public boolean isSuspended() {
                    return false;
                }

                @Override
                public Set<FtpIoSession> getActiveSessions() {
                    return null;
                }

                @Override
                public boolean isImplicitSsl() {
                    return false;
                }

                @Override
                public SslConfiguration getSslConfiguration() {
                    return null;
                }

                @Override
                public int getPort() {
                    return 0;
                }

                @Override
                public String getServerAddress() {
                    return null;
                }

                @Override
                public DataConnectionConfiguration getDataConnectionConfiguration() {
                    return null;
                }

                @Override
                public int getIdleTimeout() {
                    return 0;
                }

                @Override
                public List<InetAddress> getBlockedAddresses() {
                    return null;
                }

                @Override
                public List<Subnet> getBlockedSubnets() {
                    return null;
                }

                @Override
                public SessionFilter getSessionFilter() {
                    return null;
                }
            });

            //用户名
            BaseUser user = new BaseUser();
            user.setName("admin");
            //密码 如果不设置密码就是匿名用户
            user.setPassword("123456");
            //用户主目录
            user.setHomeDirectory("E:\\hobby");

            List<Authority> authorities = new ArrayList<>();
            //增加写权限
            authorities.add(new WritePermission());
            user.setAuthorities(authorities);

            //增加该用户
            serverFactory.getUserManager().save(user);

            /**
             * 也可以使用配置文件来管理用户
             */
//      PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
//      userManagerFactory.setFile(new File("users.properties"));
//      serverFactory.setUserManager(userManagerFactory.createUserManager());

            FtpServer server = serverFactory.createServer();
            server.start();
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }
}
