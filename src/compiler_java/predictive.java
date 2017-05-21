/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compiler_java;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Administrator
 */
public class predictive {
    public boolean isT_NT(char c,String str){
        for(int i=0;i<(int)str.length();i++){
            if(c==str.substring(i, i+1).toCharArray()[0]){
                return true;
            }
        }
        return false;
    }
    int lin_col(char c,String str){
        for(int i=0;i<(int)str.length();i++){
            if(c==str.substring(i,i+1).toCharArray()[0]){
                return i;
            }
        }
        System.out.println("Err in lin_col()->"+c+'\n');
        return -1;
    }
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        predictive pdt=new predictive();
        String NT="EDTSF";
        String T1="+*()ixy#";
        String T="+*()ixy";
        int M[][]={
            {-1,-1,0,-1,0,0,0,-1},
            {1,-1,-1,2,-1,-1,-1,2},
            {-1,-1,3,-1,3,3,3,-1},
            {5,4,-1,5,-1,-1,-1,5},
            {-1,-1,6,-1,7,8,9,-1}
        };
        String[] p={"E->TD","D->+TD","D->","T->FS","S->*FS","S->","F->(E)","F->i","F->x","F->y"};
        int StackLen=50;
        int WordLen=20;
        char[] stack=new char[StackLen];
        stack[0]='#';
        stack[1]='E';
        int top=1,j=0;
        System.out.println("step"+'\t'+"栈"+'\t'+"X"+'\t'+"单词种别");
        System.out.print(j+")");
        InputStreamReader read = new InputStreamReader(
                new FileInputStream("src/data/Binary.txt"),"GBK");
        BufferedReader bufferedReader = new BufferedReader(read);
        String line=bufferedReader.readLine();
        String[] result=line.split(" ");
        int index=0;
        while(true){
            System.out.print('\t');
            for(int i=0;i<=top;i++){
                System.out.print(stack[i]);
            }
            System.out.print('\t');
            System.out.print(' ');
            System.out.print('\t');
            System.out.println(result[index]);
            char X=stack[top--];
            if(!pdt.isT_NT(X,NT)&&!pdt.isT_NT(X,T1)){
                System.out.println("Err in main->"+X);
                break;
            }
            ++j;
            System.out.print(j+")"+'\t');
            for(int i=0;i<=top;i++){
                System.out.print(stack[i]);
            }
            System.out.println(""+'\t'+X+'\t'+result[index]);
            if(X=='#'){
                String s=String.valueOf(X);
                if(s.equals(result[index])){
                    System.out.println("\tAcc");
                    break;
                }else{
                    System.out.println("Err in #-->"+X+'\t'+result[index]);
                    break;
                }
            }
            if(pdt.isT_NT(X, T)){
                String s=String.valueOf(X);
                if(s.equals(result[index])){
                    index+=2;
                }else{
                    System.out.println("Err in T-->"+X+'\t'+result[index]);
                    break;
                }
            }
            if(pdt.isT_NT(X, NT)){
                int lin=pdt.lin_col(X, NT),col=pdt.lin_col(result[index].toCharArray()[0], T1),k=M[lin][col];
                if(k!=-1){
                    for(int i=p[k].length()-1;i>=3;i--)
                        stack[++top]=p[k].substring(i,i+1).toCharArray()[0];
                }else{
                    System.out.println("Err in NT-->"+X+'\t'+result[index]);
                    break;
                }
            }
        }
        bufferedReader.close();
    }  
}