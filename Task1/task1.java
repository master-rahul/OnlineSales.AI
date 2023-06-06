import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class task1{
    
	
    public static void main(String args[]) throws IOException{
    	FileWriter writer = new FileWriter("just1.txt");
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        Map<Integer, Integer> map  = new HashMap<>();
        Map<Integer, Integer> ans  = new HashMap<>();
        for(int i = 0; i < n; i++){
            int key = s.nextInt();
            int value = s.nextInt();
            map.put(key, value);
            ans.put(key, i);
        }
        int iteration = s.nextInt();
        int[] keys = new int[map.size()];
        int[] temp_keys = new int[map.size()];
        int[] values = new int[map.size()];
        int[] temp_values = new int[map.size()];
        int[] outputCount = new int[map.size()];
        int i = 0;
        for(Map.Entry<Integer, Integer> item : map.entrySet()) {
        	keys[i] = item.getKey();
        	values[i] = item.getValue();
        	i++;
        }
       
        System.arraycopy(keys, 0, temp_keys, 0, keys.length);
        System.arraycopy(values, 0, temp_values, 0, keys.length);
        
        for(i = 0; i < iteration; i++) {
        	ArrayList<Integer> combination = allSequences( temp_keys, temp_values, new ArrayList<Integer>(), writer);
     		int index = generate(99);
     		int element =  combination.get(index);
     		outputCount[ans.get(element)]++;
     		System.arraycopy(keys, 0, temp_keys, 0, keys.length);
            System.arraycopy(values, 0, temp_values, 0, keys.length);
        }
        String arrayKeys = Arrays.toString(keys);
        String arrayValues = Arrays.toString(keys);
        String output = Arrays.toString(outputCount);
        writer.write("KEYS : " + arrayKeys+" "+" VALUES : " + arrayValues+"\n");
        writer.write("OUTCOMES : " +output);
		writer.close();
		System.out.println("KEYS : "+arrayKeys);
		System.out.println("OUTCOMES : " +output);
		System.out.print("PERCENTAGES :");
		for(i = 0; i < map.size(); i++) System.out.print("["+String.format("%.2f",((outputCount[i] *1.0/iteration) * 100.0))+ (i == map.size()-1 ? "" : ","));
		System.out.print("]");
		
    }
    
    public static ArrayList<Integer>  allSequences(int[] keys, int[] values, ArrayList<Integer> ans, FileWriter writer)throws IOException {
    	if(ans.size() == 100) {
     		writer.write(ans.toString()+"\n");
     		writer.flush();
    		return ans;
    	}
    	int index = -1;
    	while(true) {
    		index = generate(keys.length-1);
    		if(values[index] > 0) break;
    	}
		
		ArrayList<Integer> temp = new ArrayList<Integer>(ans);
		temp.add(keys[index]);
		values[index]--;
		return allSequences(keys, values, temp, writer);	
    	
    }
    public static int generate(int m) {
    	return (int)Math.floor(Math.random() * (m - 0 + 1) + 0);
    }
}
