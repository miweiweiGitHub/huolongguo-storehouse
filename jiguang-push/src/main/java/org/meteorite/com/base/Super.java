package org.meteorite.com.base;

/**
 * @author EX_052100260
 * @title: test
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-27 10:38
 */

class Sub extends Super{
    public static  int a;
    private    int b = 2,c=3;

    static{
        System.out.println("Sub static a= "+(a));
    }

    {
        System.out.println("Sub init ");
    }
    static  void  A(){
        System.out.println("Sub a= "+(a=20));
    }

    public   void  B(){
        System.out.println("Sub b= "+(b = 30));
    }

    public   void  C(){
        System.out.println("Sub c= "+(c = 50));
    }
}

public class Super {

    protected static  int a;
    protected   int b = 0;

    public Super(){
        a++;
    }

    static{
        System.out.println("Super static a= "+(a=1));
    }

    {
        System.out.println("Super init ");
    }
    static  void  A(){
        System.out.println("Super a= "+ (a++));
    }

    public   void  B(){
        System.out.println("Super b= "+(b = ++ a));
    }

    public static void main(String[] args) {
        Super s1 = new Super();
        System.out.println("Super s1.a = "+(s1.a));
        s1.A();
        s1.B();

        Sub s2 = new Sub();
        System.out.println("Super s2.a = "+(s2.a));
//        System.out.println("Super s2.a = "+(s2.b));
        s2.A();
        s2.B();
        s2.C();

        Sub s3 = new Sub();
        s2.C();
        Sub s4 = new Sub();
        s2.A();

    }

}


