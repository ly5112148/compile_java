/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler_java;

import static compiler_java.lex.do_lex_text;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class test {
    public static void main(String[] args){
        compile c=new compile();
        System.out.println("before:   "+c.result.values());
        String text="a=1+2\n";
        c.run(text);
        c.result.put("input", text+"\n");
        System.out.println(c.result.get("variable"));
        String [] results = c.result.get("value").split("@");
        String show = ">>>" + c.result.get("input");
        for (String line:results){
            show += line + "\n";
        }
        System.out.println(show);
        System.out.println("after:   "+c.result.values());
        c.result = new HashMap<String,String>(){{put("value", "");put("result", "");put("error", "");put("index", "");put("execute_result","");}};
    }   
}
