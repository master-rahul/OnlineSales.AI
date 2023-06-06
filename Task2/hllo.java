package random;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class task1{
    
/* 
 *  INPUT FORMAT:
 *  ---------------------------------------------------------------------------------------
 *  N  				: Number Of Possible Outcomes
 *  A B 			: N times {A,B} where A is the outcome and b is the percentage
 *	A B
 *  A B
 *  : :
 *  : :
 *	: :
 *	A B
 *	I 				: Number of Iterations or Number of Time EVENT OCCURRED
 *  ---------------------------------------------------------------------------------------
 *  CONSTRAINTS :
 *  ---------------------------------------------------------------------------------------
 *  N : 0 < N <= 100
 *  A : 0 < A < 2^31 - 1
 *  B : 0 < B <= 100
 *  I : 1 < I <= 1000000
 *  ---------------------------------------------------------------------------------------
 *  SOLUTION APPROACH :
 *  ---------------------------------------------------------------------------------------
 *  We know that the percentages are integer and assuming all outcomes percentages adds
 *  upto 100, so we create an ARRAYLIST of size 100 via allocating outcomes randomly until 
 *  respective percentage count is 0. This helps us to create a random ArrayList and now we
 *  use random function to get an index from which we select an element from ArrayList and
 *  as per the value of ArrayList we increment the outputArray respectively.
 *  
 *  EXAMPLE : 
 *  INPUT :
 *  ****** 
 *  2   
 *  1 3
 *  2 7
 *  5
 *  *******
 *  NOW :
 *  N = 2
 *  A = [1, 2]
 *  B = [3, 7]
 *  I = 10000
 *  OUTPUT = [{1 : 0}, {2 : 0}]
 *  As per our solution we will create of 5 random ArrayList Like below : 
 *  
 *  1 2 1 1 2 2 2 2 2 2     -> {Three 1, Seven 2}   {Random index = 2} 		{OUTPUT : [{1 : 1}, {2 : 0}]}
 * 	1 2 2 2 2 1 2 1 2 2		-> {Three 1, Seven 2}   {Random index = 3} 		{OUTPUT : [{1 : 1}, {2 : 1}]}
 *  1 2 1 2 2 2 2 1 2 2		-> {Three 1, Seven 2}   {Random index = 1} 		{OUTPUT : [{1 : 1}, {2 : 2}]}
 *  1 1 2 2 2 2 2 2 2 1		-> {Three 1, Seven 2}   {Random index = 0} 		{OUTPUT : [{1 : 2}, {2 : 2}]}
 *  2 2 2 2 2 1 2 2 1 1		-> {Three 1, Seven 2}   {Random index = 7} 		{OUTPUT : [{1 : 2}, {2 : 3}]}
 *  : : : : : : : : : :     : : : : : : : : : : :   : : : :  : : : :  		  : : : :  : : : :  : : : : 
 *  : : : : : : : : : :     : : : : : : : : : : :   : : : :  : : : :  		  : : : :  : : : :  : : : : 
 *  : : : : : : : : : :     : : : : : : : : : : :   : : : :  : : : :  		  : : : :  : : : :  : : : : 
 *  2 2 2 2 2 1 2 2 1 1		-> {Three 1, Seven 2}   {Random index = 7} 		{OUTPUT : [{1 : 3048}, {2 : 6952}]}
 *  
 *  We made length of our ArrayList as 10 as the sum of percentages = 10;
 *  
 *  RESULT :
 *  ----------------------------
 *  KEYS : [1, 2]
 *  OUTCOMES : [3048, 6952]
 *  PERCENTAGES :[{ 1 : 30.48% }, { 2 : 69.52% }]
 *  ---------------------------------------------------------------------------------------
 *  
 *  
*/	
	

	
    public static void main(String args[]) throws IOException{
    	FileWriter writer = new FileWriter("output.txt");   // CREATES NEW FILE
        Scanner s = new Scanner(System.in);    // Scanner Input
        int N = s.nextInt();
        Map<Integer, Integer> map  = new HashMap<>();     // Map to store outcomes and their respective percentages
        Map<Integer, Integer> ans  = new HashMap<>();	  // Map to store outcomes and their respective indexes
        for(int i = 0; i < N; i++){
            int A = s.nextInt();						 // Outcome Input
            int B = s.nextInt();						 // Percentage Input
            map.put(A, B);						  		 // Adding {outcome, percentage} pair in map
            ans.put(A, i);							  	 // Adding {outcome, index} pair in map
        }	
        int I = s.nextInt();					  		 	//  Iteration Input
        int[] outcomes = new int[map.size()];				 // OUTCOME ARRAY
        int[] temp_outcomes = new int[map.size()];			// TEMP_OUTCOME ARRAY
        int[] percentages = new int[map.size()];			// PRECENTAGE ARRAY
        int[] temp_percentages = new int[map.size()];		// TEMP_PRECENTAGE ARRAY
        int[] outputCount = new int[map.size()];			// Contains Count of resulted outcome based on outcome array
        int percentageSum = 0;
        int i = 0;
        for(Map.Entry<Integer, Integer> item : map.entrySet()) {     // Populating outcomes and percentages array
        	outcomes[i] = item.getKey();
        	percentages[i] = item.getValue();
        	percentageSum += item.getValue();
        	i++;
        }
       
        System.arraycopy(outcomes, 0, temp_outcomes, 0, percentages.length); 		// Populating temp_outcomes 
        System.arraycopy(percentages, 0, temp_percentages, 0, percentages.length);	// Populating temp_percentages 
        
        for(i = 0; i < I; i++) {
        	ArrayList<Integer> combination = allSequences( temp_outcomes, temp_percentages, new ArrayList<Integer>(), writer, percentageSum);  // Sends a random ArrayList consisting of all outcomes based on their percentages given
     		int index = generate(percentageSum-1);
     		int element =  combination.get(index);
     		outputCount[ans.get(element)]++;
     		System.arraycopy(outcomes, 0, temp_outcomes, 0, percentages.length);	// Re-Populating temp_outcomes 
            System.arraycopy(percentages, 0, temp_percentages, 0, percentages.length); // Re-Populating temp_percentages 
        }
        String arrayKeys = Arrays.toString(outcomes);
        String arrayValues = Arrays.toString(percentages);
        String output = Arrays.toString(outputCount);
        writer.write("KEYS : " + arrayKeys+" "+" VALUES : " + arrayValues+"\n");
        writer.write("OUTCOMES : " +output);
		writer.close();
		// OUTPUT : 
		System.out.println("KEYS : "+arrayKeys);
		System.out.println("OUTCOMES : " +output);
		System.out.print("PERCENTAGES :");
		System.out.print("[");
		for(i = 0; i < map.size(); i++) System.out.print("{ "+outcomes[i] +" : "+String.format("%.2f",((outputCount[i] *1.0/I) * 100.0))+ (i == map.size()-1 ? "% }" : "% }, "));
		System.out.print("]");
		
    }
    
    public static ArrayList<Integer>  allSequences(int[] keys, int[] values, ArrayList<Integer> ans, FileWriter writer, int length)throws IOException {
    	if(ans.size() == length) {   // WHEN ARRAYLIST IS OF LENGTH 100 RETURN
     		writer.write(ans.toString()+"\n");   // WRITES ARRAYLIST IN FILE
     		writer.flush();
    		return ans;
    	}
    	int index = -1;
    	while(true) {
    		index = generate(keys.length-1);
    		if(values[index] > 0) break;
    	}
		ArrayList<Integer> temp = new ArrayList<Integer>(ans);
		temp.add(keys[index]);      // ADDS RANDOM OUTCOME BASED ON DECREMENTING PRECENTAGE
		values[index]--;
		return allSequences(keys, values, temp, writer, length);	
    }
    
    public static int generate(int m) {     // RANDOM FUCNTION
    	return (int)Math.floor(Math.random() * (m - 0 + 1) + 0);
    }
}
