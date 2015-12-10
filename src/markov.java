import java.io.BufferedReader; 
import java.io.FileReader; 
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.ArrayList; 

public class markov {
	
	public final String FILE = "yaks.txt";
	public HashMap<ArrayList<String>, ArrayList<String>> states = new HashMap<ArrayList<String>, ArrayList<String>>();
	public HashMap<String, String> yo = new HashMap<String, String>(); 
	public ArrayList<ArrayList<String>> firstPairs = new ArrayList<ArrayList<String>>(); 

	public void readFile() {
		String line = null; 
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILE));
			while((line = br.readLine())!= null) {
				parseLine(line); 
			}
			br.close(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parseLine(String s) {

		String[] words = s.split(" ", 0);
		for(int i = 0; i < words.length-2; i++) {
			
			ArrayList<String> pair = new ArrayList<String>();
			pair.add(words[i]);
			pair.add(words[i+1]);
			
			if(i==0) {
				firstPairs.add(pair);
			}
			
			
		//	System.out.println(words[i] + " " + words[i+1]);
			if(states.containsKey(pair)) {
				ArrayList<String> al = states.get(pair);
				al.add(words[i+2]);
				states.put(pair, al);
			}else if(i+2>=words.length) {
				states.put(pair, new ArrayList<String>());
			}
			else {
				ArrayList<String> al = new ArrayList<String>(); 
				al.add(words[i+2]);
				states.put(pair, al);
			}
		}
		
		/*for (ArrayList<String> pairs: states.keySet()){

			System.out.println(pairs.toString() + ": " + states.get(pairs).toString()); 
		} */
		
	}
	
	public void generateString() {
		
		/*for (ArrayList<String> pairs: states.keySet()){

		System.out.println(pairs.toString() + ": " + states.get(pairs).toString()); 
	} */
		
		Random rand = new Random(); 
	  //  ArrayList<ArrayList<String>> keys = new ArrayList<ArrayList<String>>(keySet());
	    ArrayList<String> startPair = firstPairs.get(rand.nextInt(firstPairs.size()));
	    StringBuilder sb = new StringBuilder(); 
	    sb.append(startPair.get(0) + " ");	    
	    boolean last = true; 
	    
	    while(states.containsKey(startPair) && sb.length() < 200) {
	    	
	    //	System.out.println(startPair.toString());
	    	
	    	sb.append(startPair.get(1) + " ");
	    	ArrayList<String> followingWords = states.get(startPair);

	      	String second = startPair.get(1);
	    	startPair = new ArrayList<String>();
	    	startPair.add(second);
	    	
	    	if(followingWords.size()>0){
	    		String follow = ""; 
	    		int num = rand.nextInt(followingWords.size());
	    		follow = followingWords.get(num);
	    	/*	while(follow.equals(second)) {
	    			num = rand.nextInt(followingWords.size());
		    		follow = followingWords.get(num);
	    		}*/
	    		startPair.add(follow);
	    	} else {
	    		last = false; 
	    		break;
	    	}
	   // 	System.out.println(startPair.toString());
	    }
	    if(last || !states.containsKey(startPair)) {
	    	sb.append(startPair.get(1));
	    }

    	System.out.print(sb.toString());
	}
	
	public static void main(String[] args) {
		markov m = new markov(); 
		m.readFile();
		//m.parseLine("Hello it's me. I've been wondering if after all these years you'd like to meet.");
		//System.out.println("parsed.");
		m.generateString(); 
	}
	 
}