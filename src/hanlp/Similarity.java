package hanlp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Similarity {  
    Map<Character, int[]> vectorMap = new HashMap<Character, int[]>();    
    int[] tempArray = null;    
    public Similarity(String string1, String string2) {   
        for (Character character1 : string1.toCharArray()) { //���Ϊ����        	
            if (vectorMap.containsKey(character1)) {  //���ַ��Ƿ��Ѿ�����map��
                vectorMap.get(character1)[0]++;  //key��Ӧ��ֵ��һ�����飬��һ���ַ������ַ����ִ����洢�������һ��λ��
            } else {  
                tempArray = new int[2];  
                tempArray[0] = 1;   //����ַ�û�г��ֹ�������ӵ�map�У������һ��λ����Ϊ1
                tempArray[1] = 0;  
                vectorMap.put(character1, tempArray);  
            }  
        }  
        for (Character character2 : string2.toCharArray()) {  //���Ϊ����
        	//System.out.println(character2);
        	if (vectorMap.containsKey(character2)) {  
                vectorMap.get(character2)[1]++;  
            } else {  
                tempArray = new int[2];  
                tempArray[0] = 0;  
                tempArray[1] = 1;  //�ڶ����ַ������ַ����ִ����洢������ĵڶ���λ��
                vectorMap.put(character2, tempArray);  
            }  
        }
        /*
         * �����map���key��Ӧ�����飬��һ��λ�ô洢���ǵ�һ���ַ����ַ���Ӧ���������ڶ���λ�ô洢���ǵڶ����ַ����ַ���Ӧ��������
         */
    }    
    // �������ƶ�
    public double sim() {  //���ӷ�ĸ���
        double result = 0;  
        result = pointMulti(vectorMap) / sqrtMulti(vectorMap);  
        return result;  //���ҶȽ������,��Ϊ�����ң����ؽ��Խ�󣬼н�ԽС��������������Խ�ӽ����������ַ���Խ����
    }  
   //��ƽ����
    private double sqrtMulti(Map<Character, int[]> paramMap) {  
        double result = 0;  
        result = squares(paramMap);  //����ƽ����
        result = Math.sqrt(result);  //�ٿ����ţ�������ģ
        return result;  
    }    
    // ��ƽ����
    private double squares(Map<Character, int[]> paramMap) {  
        double result1 = 0;  
        double result2 = 0;    
        Set<Character> keySet = paramMap.keySet();  
        for (Character character : keySet) {  
            int temp[] = paramMap.get(character);  //��ȡkey��Ӧ��ֵ--����
            result1 += (temp[0] * temp[0]);  //temp[0]�洢���ǵ�һ���ַ�����Ӧ������
            result2 += (temp[1] * temp[1]);  //temp[1]�洢���ǵڶ����ַ�����Ӧ������
        }  
        return result1 * result2;  
    }    
    // ���
    private double pointMulti(Map<Character, int[]> paramMap) {  
        double result = 0;  
        Set<Character> keySet = paramMap.keySet();  //����map������keyֵ���б������set��Ҳ������list�����
        for (Character character : keySet) {  //�洢��keyֵ���ǲ��ظ���
            int temp[] = paramMap.get(character);  //��ȡkey��Ӧ��ֵ
            result += (temp[0] * temp[1]);  
        }  
        return result;  
    }    
}  