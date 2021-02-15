class AA
 {
    public static void main(String[] args) 
    {
        int i;   //변수 i 지정
       for(i = 4; i < 10; i+=2){
                pprn(10 - i);    //공백을 출력하는 함수 호출
               pprns(i*2);    //*을 출력하는 함수 호출
               pprn((10 - i)*2); //하트의 갈라지는 부분 공백
               pprns(i*2);    //갈라진 이후에 연결되는 *출력
       System.out.println();     //한줄을 완성하면 줄 내림
       }
        /* 하트의 머리를 출력하는 For문 종료*/
        for(i = 20; i >= 0; i-=2){
                pprn(20 - i);    //공백 출력 함수 호출
               pprns(i*2);    //*을 출력하는 함수 호출
       System.out.println();     //한줄이 완성되면 줄내림
       }
        /* 하트의 아랫도리 출력*/
    }
    /*a로 받아들인 int만큼 공백을 출력하는 메소드*/
    static void pprn(int a){
        int j;
        for(j = 0; j < a; j++)
            System.out.print(" ");
    }
    /*a로 받아들인 int만큼 *을 출력하는 메소드*/
   static void pprns(int a){
        int j;
        for(j = 0; j <= a; j++)
            System.out.print("*");
    }
 }
