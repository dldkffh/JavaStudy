package ch4CheckTime;

public class PassS {
	   public static void main(String[] args){
	      PassS p = new PassS();
	      p.start();
	   }
	   void start(){
	      String s = "dream";
	      fix(s);
	      System.out.println(s);
	   }
	   void fix(String s){
	      s = s + "stream";
	      System.out.println(s);
	   }
	}
