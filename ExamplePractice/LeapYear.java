
public class LeapYear
{
   public static void main(String args)
  {
     System.out.print("2300년은 윤년인가? " + "(O) 또는 (X)로 답하시오 ");
     // (divisible by 4 and not by 100) or (divisible by 400) //
    System.out.println( 2300 % 4 || 0 && 1800 % 100 !=0 || 1800 % 400 == 0);
  }
}