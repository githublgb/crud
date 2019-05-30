package test.java.number;

import java.sql.SQLOutput;

public class num {
    public static void main(String[] args) {
          final double DOUBLE_PRECISION = 0.00000001;





        double d1 = 452.364559;
        double d2 =452.364559;
        boolean isTrue =Math.abs(d1 - d2) < DOUBLE_PRECISION;

        System.out.println(isTrue);

    }
}
