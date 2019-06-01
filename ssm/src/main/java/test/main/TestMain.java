package test.main;

import com.lgb.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMain {
    private final Logger LOGGER = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {
        //list的toString 和数组的toString person重写了toString方法
       // testListToString();
        testNull();
    }

    public static void testListToString() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person());
        personList.add(new Person());

        Person[] p = new Person[2];
        p[0] =new Person();

        Person[] a = {new Person(), new Person()};

        System.out.println(personList.toString());
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(p));
    }


    public static void testNull(){
        Person p = new Person();

        System.out.println((p.getBri()==null));

    }

}
