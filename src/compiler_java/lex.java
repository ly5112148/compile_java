package compiler_java;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

//标识符:x 数字:u
//基本字cos:c sin:s max:a min:i true:t false:f
//括号 ( )
//运算符 + - * / = ^
//逻辑运算符 ||:| &&:& !
//三元运算符 ? :
//关系运算符 =:e !=:E <:l <=:L >:g >=:G
//结束符 #
//删除取值 []:d


//单行分词：do_lex_text 文件分词：do_lex_file


public class lex {
	static int index = 0;
	
	//scanner
	static String colchar = "ae0+-*/=^|&!?:<>.()#[]; ";				//DFA列字符
	//终态集
	static int[] end = {1 ,2 ,3 ,4 ,5 ,6 ,7 ,8 ,11,12,13,14,15,18,19,20,21,22,23,24,26,27,28,29,31,32};			
	//状态转移矩阵
	static int[][] M = {							
		//	 a  e  0  +  -  *  /  =  ^  |  &  !  ?  :  <  >  .  (  )  #  [  ]  ;
			{1 ,1 ,2 ,3 ,4 ,5 ,6 ,7 ,8 ,9 ,10,11,12,13,14,15,0 ,27,28,29,30,0 ,32},//0
			{1 ,1 ,1 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//1
			{0 ,17,2 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,16,0 ,0 ,0 ,0 ,0 ,0 },//2
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//3
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//4
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//5
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//6
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,18,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//7
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//8
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,19,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//9
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,20,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//10
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,21,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//11
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//12
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//13
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,22,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//14
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,23,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//15
			{0 ,0 ,24,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//16
			{0 ,0 ,26,25,25,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//17
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//18
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//19
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//20
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//21
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//22
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//23
			{0 ,17,24,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//24
			{0 ,0 ,26,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//25
			{0 ,0 ,26,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//26
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//27
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//28
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//29
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,31,0 },//30
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 },//31
			{0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 } //32
		};
	
	//search_table
	static String table[] = {
			"cos","sin","max","min","true","false",	//基本字csaitf
			"(",")",								//括号
			"+","-","*","/","=","^",				//运算符
			"||","&&","!",							//逻辑运算符
			"?",":",								//三元运算符
			"==","!=","<","<=",">",">=",			//关系运算符eElLgG
			"#",									//终止符
			"[]",									//删除取值
			";"										//一句结束
		};
		
	static String code = "csaitf()+-*/=^|&!?:eElLgG#d;";
	
	private static class code_val{
		private char code;
		private String val;
		
		public code_val(){
			code = '\0';
			val = "NUL";
		}
		public code_val(char ncode,String nval){
			code = ncode;
			val = nval;
		}
		
		public void setcode(char ncode){
			this.code = ncode;
		}
		public char getcode(){
			return this.code;
		}

		public void setval(String nval){
			this.val = nval;
		}
		public String getval(){
			return this.val;
		}
	}


	
	
	public static int col(char c,String code_char){
		//列定位
		if(c!='e' && c>='a' && c<='z') c = 'a';		//除了e外英文统一为a
		else if(c>='0' && c<='9') c = '0';			//数字统一为0
		
		for(int i = 0;i < code_char.length();i++){	//是否为合法字符，返回字符所在列
			if(c == code_char.charAt(i)){
				return i;
			}
		}
		System.out.println("Error char ->"+c);
		System.exit(0);
		
		
		return 0;
	}
	
	
	public static code_val scanner(String line,int[] i){
		//主要函数
		code_val t = new code_val();
		String token = "";							//拼接单词
		
		int s = 0, j = col(line.charAt(i[0]),colchar);
		
		//状态转移
		while(j < colchar.length()-1 && M[s][j]!=0){
			token += line.charAt(i[0]);
			s = M[s][j];							//下一状态
			if(line.charAt(i[0]) == '#') break; 	//读取完成

			j = col(line.charAt(++i[0]),colchar);	//下一字符转换为序列号
		}
		
		//是否终态
		boolean final_state = false;
		for(int k = 0;k < end.length;k++){
			if(s == end[k]){
				final_state = true;
				break;
			}
		}
		if(!final_state){//不是终态，退出
			if(token.equals(""))	System.out.println("Unidentified starting code -> "+line.charAt(i[0]));
			else 					System.out.println("Unfinished code -> "+token);
			System.exit(0);
		}
		
		//设置code、val
		if(search_table(token)!=' '){			//存在单词编码
			t.setcode(search_table(token));
		}
		else if(t.getcode()=='\0'){				//数字或标识符
			char first = token.charAt(0);
			if(first>='a'&&first<='z') t.setcode('i');	//标识符
			else t.setcode('u');						//数字
			
			t.setval(token);							//值
		}
		
		return t;
	}
	
	
	public static char search_table(String token){
		//把得到的词传进来，查询是否非标识符或数字
		for(int i = 0;i < table.length;i++){
			if(token.equals(table[i])){
				return code.charAt(i);	//存在 返回对应的code
			}
		}
		return ' ';						//不存在 返回' '
	}
	
	
	
	public static ArrayList<code_val> do_lex_text(String text) throws IOException{//输入一行字符串，返回该字符串分词结果数组，
		ArrayList<code_val> result = new ArrayList<code_val>();
		text = pretreatment.text_pretreatment(text);

		result = do_lex_line(text);
		
		return result;
	}
	public static ArrayList<code_val> do_lex_file(String text){//输入文件名，返回该文件分词结果数组，
		ArrayList<code_val> result = new ArrayList<code_val>();
		text = pretreatment.file_pretreatment(text);
		
		result = do_lex_file(text);
		
		return result;
	}
	private static ArrayList<code_val> do_lex_line(String text) throws FileNotFoundException, IOException{
		ArrayList<code_val> result = new ArrayList<code_val>();
		code_val t = new code_val();
		int[] i = {0};//当前读取的字符，为了能修改参数，用了只有一个元素的数组
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new 
        BufferedOutputStream(new FileOutputStream("src/data/Binary.txt"))));
        
		
		do{
                    while(text.charAt(i[0])==' '){//除去空格
                            i[0]++;
                    }

                    t = scanner(text,i);
                    bufferedWriter.write(t.getcode()+" "+t.getval()+" ");
                    System.out.print(t.getcode()+" "+t.getval()+" ");

                    result.add(t);
			
		}while(t.getcode()!='#');
		bufferedWriter.close();
		return result;
	}

	
	
	public static void main(String[] args) throws IOException {
//		String line = "   main(String args)[]1.23e-.3 cos cos2 cosa";
            String line = "a=1+2";
//            String line = "a=1";
		
            ArrayList<code_val> result= do_lex_text(line);
            for(int i = 0;i < result.size(); i ++){
            System.out.println(result.get(i));
        }
//		String filename="test.txt";
//		ArrayList<code_val> result_file= do_lex_file(filename);
//		for(int i = 0;i < result_file.size(); i ++){
//            System.out.println(result_file.get(i));
//        }
	}
}
