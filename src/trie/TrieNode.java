package trie;

import java.util.HashMap;

public class TrieNode {
	
	private HashMap<Character, TrieNode> children;
	private String content;
	private boolean isWord;
	
	public TrieNode(String content, boolean isWord) {
		this.children = new HashMap<Character, TrieNode>();
		this.content = content;
		this.isWord = isWord;
	}
	
	public TrieNode addChild(Character c, boolean isWord) {
		TrieNode n = new TrieNode(content + c, isWord);
		children.put(c, n);
		return n;
	}

	public HashMap<Character, TrieNode> getChildren() {
		return children;
	}

	public String getContent() {
		return content;
	}

	public boolean isWord() {
		return isWord;
	}
	
	
}
