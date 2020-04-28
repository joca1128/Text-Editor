package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		TrieNode currentNode = root;
		String wordCopy = word.toLowerCase();
		if(!word.isEmpty() && word!=null && !this.isWord(wordCopy)) {
			for(int i = 0 ; i < word.length() ; i++) {
				if(currentNode.insert(wordCopy.charAt(i))!=null) {
					currentNode = currentNode.getChild(wordCopy.charAt(i));
				}
				else {
					currentNode = currentNode.getChild(wordCopy.charAt(i));
				}
			}
				
			currentNode.setEndsWord(true);
			size++;
			return true;
		}
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		TrieNode currentNode = root;
		String sLC = s.toLowerCase();
		if(!s.isEmpty() && s!= null) {
			for(int i = 0; i < s.length() ; i++) {
				if(currentNode.getChild(sLC.charAt(i))==null) {
					return false;
				}
				else 
					currentNode = currentNode.getChild(sLC.charAt(i));
			}
			if(currentNode.endsWord())
				return true;
			else return false;
		}
		return false;
	}
	
	private TrieNode findNode(String prefix) {
		TrieNode currentNode = root;
		String prefixLC = prefix.toLowerCase();
			for(int i = 0; i < prefix.length() ; i++) {
				if(currentNode.getChild(prefixLC.charAt(i))==null) {
					return null;
				}
				else 
					currentNode = currentNode.getChild(prefixLC.charAt(i));
			}
			return currentNode;
	}
	
	private TrieNode nextStem (TrieNode tn) {
		if(tn.endsWord()) {
			return tn;
		}
		for(Character ch : tn.getValidNextCharacters()) {
			TrieNode curr= tn.getChild(ch);
			if(curr.endsWord()) {
				return curr;
			}
			//else {
				//nextStem(curr);
			//}
		}
		return null;
	}
	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 List<String> list =new LinkedList<String>();
    	 int i = 0;
    	 TrieNode tn = findNode(prefix);
    	 if(tn == null)
    		 return list;
    	 Queue<TrieNode> queue = new LinkedList<TrieNode>();
    	 tn = this.nextStem(tn);
    	 queue.add(tn);
    	 while(!queue.isEmpty() && i<numCompletions) {
    		 TrieNode curr= queue.remove();
    		 if(curr.endsWord())
    			 list.add(curr.getText());
    		 if(curr!=null) {
    			 for(Character c : curr.getValidNextCharacters()) {
    				 queue.add(curr.getChild(c));
    			 }
    		 }
    		 i = list.size();
    	 }
    	 list.sort(null);
    	 list.sort(new Comparator<String>() {
    		 @Override
    		 public int compare(String o1, String o2) {
    			 return o1.length()-o2.length();
    		 }
    	 }) ;
//    	 for(String s : list)
//    		 System.out.println(s);
//    	 System.out.println("------");
         return list;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
	

	
 	public static void main(String [] args) {
 		AutoCompleteDictionaryTrie t = new AutoCompleteDictionaryTrie();
 		t.addWord("Hello");
 		t.addWord("ello");
 		t.addWord("hi");
 		t.addWord("Hellow");
 		t.printTree();
 	}
 	
}

