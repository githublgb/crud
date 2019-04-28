package test.test;

public class TestMain {

    public static final int NORMAL = 1; // 普通业务
    public static final int INTSERV = 2; // 综合业务

    public static void main(String[] args) {
        System.out.println( getName(1));


    }

    public static final String getName(Integer type) {

        String returnName = "";
        switch ( type) {
            case  NORMAL:
                returnName = "555555";
                break;
            case  INTSERV:
                returnName = "888888";
                break;
        }
        return returnName;
    }

}
