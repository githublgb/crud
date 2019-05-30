package test.springaop;

import test.springaop.impl.CglibRealSubject;
import test.springaop.impl.JdkRealSubject;
import test.springaop.interfance.Subject;

public class main {
    public static void main(String[] args) {
        // 我们要代理的真实对象
        Subject subjectJdk = new JdkRealSubject();
        JdkProxy proxy = new JdkProxy();

        // 传入真实对象，返回一个代理对象
        Subject s=   (Subject)proxy.bind(subjectJdk);
        System.out.println(s.getClass().getName());
        s.hello("你好");

        //cglib的动态代理
        Subject subjectCglib = new CglibRealSubject();
         CglibProxy cglibProxy = new CglibProxy();
         //创建代理对象
        Subject c= (CglibRealSubject)  cglibProxy.getInstance(subjectCglib);
         System.out.println(c.getClass().getName());
         c.hello("是cglib动态代理吗？");


    }

}
