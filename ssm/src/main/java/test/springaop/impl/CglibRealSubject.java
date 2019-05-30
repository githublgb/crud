package test.springaop.impl;


import test.springaop.interfance.Subject;

public class CglibRealSubject implements Subject {

    @Override
    public void rent() {

    }

    @Override
    public void hello(String str) {

        System.out.println("CGLIB动态代理：" + str);
    }

}
