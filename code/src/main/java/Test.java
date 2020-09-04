public class Test {

    public static void main(String[] args) {

        Integer i1 = 100;
        Integer i2 = 100;
        Integer i4 = Integer.valueOf(100);


        Integer i3 = Integer.valueOf(200);
        Integer i5 = Integer.valueOf(200);
        Integer i6 = 400;
        Integer i7 = 400;

        int n1 = 100;
        int n2 = 200;

        System.out.println("i1 == i2 "+ (i1 == i2));
        System.out.println("i1 == i4 "+ (i1 == i4));
        System.out.println("i3 == i5 "+ (i3 == i5));
        System.out.println("i6 == i7 "+ (i6 == i7));


        System.out.println("i1 == n1 "+ (i1 == n1));
        System.out.println("i3 == n2 "+ (i3 == n2));

    }

}
