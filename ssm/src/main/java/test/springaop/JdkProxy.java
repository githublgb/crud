package test.springaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy implements InvocationHandler {

    //这个就是我们要代理的真实对象
    private Object target;

    /**
     * 绑定真实对象并且返回代理对象
     * @param target
     * @return
     */
    public Object bind(Object target){
        this.target = target;

        //通过反射机制，创建一个代理类对象实例并返回。用户进行方法调用时使用
        //创建代理对象时，需要传递该业务类的类加载器（用来获取业务实现类的元数据，在包装方法是调用真正的业务方法）、接口、handler实现类
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);

    }

    //代理对象调用方法时。最终为调用子代理类的方法， 真实对象反射调用方法，method.invoke(subject, args);
    @Override
    public Object invoke(Object object, Method method, Object[] args)  throws Throwable {

        //　在代理真实对象前我们可以添加一些自己的操作
        System.out.println("before rent house");

        System.out.println("Method:" + method);

        // 当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(target, args);

        //　　在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("after rent house");

        return null;
    }
}
