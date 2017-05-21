/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler_java;

/**
 *
 * @author Administrator
 */
public class code_val {
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
