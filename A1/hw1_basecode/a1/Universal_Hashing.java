import java.io.*;
import java.util.*;

public class Universal_Hashing extends Open_Addressing{
	int a;
	int b;
	int p;

	protected Universal_Hashing(int w, int seed) {
		super(w, seed, -1);
		int temp = this.m+1; // m is even, so temp is odd here
		while(!isPrime(temp)) {
			temp += 2;
		}
		this.p = temp;
		a = generateRandom(0, p, seed);
		b = generateRandom(-1, p, seed);
	}
	
	/**
	 * Checks if the input int is prime
	 */
	public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i*i <= n; i++) {
        	if (n % i == 0) return false;
        }
        return true;
    }


	int uHash (int key) {
		return (((a*key+b)%p)%m);
	}
	/**
     * Implements universal hashing
     */
	@Override
    public int probe(int key, int i) {
    	//TODO: implement this function and change the return statement
		return ((uHash(key)+i)%power2(r));
    }



    /**
     * Inserts key k into hash table. Returns the number of collisions encountered,
     * and resizes the hash table if needed
     */
	@Override
    public int insertKeyResize(int key) {
		//TODO: implement this function and change the return statement
		int output = insertKey(key);
		double lf = (double)size/(double)m;
		if(lf>MAX_LOAD_FACTOR)
		{
			Open_Addressing noa = new Universal_Hashing((r*2)+1,seed);
			for(int j : Table){
				if (j >= 0) {
					if(j==key)
						output = noa.insertKey(j);
					else
						noa.insertKey(j);
				}
			}
			this.Table = noa.Table;
			this.r = noa.r;
			this.m = noa.m;
			this.w = noa.w;
			this.A = noa.A;
		}
		return output;
    }
}
