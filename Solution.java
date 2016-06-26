public class Solution {
	public static void main(String[] args) {
		long n = 600851475143L;
        int d = 2;
        while(d<=n/d)
        {
             if (n%d==0)
                 n/=d;
            else
                d+=1;
        }
        System.out.println(n);
	}
}
