package hanlp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class test {

	public static void main(String args[]) {
//		Similarity s1 = new Similarity("����","������");
//		System.out.println(s1.sim());
		double x = 0;
		String y = " ";
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\lenovo\\eclipse-workspace\\hanlp\\src\\hanlp\\node.txt"));
			String line = null;
			while((line = reader.readLine()) != null) {
				Similarity s1 = new Similarity(line,"����");
				if(s1.sim() > x) {
					x = s1.sim();
					y = line;
				}
			}
			System.out.println(x + " " + y);
		}catch(IOException e) {
			System.out.println("�ļ���ȡ����");
		}
	}
}
