public class autoboxingUnboxing{
    public static void main(String[] args) {

// autoboxing and unboxing while going through assignment operation
        int x = 10;
        Integer y = x; // autoboxing
//        Integer y = Integer.valueOf(x);

        System.out.println(x);
        System.out.println(y);

        Integer b = 12;
        int c = b; // unboxing
//        int c = b.intValue();
        System.out.println(b);
        System.out.println(c);

// autoboxing and unboxing while performing arithmatic operation
        Integer sum1 = 10;
        Integer sum2 = 20;
        int additin = sum1 + sum2;
        System.out.println(additin);

//        autoboxing and unboxing can happen while method calling
        Integer argument = 10;
        takeInt(argument);
    }

    static void takeInt(int age){
        System.out.println(age);
    }
}