package video3;

public class callByValue {
  public static void main(String[] args){
    int x = 5;
    int y = 4;
    System.out.println("x : " + x + " y : "+y);
    addTen(x, y);
    System.out.println("x : " + x + " y : "+y);
  }
  
  static void addTen(int x , int y){
    x = x + 10;
    y = y + 10;
  }

}
