/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler_java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class Scanner {
    public static enum Type { 
        Variable, if_grammar, Bool, EndSymbol, Space, Number,
        oper, compare_oper, assign_oper, String, delete_symbol,
        bool_oper, left_bracket, right_bracket, single_function,
        multiple_function, colon, question_mark, exclamatory_mark, comma; 
    }
    private static ArrayList<Token> tks = new ArrayList<Token>();
    private String token = "";
    private Type curType = Type.Space;
    
    public void initial_variable(){
        tks = new ArrayList<Token>();
        String token = "";
        Type curType = Type.Space;
    }
    public Map<String,String> Scan(jar_source source) throws Exception{
        char curChar = source.currentChar();
        Map<String,String> parse = new HashMap<String,String>(){{put("execute_result", "");put("result", "");put("error", "");put("index", "");put("value","");}};
        Map<String,String> result = new HashMap<>();
        result.put("input",source.getString());
        String value_string ="";
//        System.out.println(curChar);
        while(curChar!='~'){
            if((curChar>='a'&&curChar<='z')||(curChar>='A'&&curChar<='Z')){
                while((curChar>='a'&&curChar<='z')||(curChar>='A'&&curChar<='Z')
                        ||(curChar>='0'&&curChar<='9')||curChar=='_'){
                    this.token+=curChar;
                    this.curType=Type.Variable;
                    curChar=source.nextChar();
                }
                addToken(this.curType,this.token);
                continue;
            }
            if(curChar == ' '){
                curChar = source.nextChar();
                continue;
            }
            if(curChar>='0' && curChar<='9'){
                    while(curChar>='0' && curChar<='9'){
                            this.token += curChar;
                            this.curType = Type.Number;
                            curChar = source.nextChar();
                    }
                    if(curChar=='.'){
                            this.token += curChar;
                            curChar = source.nextChar();
                            while(curChar>='0' && curChar<='9'){
                                    this.token += curChar;
                                    this.curType = Type.Number;
                                    curChar = source.nextChar();
                            }
                    }
                    if(curChar=='E' || curChar=='e'){
                            this.token += curChar;
                            curChar = source.nextChar();
                            if(curChar=='+' || curChar=='-'){
                                    this.token += curChar;
                                    curChar = source.nextChar();
                            }
                            if(curChar>='0' && curChar<='9'){
                                    while(curChar>='0' && curChar<='9'){
                                            this.token += curChar;
                                            this.curType = Type.Number;
                                            curChar = source.nextChar();
                                    }
                            }
                            else{
                                    System.out.println("error" + "    " + curChar);
                            }
                    }
                    addToken(this.curType, this.token);
                    continue;
            }
            if(curChar=='+' || curChar=='-' || curChar=='*' || curChar=='/' || curChar=='^'){
                    this.token += curChar;
                    this.curType = Type.oper;
                    char c = curChar;
                    curChar = source.nextChar();
//				if((c=='+' && curChar=='+') || (c=='-' && curChar=='-')){
//					this.token += curChar;
//					this.curType = Type.oper;
//					curChar = source.nextChar();
//				}
                    addToken(this.curType, this.token);
                    continue;
            }
            if(curChar=='!' || curChar=='='){
                    this.token += curChar;
                    this.curType = Type.assign_oper;
                    char c = curChar;
                    curChar = source.nextChar();
                    if((c=='!'||c=='=') && curChar=='='){
                            this.token += curChar;
                            this.curType = Type.compare_oper;
                            curChar = source.nextChar();
                    }else if(c=='!'||c=='<'||c=='>'){
                            this.curType = Type.compare_oper;
                    }
                    addToken(this.curType, this.token);
                    continue;
            }
            if(curChar==';'){
                    addToken(Type.EndSymbol, ";");
                    curChar = source.nextChar();
                    continue;
            }else if(curChar=='\n'){
                    addToken(Type.EndSymbol, "");

                    //若为空，则到了句尾，解析这条语句
                    if(this.curType == Type.EndSymbol){
                            for(Token t:tks){
                                    System.out.println(t.type + "    " + t.value);
                            }
                            Parser parser = new Parser(tks);
                            parse = parser.parse();
                            value_string += parse.get("result")+"@";
                            result.put("error",parse.get("error"));
                            result.put("variable",getVariables());


                            tks = new ArrayList<Token>();
                            System.out.println("tokens re initialize");
                    }

                    curChar = source.nextChar();
                    continue;
            }
            result.put("error","invalid input: "+String.valueOf(curChar));
            result.put("variables",getVariables());
            break;
        }
        result.put("value",value_string);
        return result;
    }
    public void addToken(Type curType,String token){
        if(curType == Type.Variable){
            if(token.equals("if") || token.equals("else") || token.equals("elif")
                            || this.token.equals("end")){
                curType = Type.if_grammar;
            }else if(token.toLowerCase().equals("true") || token.toLowerCase().equals("false")){
                curType = Type.Bool;
            }else if(token.equals("sin") || token.equals("cos")){
                curType = Type.single_function;
            }else if(token.equals("max") || token.equals("min")){
                curType = Type.multiple_function;
            }
        }

        Scanner.tks.add(new Token(curType,token));
        this.token = "";
        if(curType== Type.EndSymbol){
            this.curType = Type.EndSymbol;
        }else{
            this.curType = Type.Space;
        }

    }
    public String getVariables(){
            String v = "    Variable    :    value    \n";
            for (String key:compile.var.keySet()){
                    v += "    "+key+"    :    "+compile.var.get(key)+"\n";
            }
            return v;
    }
}
