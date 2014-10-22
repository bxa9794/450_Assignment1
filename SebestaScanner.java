/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sebestaScanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bharathreddy
 */
public class SebestaScanner {

    /**
     * @param args the command line arguments
     */
    /*GLOBAL VARIABLES*/
    static int charClass, lexLen, token, nextToken;
    static char lexeme[] = new char[10];
    static char nextChar;
    static FileReader inputStream = null;
    /*CHARCHTER CLASSES*/
    static final int LETTER = 0;
    static final int DIGIT = 1;
    static final int UNKNOWN = 99;
    static final int EOF = -1;
    /*TOKEN CODES*/
    static final int INT_LIT = 10;
    static final int IDENT = 11;
    static final int ASSIGN_OP = 20;
    static final int ADD_OP = 21;
    static final int SUB_OP = 22;
    static final int MULT_OP = 23;
    static final int DIV_OP = 24;
    static final int LEFT_PAREN = 25;
    static final int RIGHT_PAREN = 26;
    
    public static void main(String[] args) {
        // TODO code application logic here
        try{
           inputStream = new FileReader("C:\\Users\\bharathreddy\\Documents\\NetBeansProjects\\SebestaScanner\\front.txt");
         
            func f = new func();
            f.getChar();
            do{
               f.lex();
            }while(nextToken!=EOF);
        }catch(FileNotFoundException e){  
            System.out.println("File open error");
            System.out.println("Directory: "+ System.getProperty("user.dir"));
        }finally{
            if(inputStream!= null){
                try{
                inputStream.close();
                }catch(IOException e){
                    System.out.println("File close error");
                }
            }
        }
       
    }
    


public static class func{
    

void addChar(){
    
     if(lexLen<=98){
    
        lexeme[lexLen++] = nextChar;  
                
      }
    
    else
        System.out.println("Error: lexeme is too long.");
 
    lexeme[lexLen] = 0;  
   
}


public void getChar(){
    
    int temp=0;
    try {
        temp = inputStream.read();
    } catch (IOException ex) {
        Logger.getLogger(SebestaScanner.class.getName()).log(Level.SEVERE, null, ex);
    }
    nextChar = (char) temp;
    if(nextChar != EOF){
        if(Character.isAlphabetic(nextChar))
            charClass = LETTER;
        else if(Character.isDigit(nextChar))
            charClass = DIGIT;
        else
            charClass = UNKNOWN;
    
    }
    else
        charClass = EOF;
}

void getNonBlank(){

    while(Character.isWhitespace(nextChar))
        
        
        getChar();
}


int lookup(char ch){

    switch(ch){
        case '(':
             addChar();
             nextToken = LEFT_PAREN;
             break;

        case ')':
             addChar();
             nextToken = RIGHT_PAREN;
             break; 

        case '+':
             addChar();
             nextToken = ADD_OP;
             break;

        case '-':
             addChar();
             nextToken = SUB_OP;
             break;

        case '*':
             addChar();
             nextToken = MULT_OP;
             break;

         case '/':
             addChar();
             nextToken = DIV_OP;
             break;

         default:
             addChar();
             nextToken = EOF;
             //nextToken = EOF;
         lexeme[0] = 'E';
         lexeme[1] = '0';
         lexeme[2] = 'F';
         lexeme[3] = 0;
             break;
    }

    return nextToken;
}


int lex(){

    lexLen = 0;
    getNonBlank();
    switch (charClass){

    case LETTER:
         addChar();
         getChar();
         while(charClass == LETTER || charClass == DIGIT){

            addChar();
            getChar();
         }
         nextToken = IDENT;
         break;

    case DIGIT:
         addChar();
         getChar();
         while(charClass == DIGIT){

            addChar();
            getChar();
         }
         nextToken = INT_LIT;
         break;

    case UNKNOWN:
         lookup(nextChar);
         getChar();
         break;

    case EOF:
         nextToken = EOF;
         lexeme[0] = 'E';
         lexeme[1] = '0';
         lexeme[2] = 'F';
         lexeme[3] = 0;

    }

   
    System.out.printf("Next token is: %d: ", nextToken);
    System.out.printf("Next lexeme is %s:  \n ", new String(lexeme).trim());
    //System.out.println("------------------------------------------------- ");
    Arrays.fill(lexeme,' ');
    return nextToken;
}
}
}


