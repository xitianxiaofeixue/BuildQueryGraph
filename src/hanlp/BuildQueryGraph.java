package hanlp;

import java.io.FileNotFoundException;

public class BuildQueryGraph {

	public static void main(String args[]) throws FileNotFoundException {
		demo d1 = new demo("��������߳������ĸ�������");
		d1.buildGraph();
		hanlp h1 = new hanlp();
		h1.arrangementQueryGraph();
	}
}
