package hanlp;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
public class demo {
	static String s1;
	public demo(String s1) {
		this.s1 = s1;
	}
	public static void buildGraph() throws FileNotFoundException{
		Path graphFile = Paths.get("C:\\Users\\lenovo\\eclipse-workspace\\hanlp\\src\\hanlp", "graph.txt");
		PrintWriter writer = new PrintWriter(graphFile.toFile());
		//依存句法分析
		CoNLLSentence sentence = HanLP.parseDependency(s1);
		//System.out.println(sentence);
        //把名字，序号，词性存放在一个ArrayList中
        ArrayList<word> words = new ArrayList<word>();
        for (CoNLLWord x : sentence)
        { 
        	word newWord = new word(x.LEMMA,x.ID,x.POSTAG,x.HEAD.ID);
        	words.add(newWord);
        }
        int wordNum = 0;
        for(int i = 0 ; i < words.size() ; i ++) {
        	String cx = words.get(i).getCx();
        	if(cx.contains("n") || cx.contains("r")) {
        		wordNum = nounNode(words.get(i),writer,words,wordNum);
        	}else if(cx.contains("v")) {
        		wordNum = verbNode(words.get(i),writer,words,wordNum);
        	}
        }
        writer.flush();
	}
	public static int nounNode(word word,PrintWriter writer,ArrayList<word> words,int wordNum) {
		//npNode的子节点集合
		ArrayList<word> children = new ArrayList<>();
		for(int i = 0 ; i < words.size() ; i ++) {
			if(words.get(i).getHeadId() == word.getId()) {
				children.add(words.get(i));
			}
		}
		ArrayList<word> nounChild = new ArrayList<word>();
		for(int j = 0 ; j < children.size() ; j ++) {
			if(children.get(j).getCx().contains("n") || children.get(j).getCx().contains("r")) {
				nounChild.add(children.get(j));
			}
		}
		if(nounChild.size() == 0) {
			if(word.getName().equals("刘德华")) {
				if(!word.isInGraph()) {
					writer.write("v " + wordNum + " " + word.getName() + "\n");
					word.setInGraph(true);
					word.setGraphNum(wordNum);
					return ++wordNum;
				}
			}else {
				if(!word.isInGraph()) {
					writer.write("v " + wordNum + " " + word.getName() + "\n");
					word.setInGraph(true);
					word.setGraphNum(wordNum);
					return ++wordNum;
				}
			}
		}else{
			for(int i = 0 ; i < nounChild.size() ; i ++) {
				wordNum = nounNode(nounChild.get(i),writer,words,wordNum);
			}
			if(!word.isInGraph()) {
				writer.write("v " + wordNum + " " + word.getName() + "\n");
				word.setInGraph(true);
				word.setGraphNum(wordNum);
				wordNum ++;
			}
			for(int i = 0 ; i < nounChild.size() ; i ++) {
				if(word.getName().equals("刘德华")) {
					writer.write("e " + nounChild.get(i).getGraphNum() + " " + word.getGraphNum() + " -1" + "\n");
				}else {
					writer.write("e " + nounChild.get(i).getGraphNum() + " " + word.getGraphNum() + " " + word.getName() + "\n");
				}
			}
		}
		return wordNum;
	}
	
	public static int verbNode(word word,PrintWriter writer,ArrayList<word> words,int wordNum) {
		//vpNode的子节点集合
		ArrayList<word> children = new ArrayList<>();
		for(int i = 0 ; i < words.size() ; i ++) {
			if(words.get(i).getHeadId() == word.getId()) {
				children.add(words.get(i));
			}
		}
		for(int i = 0 ; i < children.size() ; i ++) {
			if(children.get(i).getCx().contains("n") || children.get(i).getCx().contains("r")) {
				wordNum = nounNode(children.get(i),writer,words,wordNum);
			}
		}
		ArrayList<word> nounChild = new ArrayList<word>();
		for(int j = 0 ; j < children.size() ; j ++) {
			if(children.get(j).getCx().contains("n") || children.get(j).getCx().contains("r")) {
				nounChild.add(children.get(j));
			}
		}
		if(nounChild.size() == 2) {
			writer.write("e " + nounChild.get(0).getGraphNum() + " " + nounChild.get(1).getGraphNum() + " " + word.getName() + "\n");
		}
		return wordNum;
	}

	
	/*
	 * public static void main(String args[]) throws FileNotFoundException {
	 * 
	 * }
	 */
	
}
