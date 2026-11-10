package video4;

public class argsCode {
    public static void main(String[] args){
        System.out.println("number of args : "+args.length);
        for(int i = 0 ; i < args.length; i++){
            System.out.println("Argument : "+i+" : "+args[i]);
        }
    }
}
