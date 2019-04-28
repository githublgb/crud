package test.springaop.impl;

import test.springaop.interfance.Subject;

public class JdkRealSubject implements Subject {
    @Override
    public void rent()
    {
        System.out.println("I want to rent my house");
    }

    @Override
    public void hello(String str)
    {
        System.out.println("hello: " + str);
    }
}
