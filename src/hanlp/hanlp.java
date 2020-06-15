package hanlp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class hanlp {
	
	public static void arrangementQueryGraph() {
		ArrayList<String> nodes = new ArrayList<>();
		ArrayList<String> nodes1 = new ArrayList<>();
		ArrayList<String> relations = new ArrayList<String>();
		ArrayList<OneNode> queryNodes = new ArrayList<OneNode>();
		ArrayList<OneNode> queryNodes1 = new ArrayList<OneNode>();
		ArrayList<OneRelationship> queryRelations = new ArrayList<OneRelationship>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\lenovo\\eclipse-workspace\\hanlp\\src\\hanlp\\relationShip.txt"));
			String line = null;
			while((line = reader.readLine()) != null) {
				relations.add(line);
			}
		}catch(IOException e) {
			System.out.println("文件读取出错。");
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\lenovo\\eclipse-workspace\\hanlp\\src\\hanlp\\node.txt"));
			String line = null;
			while((line = reader.readLine()) != null) {
				nodes1.add(line);
			}
		}catch(IOException e) {
			System.out.println("文件读取出错。");
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\lenovo\\eclipse-workspace\\hanlp\\src\\hanlp\\graph.txt"));
			String line = null;
			while((line = reader.readLine()) != null) {
				if(line.startsWith("v")) {
					String[] lineSplit = line.split(" ");
					OneNode node1 = new OneNode();
					node1.setN(Long.parseLong(lineSplit[1]));
					node1.setName(lineSplit[2]);
					queryNodes1.add(node1);
					nodes.add(lineSplit[2]);
				}else if(line.startsWith("e")) {
					String[] lineSpilt = line.split(" ");
					int x = 0;
					for(int i = 0 ; i < queryRelations.size() ; i ++) {
						if(queryRelations.get(i).getN1()==Long.parseLong(lineSpilt[1]) && queryRelations.get(i).getN2()==Long.parseLong(lineSpilt[2]) && queryRelations.get(i).getLabel().equals(lineSpilt[3])) {
							x = 1;
						}
					}
					if(x == 0) {
						OneRelationship relationship1 = new OneRelationship();
						relationship1.setN1(Long.parseLong(lineSpilt[1]));
						relationship1.setLabel(lineSpilt[3]);
						relationship1.setN2(Long.parseLong(lineSpilt[2]));
						queryRelations.add(relationship1);
					}
				}
			}
		}catch(IOException e) {
			System.out.println("文件读取出错。");
		}
		
		//整理查询图的边
		for(int i = 0 ; i < queryRelations.size() ; i ++) {	
			double x = -1;
			int count = 0;
			for(int j = 0 ; j < relations.size() ; j ++) {
				Similarity s1 = new Similarity(queryRelations.get(i).getLabel(),relations.get(j));
				double y = s1.sim();
				if(y > x) {
					x = y;
					count = j;
				}
			}
			if (x > 0.6) {
				queryRelations.get(i).setLabel(relations.get(count));
			}else {
				queryRelations.remove(i);
			}
		}
		
		//整理查询图的节点
		for(int i = 0; i < queryRelations.size() ; i ++) {
			int x = 0 ;
			int y = 0 ;
			for(int j = 0 ; j < queryNodes.size() ; j ++) {
				if(queryNodes.get(j).getN()==queryRelations.get(i).getN1()) {
					x = 1;
				}
				if(queryNodes.get(j).getN()==queryRelations.get(i).getN2()) {
					y = 1;
				}
			}
			if(x==0) {
				OneNode node1 = new OneNode();
				node1.setN(queryRelations.get(i).getN1());
				node1.setName(nodes.get(Integer.parseInt(String.valueOf(queryRelations.get(i).getN1()))));
				queryNodes.add(node1);
			}
			if(y==0) {
				OneNode node1 = new OneNode();
				node1.setN(queryRelations.get(i).getN2());
				node1.setName(nodes.get(Integer.parseInt(String.valueOf(queryRelations.get(i).getN2()))));
				queryNodes.add(node1);
			}
		}
//		for(int i = 0 ; i < queryNodes.size() ; i ++) {
//			System.out.println(queryNodes.get(i).getN() + " " + queryNodes.get(i).getName());
//		}
		//整理查询图的顶点
//		for(int i = 0 ; i < queryNodes.size() ; i ++) {
//			String name = queryNodes.get(i).getName();
//			int count1 = 0;
//			int count2 = 0;
//			for(int j = 0 ; j < nodes.size() ; j ++) {
//				if(name.equals(nodes.get(j))) {
//					count1 = 1;
//				}
//			}
//			for(int j = 0 ; j < relations.size() ; j ++) {
//				Similarity s1 = new Similarity(queryNodes.get(i).getName(),relations.get(j));
//				double y = s1.sim();
//				if(y > 0.5) {
//					System.out.println(y);
//					count2 = 1;
//				}
//			}
//			if(count1 == 0) {
//				queryNodes.get(i).setName("-1");
//			}
//			if(count1 == 0 && count2 == 0) {
//				queryNodes.remove(i);
//			}
//		}
		//借助另一个顶点数组为顶点排序
		for(int i = 0 ; i < queryNodes1.size() ; i ++) {
			int x = 0;
			for(int j = 0 ; j < queryNodes.size() ; j ++) {
				if(queryNodes.get(j).getN() == queryNodes1.get(i).getN() && queryNodes.get(j).getName().equals(queryNodes1.get(i).getName())) {
					x = 1;
					break;
				}
			}
			if(x == 0) {
				queryNodes1.remove(i);
			}
		}

		//判断节点是否存在
		for(int i = 0 ; i < queryNodes1.size() ; i ++) {
			double x = 0;
			double y = 0;
			for(int j = 0 ; j < nodes1.size() ; j ++) {
				Similarity s1 = new Similarity(queryNodes1.get(i).getName(),nodes1.get(j));
				y = s1.sim();
				if(y > x) {
					x = y;
				}
			}
			if(x < 1) {
				queryNodes1.get(i).setName("-1");
			}
		}
		
		
		for(int i = 0 ; i < queryNodes1.size() ; i ++) {
			if (queryNodes1.get(i).getN() != i) {
				for(int j = 0 ; j < queryRelations.size() ; j ++) {
					if(queryRelations.get(j).getN1() == queryNodes1.get(i).getN()) {
						queryRelations.get(j).setN1(i);
					}
					if(queryRelations.get(j).getN2() == queryNodes1.get(i).getN()) {
						queryRelations.get(j).setN2(i);
					}
				}
				queryNodes1.get(i).setN(i);
			}
		}

		//将整理好的节点和边组合成查询图
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\lenovo\\eclipse-workspace\\hanlp\\src\\hanlp\\newGraph.txt"));
			out.write("t # 0" + "\n");
			for (int i = 0 ; i < queryNodes1.size(); i ++) {
				out.write("v " + queryNodes1.get(i).getN() + " " + queryNodes1.get(i).getName() + "\n");
			}
			for (int i = 0 ; i < queryRelations.size() ; i ++) {
				out.write("e " + queryRelations.get(i).getN1() + " " + queryRelations.get(i).getN2() + " " + queryRelations.get(i).getLabel() + "\n");
			}
			out.write("t # -1" + "\n");
			out.close();
		}catch(IOException e) {
			
		}
		
		
	}
		
}
