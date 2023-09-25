import java.io.*;
import java.util.*;

public class Open_Addressing {
	public static final double MAX_LOAD_FACTOR = 0.75;
	
	public int m; // number of slots
	public int A; // the default random number
	int w;
	int r;
	int seed;
	public int[] Table;
	int size; // number of elements stored in the hash table

	protected Open_Addressing(int w, int seed, int A) {
		this.seed = seed;
		this.w = w;
		this.r = (int) (w - 1) / 2 + 1;
		this.m = power2(r);
		if (A == -1) {
			this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
		} else {
			this.A = A;
		}
		this.Table = new int[m];
		for (int i = 0; i < m; i++) {
			Table[i] = -1;
		}
		this.size = 0;
	}

	/*
	 * Calculate 2^w
	 */
	public static int power2(int w) {
		return (int) Math.pow(2, w);
	}

	public static int generateRandom(int min, int max, int seed) {
		Random generator = new Random();
		if (seed >= 0) {
			generator.setSeed(seed);
		}
		int i = generator.nextInt(max - min - 1);
		return i + min + 1;
	}

	int chain (int key) {
		return (((A*key)%power2(w))>>(w-r));
	}

	/*
	 * Implements the hash function g(k)
	 */
	public int probe(int key, int i) {
		return ((chain(key)+i)%power2(r));
	}



	/*
	 * Inserts key k into hash table. Returns the number of collisions encountered
	 */
	public int insertKey(int key) {
		//TODO: implement this function and change the return statement.
		int cols = 0;
		while(Table[probe(key, cols)]>=0 && cols < m)
		{
			cols++;
		}
		if (cols<m) {
			Table[probe(key, cols)] = key;
			this.size = size+1;
		}
		return cols;
	}


	/*
	 * Sequentially inserts a list of keys into the HashTable. Outputs total number of collisions
	 */
	public int insertKeyArray(int[] keyArray) {
		int collision = 0;
		for (int key : keyArray) {
			collision += insertKey(key);
		}
		return collision;
	}

	/*
	 * @param the key k to be searched
	 * @return an int array containing 2 elements:
	 * first element = index of k in this.Table if the key is present, = -1 if not present
	 * second element = number of collisions occured during the search
	 */
	public int[] searchKey(int k) {
		//TODO: implement this function and change the return statement
		int cols = 0;
		while(Table[probe(k, cols)]!=k && cols < m)
		{
			cols++;
		}
		int index = -1;
		if(Table[probe(k, cols)]==k)
			index = probe(k, cols);
		int[] output = {index, cols};
		return output;
	}
	
	/**
	 * Removes key k from hash table. Returns the number of collisions encountered
	 */
	public int removeKey(int k){
		//TODO: implement this function and change the return statement.
		int[] searchResult = searchKey(k);
		if(Table[searchResult[0]] == k) {
			Table[searchResult[0]] = -2;
			size--;
		}
		return searchResult[1];
	}

	/*
	 * Inserts key k into hash table. Returns the number of collisions encountered,
	 * and resizes the hash table if needed
	 */
	public int insertKeyResize(int key) {
		//TODO: implement this function and change the return statement
		if((((double)size+1)/(double)m)>MAX_LOAD_FACTOR)
		{
			Open_Addressing noa = new Open_Addressing((r*2)+1,seed,-1);
			for(int j : Table){
				if (j >= 0) {
					noa.insertKey(j);
				}
			}
			this.Table = noa.Table;
			this.r = noa.r;
			this.m = noa.m;
			this.w = noa.w;
			this.A = noa.A;
		}
		int output = insertKey(key);
		return output;
	}

	/*
	 * Sequentially inserts a list of keys into the HashTable, and resize the hash table
	 * if needed. Outputs total number of collisions
	 */
	public int insertKeyArrayResize(int[] keyArray) {
		int collision = 0;
		for (int key : keyArray) {
			collision += insertKeyResize(key);
		}
		return collision;
	}

	/*
	 * @param the key k to be searched (and relocated if needed)
	 * @return an int array containing 2 elements:
	 * first element = index of k in this.Table (after the relocation) if the key is present, 
	 * 				 = -1 if not present
	 * second element = number of collisions occured during the search
	 */
	public int[] searchKeyOptimized(int k) {
		//TODO: implement this function and change the return statement
		int cols = 0;
		int moveIndex = -1;
		while(Table[probe(k, cols)]!=k && cols < m)
		{
			if(moveIndex == -1 && Table[probe(k, cols)]==-2)
				moveIndex = probe(k, cols);
			cols++;
		}
		int index = -1;
		if(Table[probe(k, cols)]==k) {
			index = probe(k, cols);
			if(moveIndex != -1){
				Table[index] =-2;
				Table[moveIndex] = k;
				index = moveIndex;
			}
		}
		int[] output = {index, cols};
		return output;
	}

	/**
	 * @return an int array of n keys that would collide with key k
	 */
	public int[] collidingKeys(int k, int n, int w) {
		//TODO: implement this function and change the return statement
		int[] output = new int[n];
		for(int i = 0 ; i<n ; i++){
			output[i] = k+i*power2(w);
			//System.out.println(k+i*power2(w)+" "+chain(output[i]));
		}
		return output;
	}
}
