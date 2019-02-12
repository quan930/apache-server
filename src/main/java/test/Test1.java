package test;

import app.mrquan.apache.ApacheServer;
import app.mrquan.apache.HttpExchange;
import app.mrquan.apache.HttpHandler;
import app.mrquan.nginx.Callback;
import app.mrquan.nginx.NginxServer;
import app.mrquan.nginx.pojo.RequestDetails;

import java.io.IOException;

public class Test1 {
    public static void main(String[] args){
        ApacheServer apacheServer = ApacheServer.create(9999, "/","www/html",(httpExchange)-> {
            System.out.println("apache"+httpExchange.getRequestLine());
//                System.out.println(httpExchange.getRequestHeaders());
            if (httpExchange.getRequestEntityBody()!=null){
                System.out.println(new String(httpExchange.getRequestEntityBody()));
            }
//            System.out.println(new String(httpExchange.getRequestEntityBody()));
        });
        apacheServer.start();
        NginxServer nginxServer = NginxServer.create(8888, "/", "127.0.0.1", 9999, new Callback() {
            @Override
            public void callback(RequestDetails requestDetails) {
                System.out.println("=================================nginx:"+(Boolean.TRUE.equals(requestDetails.getCache())?"已缓存":"未缓存")+"\t"+requestDetails);
            }
        });
        nginxServer.start();
        System.out.println("begin~~~~");

    }
}