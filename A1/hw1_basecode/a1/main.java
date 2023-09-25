import java.io.*;
import java.util.*;

public class main {     


	public static void main(String[] args) {
		Chaining testChaining = new Chaining(4, 0, -1);
		Open_Addressing testOA = new Open_Addressing(4, 0, -1);

		int[] keyArray = {1,2,3,4};
		testOA.insertKeyArrayResize(keyArray);
		System.out.println(Arrays.toString(testOA.Table));
		testOA.insertKeyResize(5);
		testOA.insertKeyResize(33);
		testOA.removeKey(1);
		System.out.println("Printing prior to optimized search");
		System.out.println(Arrays.toString(testOA.Table));
		testOA.searchKeyOptimized(33);
		testOA.searchKeyOptimized(5);
		System.out.println("Printing post optimization");
		System.out.println(Arrays.toString(testOA.Table));
	}
}