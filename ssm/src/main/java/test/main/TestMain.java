package test.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TestMain {
    private final Logger LOGGER = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {


        Double a = 0.00000005D;
        Double b = 0.000000051D;
        System.out.println(a.toString());
        System.out.println(b.toString());

        System.out.println(a.toString().equals(b.toString()));

    }

}
