/* WORD LADDER DFS_Runnable.java
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 * This is a Runnable made so that the DFS algorithm can be executed in a thread with a larger
 * stack size in order to handle long word ladders.
 * @author Daniel Diamont
 *
 */
public class DFS_Runnable implements Runnable{
	
	//runnable has a hashtable, start word, end word, and a ladder ArrayList
	private Hashtable<String,HashSet<String>> graph;
	private String start;
	private String end;
	private ArrayList<String> ladder;
	
	/**
	 * This enumeration functions to color code the nodes in the BFS graph to indicate whether
	 * a node has not been explored, is currently being explored, or has been explored.
	 * @author Daniel Diamont
	 */
	public enum Color {white,gray,black};
	
	/**
	 * Constructor for the Runnable
	 * @param graph the graph passed in from the main program
	 * @param start the start word of the ladder passed from the main program
	 * @param end the end word passed from the main program
	 */
	public DFS_Runnable(Hashtable<String,HashSet<String>> graph, String start, String end){
		this.graph = graph;
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Run method for runnable. Will execute the modified, greedy DFS algorithm.
	 */
	public void run(){
		
		Set<String> keys = graph.keySet();
		
		//build discovered hash table
		Hashtable<String,Color> discovered = new Hashtable<String,Color>(keys.size()*2);
		
		//build ancestor hash table
		Hashtable<String,String> ancestor = new Hashtable<String,String>(keys.size()*2);
		
		//set ancestor hash table to null at first
		for(String s : keys){
			
			discovered.put(s, Color.white);
			ancestor.put(s, "");
			
		}
		
		//set root parameters
		discovered.put(start, Color.gray);
		
		//greed: find the edge that has the most amount of characters similar to end and start there
		ArrayList<String> nextNodes = DFS_Greed(graph,start,end);		
		
		//call recursive helper function on every edge incident to start
		for(int i = 0; i < nextNodes.size(); i++){
			if(discovered.get(nextNodes.get(i)) == Color.white){
				ancestor.put(nextNodes.get(i),start);
				//DFS(graph,discoverTime, discovered, finished, ancestor, v, end, time);
				DFS(graph, discovered, ancestor, nextNodes.get(i), end);
			}
		}
		
		//check that we found node 'end'
		if(discovered.get(end).equals(Color.black)){
			ArrayList<String> ladder = new ArrayList<String>();
			
			String index = end;
			
			ladder.add(end);
			
			//fill up ladder with the path found
			do {
				String rung = new String(ancestor.get(index));
				ladder.add(rung);
				index = ancestor.get(index);				
			}
			while(!ancestor.get(index).equalsIgnoreCase(""));
			
			Collections.reverse(ladder); //sort ladder to display results from beginning to end
			
			this.ladder = ladder;
			
		}		
		else{ //we found no ladders between 'start' and 'end' token
			ArrayList<String> ladder = new ArrayList<String>();
			ladder.add(start);
			ladder.add(end);
			
			this.ladder = ladder;
		}

	}
	
	/**
	 * This is the recursive DFS call.
	 * @param graph passed from the main thread
	 * @param discovered passed from the main thread
	 * @param ancestor passed from the main thread
	 * @param u passed from the main thread
	 * @param end passed from the main thread
	 */
	public static void DFS(Hashtable<String,HashSet<String>> graph, Hashtable<String,Color> discovered,	Hashtable<String,String> ancestor, 
			String u, String end){

		discovered.put(u, Color.gray);
		
		ArrayList<String> nextNodes = DFS_Greed(graph,u,end);
		
		for(int i = 0; i < nextNodes.size(); i++){
			if(discovered.get(nextNodes.get(i)) == Color.white){
				ancestor.put(nextNodes.get(i),u);
				DFS(graph, discovered, ancestor, nextNodes.get(i), end);
			}
		}		
		
		//blacken u when finished
		discovered.put(u, Color.black);	
		
	}	
	
	/**
	 * This is the greedy algorithm for creating the order in which to visit a node's children.
	 * 
	 * Time complexity: O(m^2) where m is the number of nodes.
	 * @param graph passed from the main thread
	 * @param u passed from main thread
	 * @param end passed from main thread
	 * @return arrayList of children to visit in greedy order
	 */
	public static ArrayList<String> DFS_Greed(Hashtable<String,HashSet<String>> graph, String u, String end){
		//greed: find the edge that has the most amount of characters similar to end and start there
				Hashtable<String,Integer> nodes = new Hashtable<String,Integer>();
				
				for(String v : graph.get(u)){					
					int count = 0;
					int len = u.length();					
					//count the number of characters that are equal between node u and node v
					for(int i = 0; i < len; i++)
					{
						if(end.charAt(i) == v.charAt(i)){
							count++;
						}
					}					
					nodes.put(v, count);
				}
				
				//sort alternatives in preferred order
				Set<String> keys = nodes.keySet();
				
				ArrayList<Integer> sizes = new ArrayList<Integer>();
				
				for(String key : keys){
						sizes.add(nodes.get(key));
				}
				
				for(int i=0; i < sizes.size(); i++){
					
					for(int j = i+1; j < sizes.size(); j++){
						if(sizes.get(i) < sizes.get(j)){
							int temp = sizes.get(i);
							sizes.set(i, sizes.get(j));
							sizes.set(j, temp);
						}
					}
				}			
				
				ArrayList<String> nextNodes = new ArrayList<String>();
				
				for(int i = 0; i < sizes.size(); i++){
					for(String key : keys){
						if(nodes.get(key) == sizes.get(i)){
							nextNodes.add(key);
						}
					}
				}
				return nextNodes;
	}
	
	/**
	 * Getter method for the main thread to collect the results of the DFS algorithm
	 * @return ArrayList consisting of the word ladder between the specified start and end word.
	 */
	public ArrayList<String> getLadder(){
		return this.ladder;
	}

}
