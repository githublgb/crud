package test.java.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class javaTest {

    private static Boolean a = false;

    public static void main(String[] args) {

        System.out.println("李古波".startsWith("6"));
        List<Integer> a = new ArrayList<>();

        a.add(1);
        a.add(2);
        a.add(3);
        a.add(1);
        a.add(1);

        for (Iterator<Integer> iterator = a.iterator(); iterator.hasNext(); ) {
            Integer next = iterator.next();

            if (next == 1) {
                iterator.remove();
            }

        }
       // System.out.println(a.toString());


    }


    public static void booleanTest(boolean a) {

        a = true;
        System.out.println(a);
    }
}
