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
public class Token {
    public final Scanner.Type type; 
    public final String value; 

    public Token(Scanner.Type curType, String value) {
            this.type = curType; 
            this.value = value;

    } 
}
