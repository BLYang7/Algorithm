package com.algorithm;

public class KMP {
	
	private static int[] nextArray(char P[]){
	    int q,k;
	    int m = P.length;
	    int[] next = new int[m];
	    next[0] = 0;
	    for (q = 1,k = 0; q < m; ++q)
	    {
	        while(k > 0 && P[q] != P[k])
	            k = next[k-1];
	        if (P[q] == P[k])
	        {
	            k++;
	        }
	        next[q] = k;
	    }
		return next;
	}
	
	
	private static void kmp( char T[], char P[], int next[])
	{
	    int n,m;
	    int i,q;
	    n =  T.length;
	    m =  P.length;
	    next = nextArray(P);
	    for (i = 0,q = 0; i < n; ++i)
	    {
	        while(q > 0 && P[q] != T[i])
	            q = next[q-1];
	        if (P[q] == T[i])
	        {
	            q++;
	        }
	        if (q == m)
	        {
	            System.out.println("Pattern occurs with shift: " + (i-m+1) );
	        }
	    }    
	}
	
	
	public static void main(String[] args) {
		int[] next;
		char T[] = "ababxbababcadfdsss".toCharArray();
		char P[] = "abcdabd".toCharArray();
		next = nextArray(P);
		kmp(T,P,next);
	}

}

















