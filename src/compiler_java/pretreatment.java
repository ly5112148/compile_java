package compiler_java;

import java.util.Scanner;
import java.io.*;

public class pretreatment {
	static boolean in_comment = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String buf = "";
		file_pretreatment("pretreatment.txt");
		System.out.println(buf);
	}
	//文件预处理，按行读入然后给预处理器处理
	public static String file_pretreatment(String fileName) {
		File file = new File(fileName);
        BufferedReader reader = null;
        String result = "";
        
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	result += line_pretreatment(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        
        return result+"#";
    }
	
	public static String text_pretreatment(String line){
		line = line_pretreatment(line);
		line += "#";
		return line;
	}
	
	
	//代码预处理
	private static String line_pretreatment(String line) {
		line = line.toLowerCase();//变小写
		//换行、tab变空格
		line = line.replaceAll("\r\n", "\n");
		line = line.replaceAll("\r", "\n");
		line = line.replaceAll("\n|\t", " ");
		//去除注释
		if(line.indexOf("//")!=-1){
			line = line.split("//")[0];
		}
		if(line.indexOf("/\\*")!=-1){
			if(line.indexOf("\\*/")==-1){//注释不在行内结束
				in_comment = true;
				line = line.split("/*")[0];
				/*456
				 * 789
				 */
			}
			else{//注释在行内结束
				/*12345*/
				line = line.split("/*")[0] + line.split("\\*/")[1];
			}
		}
		if(in_comment){
			if(line.indexOf("\\*/")==-1){//注释未结束
				line = "";
			}
			else{
				line = line.split("\\*/")[1];
			}
		}
		
		return line;
		
    }
}
