package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import squiggle.SquigglePainter;
import trie.Trie;
import trie.TrieNode;

public class Main {
	
	private Trie trie;
	private JTextArea textArea;
	private SquigglePainter underline;
	
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		trie = new Trie();
		long start = System.currentTimeMillis();
		
		try(BufferedReader br = new BufferedReader(new FileReader("res/words_alpha.txt"))) {
			String line;
			int wordCount = 0;
			TrieNode lastNode = trie.getBase();
			while((line = br.readLine()) != null) {
				char[] chars = line.toCharArray();
				int i = 0;
				for(char c : chars) {
					// (i + 1) will give us the 1 based index of the current char
					// if this char is the same as the string length, it is the last char and a word
					boolean last = ((i + 1) == line.length());
					HashMap<Character, TrieNode> children = lastNode.getChildren();
					if(!children.containsKey(c)) {
						TrieNode n = lastNode.addChild(c, last);
						lastNode = last ? trie.getBase() : n;
					} else {
						lastNode = last ? trie.getBase() : children.get(c);
					}
					i++;
				}
				wordCount++;
			}
			System.out.println(String.format("Finished indexing %s words in %sms", wordCount, (System.currentTimeMillis() - start)));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame();
		frame.setSize(200, 300);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textArea = new JTextArea();
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				String[] words = textArea.getText().split(" ");
				if(!isWord(words[words.length - 1])) {
					try {
						int length = textArea.getText().length();
						textArea.getHighlighter().addHighlight(length - words[words.length - 1].length(), length, underline);
					} catch(BadLocationException e1) {
						e1.printStackTrace();
					}
				} else {
					
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				String[] words = textArea.getText().split(" ");
				if(!isWord(words[words.length - 1])) {
					try {
						int length = textArea.getText().length();
						textArea.getHighlighter().addHighlight(length - words[words.length - 1].length(), length, underline);
					} catch(BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		underline = new SquigglePainter(Color.RED);
		frame.add(textArea);
		
		frame.setVisible(true);
	}
	
	private boolean isWord(String word) {
		char[] chars = word.toCharArray();
		TrieNode lastNode = trie.getBase();
		int i = 0;
		for(char c : chars) {
			HashMap<Character, TrieNode> children = lastNode.getChildren();
			if(children.containsKey(c)) {
				TrieNode current = children.get(c);
				if(i + 1 == word.length()) {
					// this is the last char
					return current.isWord();
				} else {
					// not last char but part of word(s)
					lastNode = current;
				}
			} else {
				// current char is not part of any words
				return false;
			}
			
			i++;
		}
		return false;
	}
	
}
