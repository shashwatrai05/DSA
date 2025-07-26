public class Palindrome {
    public static void main(String[] args){
        int a=1221;
        int b=0;
        int ori=a;
        while(a!=0){
            int temp=a%10;
            b=b*10+temp;
        }

        System.out.println(ori==b);
    }
    
}
