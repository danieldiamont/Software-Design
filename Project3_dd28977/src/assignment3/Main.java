/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Daniel Diamont>
 * <d28977>
 * <15455>
 * Slip days used: <0>
 * Git URL:
 * Spring 2018
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
		
		// TODO methods to read in words, output ladder
		//prompt user
		//call parse
		//run DFS or BFS (whichever)
		//return ladder
		boolean endProgram = false;
		
		while(!endProgram){
			ArrayList<String> words = parse(kb);
			
			if(words.size() == 2)
			{
				ArrayList<String> bfs_ladder = getWordLadderBFS(words.get(0),words.get(1));
				printLadder(bfs_ladder);
			}
			else
			{
				endProgram = true;
			}
		}
		
		return;
		
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		
		//TODO more code
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		
		String input = new String(keyboard.nextLine());
		
		//replace newlines and tabs
		input = input.replace("\n"," ");
		input = input.replace("\t", " ");
		
		input.toUpperCase(); //change to uppercase
		
		//trim
		String [] in = input.trim().split("\\s+"); //regex
		
		ArrayList<String> list = new ArrayList<String>();
		
		if(in.length == 1){ //if length is only 1, just quit
			return list;
		}
	
		
		String str1 = new String(in[0]);
		String str2 = new String(in[1]);
		
		str1 = str1.trim();//get rid of trailing and leading whitespace
		str2 = str2.trim();
		
		if(str1.equalsIgnoreCase("/quit") || str2.equalsIgnoreCase("/quit")){
			return list; //if either of the words is quit, then quit
		}
		
		list.add(str1);
		list.add(str2);
				
		return list;
	}
	
	/**
	 * This enumeration functions to color code the nodes in the BFS graph to indicate whether
	 * a node has not been explored, is currently being explored, or has been explored.
	 * @author Daniel Diamont
	 *
	 */
	public enum Color {white,gray,black};
	
	/**
	 * This function is called by the main program to get a word ladder between two words. The 
	 * underlying mechanism is a greedy modification to the recursive DFS algorithm.
	 * 
	 * getWordLadderDFS makes a Set of Strings from all the words in the given file. It creates
	 * a Hashtable to represent the words in a graph format, where the edges indicate two words
	 * that differ by a single letter. The greed in the DFS algorithm comes into play when choosing
	 * the nodes to explore. We can greedily order in which we want to explore the children of a node
	 * based on how many letters each child shares in common with the destination we want to reach in
	 * the word ladder. In this manner, we can decrease the length of the path that DFS has to take to
	 * reach word 'end' from word 'start'.
	 * 
	 * Since the DFS algorithm is recursive, and the default thread given by the JVM does not have a
	 * stack length sufficient to handle the recursion, we create a new thread with a larger stack-size,
	 * and run the algorithm in that new thread. We wait for the thread to finish its comuputation,
	 * and we return the result.
	 * 
	 * 
	 * @param start is the word at the start of our ladder (root node)
	 * @param end is the end of the ladder
	 * @return ladder in the form of an ArrayList
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		Set<String> dict = makeDictionary(); //parse file
		
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		Hashtable<String,HashSet<String>> graph = makeGraph(dict); //create graph
		
		DFS_Runnable DFS = new DFS_Runnable(graph, start, end); //create Runnable
		Thread t = new Thread(null, DFS, "dfs_thread", 1<<26); //create new thread with the DFS runnable
		t.start();
		try {
			t.join(); //join the thread so the main program can 'wait' for the thread to finish executing
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> ladder = DFS.getLadder(); //get the results of the thread's computation
		
		
		return ladder;
	}
	
	/**
	 * This function is called by the main program to get a word ladder between two words. The 
	 * underlying mechanism is breadth first search. BFS already calculates the shortest path
	 * between two nodes in a connected graph.
	 * 
	 * @param start is the word at the beginning of our ladder
	 * @param end is the word at the end of our ladder
	 * @return ladder is the ArrayList containing the shortest ladder of nodes between start and end
	 */
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		Set<String> dict = makeDictionary();//create dictionary
		
		start = start.toUpperCase();
		end = end.toUpperCase();
		
		Hashtable<String,HashSet<String>> graph = makeGraph(dict);//create graph
		
		//build discovered hash table
		Hashtable<String,Color> discovered = new Hashtable<String,Color>(dict.size()*2);
		
		//build ancestor hash table
		Hashtable<String,String> ancestor = new Hashtable<String,String>(dict.size()*2);
		
		//set ancestor hash table to null at first
		for(String s : dict){
			
			discovered.put(s, Color.white);
			ancestor.put(s, "");
		}
		
		//set layer counter and build Queue
		int layer = 0;
		Queue<String> Q = new LinkedList<String>();
		
		//build layer arrayList and initialize L[0] to the first node 'start'
		ArrayList<ArrayList<String>> L = new ArrayList<ArrayList<String>>();
		ArrayList<String> layer0 = new ArrayList<String>();
		layer0.add(start);
		L.add(layer0);
		
		discovered.put(start, Color.gray);
		//put 'start' in the Q
		Q.add(start);
		
		//BFS loop
		while(!Q.isEmpty()){
			
			String u = Q.remove(); //remove node from Queue
			
			//initialize array for next level
			ArrayList<String> layerNodes = new ArrayList<String>();
			
			//for all nodes incident to the current node u
			for(String v: graph.get(u)){
				
				//if v has not been discovered
				if(discovered.get(v).equals(Color.white)){
					
					//paint v as gray
					discovered.put(v, Color.gray);
					//add u as an ancestor of v
					ancestor.put(v, u);
					//add node to current layer
					layerNodes.add(v);
					//add v to the Queue to explore later
					Q.add(v);
				}
			}
			
			//we explored all nodes incident to current node u -- mark as explored
			discovered.put(u, Color.black);
			
			//add nodes in layer + 1 to L
			L.add(layer+1,layerNodes);
			
			//increase layer counter
			layer++;
			
		}
		
		//check that we found node 'end'
		if(discovered.get(end).equals(Color.black)){
			
			ArrayList<String> ladder = new ArrayList<String>();
			
			String index = end;
			
			ladder.add(end);
			
			//add nodes to ladder
			do {
				String rung = new String(ancestor.get(index));
				ladder.add(rung);
				index = ancestor.get(index);				
			}
			while(!ancestor.get(index).equalsIgnoreCase(""));
			
			Collections.reverse(ladder);
			
			return ladder;
		}		
		else{ //we found no ladders between 'start' and 'end' token
			ArrayList<String> ladder = new ArrayList<String>();
			ladder.add(start);
			ladder.add(end);
			
			return ladder;
		}
	}
    
    /**
     * This function takes a dictionary of words and creates a Hashtable of hashsets,
     * where the keys of the Hashtable are all the words in the dictionary, and the
     * each value is a hashset that contains all of the words that differ from the index
     * by one letter.
     * 
     *  The amortized time complexity of this function is O(n+m) where n is the size of
     * the dictionary, m is the total number of edges in the graph.
     * 
     * @param dictionary of words
     * @return graph : a Hashtable of hashsets that contains the words arranged in graph form
     */
    public static Hashtable<String,HashSet<String>> makeGraph(Set<String> dictionary){
    	
    	
    	//create Hashtable of HashSets
    	Hashtable<String,HashSet<String>> graph = new Hashtable<String,HashSet<String>>(dictionary.size()*2);
		
		
		for(String u : dictionary){ //for each word in the dictionary
			
			//create a new HashSet to store all edges for each node
			HashSet<String> edges = new HashSet<String>();
			
			for (String v : dictionary){ //iterate through all the other words in the dictionary
				
				if(!u.equalsIgnoreCase(v)){ //if node and edge are not the same word...
					
					int count = 0;
					int len = u.length();
					
					//count the number of characters that are equal between node u and node v
					for(int i = 0; i < len; i++)
					{
						if(u.charAt(i) == v.charAt(i)){
							count++;
						}
					}
					
					//if u and v only differ by one letter, (u,v) is an edge
					if(count == len -1){
						
						edges.add(v); //add v to the set of edges for u
					}
					
				}
			}
			
			graph.put(u, edges); //store the node with its list of edges into the graph
		}
		
		return graph; //return the graph to the caller
    }
    
	/**
	 * This method prints out the ladder if found.
	 * If size of ladder is greater than 2, print the ladder. Else,
	 * print that no ladder exists.
	 * @param ladder
	 */
	public static void printLadder(ArrayList<String> ladder) {
		
		if(ladder.size() > 2){
			System.out.println("A " + (ladder.size()-2) + "-rung word ladder exists between " 
					+ ladder.get(0).toLowerCase() + " and " + ladder.get(ladder.size()-1).toLowerCase() + ".\n");
			
			for(int i = 0; i < ladder.size(); i++){
				System.out.println(ladder.get(i));
			}
		}
		else{
			System.out.println("No word ladder can be found between " + ladder.get(0).toLowerCase() 
					+ " and " + ladder.get(1).toLowerCase() + ".");
		}
		
		
	}

	/* Do not modify makeDictionary */
	@SuppressWarnings("resource")
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
