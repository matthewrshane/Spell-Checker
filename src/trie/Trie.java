package trie;

import java.util.HashMap;

public class Trie {
	
	private TrieNode base;
	
	public Trie() {
		base = new TrieNode("", false);
	}
	
	public HashMap<Character, TrieNode> getCharacters() {
		return base.getChildren();
	}
	
	public void addCharacter(Character c, boolean isWord) {
		base.addChild(c, isWord);
	}

	public TrieNode getBase() {
		return base;
	}
	
}
