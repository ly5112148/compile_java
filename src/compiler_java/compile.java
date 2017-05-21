/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler_java;

import java.util.HashMap;
import java.util.Map;
import sun.applet.Main;

/**
 *
 * @author Administrator
 */
public class compile {
    public static Map<String,Float> var=new HashMap<>();
    public Map<String,String> result=new HashMap<String,String>(){{put("value","");put("result","");put("error","");put("execute_result","");}};
    public void run(String s){
        jar_source j_source=new jar_source(s+"\n");
        
        Scanner scanner=new Scanner();
        scanner.initial_variable();
        try {
            result=scanner.Scan(j_source);
        } catch (Exception e) {
            System.out.println("invalid.....");
            result.put("input",s+"\n");
            result.put("value","invalid input.");
        }
    }
    public static void main(String[] args){
        compile c=new compile();
        c.run("a=1");
    }
}
