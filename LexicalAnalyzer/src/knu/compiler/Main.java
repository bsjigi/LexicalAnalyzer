package knu.compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import org.graalvm.compiler.core.common.type.ArithmeticOpTable.BinaryOp.And;




public class Main {
    

    public static void main(String[] args) throws FileNotFoundException {

        String inputText = "";
        List<String> memory = new ArrayList<>();
        

        File file = new File("tests/samples/badpre.frag");
        Scanner scanner = new Scanner(file);
        

        inputText = scanner.nextLine();
        while (scanner.hasNextLine()) {
            inputText = inputText + "\n" + scanner.nextLine();
        }

        int lineNum = 1;
        int colNum = 0;
        String front = "";
        String E = "";
       
        

        char[] string = inputText.toCharArray();
        

    
        for (int i = 0; i < string.length; i++) {
            boolean unknown_char = true;
            colNum++;

            if (string[i] >= '0' && string[i] <= '9') {
                String temp = "";
                boolean isDouble = false;
                boolean notHex = false;

                while (i < string.length && string[i] >= '0' && string[i] <= '9') {
                    temp += string[i];
                    i++;
                    colNum++;
                }
                


                // How to deal with floating-point numbers?
                //number 해결

                 

                if (string[i] == '.') {
                    isDouble = true;
                    temp += string[i];  
                    i++;
                    colNum--;
                    
                    while (i < string.length && (string[i] >= '0' && string[i] <= '9'|| string[i] == 'E' || string[i] =='+' || string[i] == '-')) {
                        temp += string[i];
                        i++;  
                        colNum = 1;
                    }
                    
                }
                
                else {
                    while (i < string.length && ((string[i] >= 'A' && string[i] <= 'Z') || string[i] == '_'
                        || (string[i] >= 'a' && string[i] <= 'z')
                        || (string[i] >= '0' && string[i] <= '9'))) {
                    temp += string[i];
                    i++;
                    colNum++;
                    }
                   
                }
                
                if (isDouble) {
                    if(temp.contains("+-")){
                        if(temp.contains("E")){
                            int idx = temp.indexOf("E");
                            front = temp.substring(0, idx);
                            System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_DoubleConstant (token value: %5$s)", front, lineNum, colNum, colNum + front.length() - 1, Double.valueOf(front)));
                            E = "E";
                            System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token address: %5$d)", E, lineNum, colNum + front.length()  , colNum + front.length()  , E.length()));
                            temp = temp.substring(idx+1);  
                            
                                
                        }
                        
                        String [] tempArray2 = temp.split("");
                        for (String s: tempArray2){
                            if (s.equals("+") || s.equals("-")){ 

                                System.out.println(String.format("%1$-14s line %2$d cols %3$d-%4$d is '%5$s'", s, lineNum, colNum + front.length()+ E.length(), colNum + front.length() + E.length(), s));
                                colNum++;
                            }
                            else{
                                System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_IntConstant (token value: %5$s)", s, lineNum,  colNum + front.length()+ E.length()  , colNum + front.length() + E.length(), Integer.decode(s)));
                                System.exit(0);
                            }
            
                        }
                    }
                    else{
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_DoubleConstant (token value: %5$s)", temp, lineNum, colNum, colNum + temp.length() - 1, Double.valueOf(temp)));
                    }
                }
                 else if (temp.matches(".*[0-9].*") ){
                     if(temp.matches(".*[xX].*") == true && temp.length()>2){
                        String temp1 = "";
                        String[] tempArray1 = temp.split("");
                        for(String s : tempArray1){
                            if (!s.equals("0")){
                                temp1 += s;
                            }
                        }
                        if(temp.matches(".*[g-wG-W].*") == true && temp.length()< 4){
                            System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_IntConstant (token value: %5$s)", 0 , lineNum, 1  ,1, 0));
                            System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token value: %5$d)", temp1 , lineNum, colNum - temp.length() + 1  , colNum -1  , temp1.length()+1));
                        }
                        else{
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_IntConstant (token value: %5$s)", temp, lineNum, colNum - temp.length()  , colNum -1, Integer.decode(temp)));
                        }
                    }
                    else{
                        String strnum = temp.replaceAll("[^0-9]","");
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_IntConstant (token value: %5$s)", strnum, lineNum, colNum - temp.length()  , colNum - temp.length() + strnum.length() -1, Integer.decode(strnum)));
                        String onlystr = temp.replaceAll("[[0-9]||[/!@#$%^&*(){}]]", "");
                        if (onlystr.length()>0){
                            if (!memory.contains(onlystr)) {
                                memory.add(onlystr); 
                            }
                       
                                System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token value: %5$d)", onlystr, lineNum, colNum - temp.length() + strnum.length()  , colNum -1  , memory.indexOf(onlystr) + 1));
                        }
                      
                    }
                 }
                else if ((temp.equals("0x") || temp.equals("0X"))){
                    String[] tempArray = temp.split("");
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_IntConstant (token value: %5$s)", tempArray[0], lineNum, 1  ,1, 0));
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token value: %5$d)", tempArray[1], lineNum, 2  , 2  , 1));
                }
              
                else if(temp.contains("E")){
                    int idx = temp.indexOf("E");
                    front = temp.substring(0, idx);
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_IntConstant (token value: %5$s)", front, lineNum, colNum - temp.length()  , colNum -1, Integer.decode(front)));
                    temp = "E";
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token address: %5$d)", temp, lineNum, colNum - temp.length()  , colNum -1  , temp.length()));
                }

                else {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_IntConstant (token value: %5$s)", temp, lineNum, colNum - temp.length()  , colNum -1, Integer.decode(temp)));
                }

                
                unknown_char = false;
            }
            

            if ((string[i] >= 'A' && string[i] <= 'Z') || string[i] == '_' || (string[i] >= 'a' && string[i] <= 'z')) {
                String temp = "";
                temp += string[i];
                i++;
                colNum++;
                while (i < string.length && ((string[i] >= 'A' && string[i] <= 'Z') || string[i] == '_'
                        || (string[i] >= 'a' && string[i] <= 'z')
                        || (string[i] >= '0' && string[i] <= '9'))) {
                    temp += string[i];
                    i++;
                    colNum++;
                }

                // 식별자, 키워드 구분
                if (temp.equals("if") || temp.equals("else") || temp.equals("for") || temp.equals("while") || temp.equals("void") ||
                        temp.equals("class") || temp.equals("extends") || temp.equals("implements") || temp.equals("interface") ||
                        temp.equals("Print") || temp.equals("break") || temp.equals("return") || temp.equals("this") ||
                        temp.equals("new") || temp.equals("ReadInteger") || temp.equals("ReadLine") || temp.equals("NewArray") ||
                        temp.equals("int") || temp.equals("double") || temp.equals("bool") || temp.equals("string") ||
                        temp.equals("id") || temp.equals("null")){
                    System.out.println(String.format("%1$-14s line %2$d cols %3$d-%4$d is T_%5$s", temp, lineNum, colNum - temp.length(), colNum - 1, temp.substring(0, 1).toUpperCase() + temp.substring(1)));
                } else if (temp.equals("true") || temp.equals("false")) {
                    System.out.println(String.format("%1$-14s line %2$d cols %3$d-%4$d is T_BoolConstant (token value: %5$s)", temp, lineNum, colNum - temp.length(), colNum - 1, temp));
                }
                else {  // 키워드(타입), 식별자
                    // How to deal with identifiers?
                    if (!memory.contains(temp)) {
                        memory.add(temp); // 새로운 식별자는 메모리에 저장
                    }
                    if(temp.length() > 1){
                        if(temp.charAt(1)=='x'|| temp.charAt(1)=='X'){
                            System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token value: %5$d)", temp, lineNum, colNum - temp.length()  , colNum -1  , temp.length()-2));
                        }else if ((colNum - 1) > 35){
                        System.out.println(String.format("\n*** Error line %d.", lineNum));
                        System.out.println(String.format("*** Identifier too long: \"%s\"\n", temp));
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token address: %5$d)", temp, lineNum, colNum - temp.length()  , colNum -1  , memory.indexOf(temp) + 1));
                        }
                        else{
                        // 식별자의 메모리 주소 판별 후 출력
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token value: %5$d)", temp, lineNum, colNum - temp.length()  , colNum -1  , memory.indexOf(temp) + 1));
                        }   
                    }else{
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token value: %5$d)", temp, lineNum, colNum - temp.length()  , colNum -1  , memory.indexOf(temp) + 1));  
                    }
                }

                if (i >= string.length) {
                    break;
                } else {
                    i--;
                    colNum--;
                }
                unknown_char = false;
            }

            if (string[i] == '"') {
                String temp = "\"";
                i++;
                colNum++;
                boolean is_terminated = true;

                while (i < string.length && string[i] != '"') {
                    if (string[i] == '\n') {
                        System.out.println(String.format("\n*** Error line %d.", lineNum));
                        System.out.println(String.format("*** Unterminated string constant: %s\n", temp));
                        is_terminated = false;
                        break;
                    }
                    temp += string[i];
                    i++;
                    colNum++;
                }

                if (i < string.length &&  string[i] == '"') {
                    temp += string[i];
                }

                if (is_terminated) {
                    System.out.println(String.format("%1$-14s line %2$d cols %3$d-%4$d is T_StringConstant (token value: %5$s)", temp, lineNum , colNum - temp.length() + 1, colNum, temp));
                }

                if (i >= string.length) {
                    break;
                }

                unknown_char = false;
            }

            if (string[i] == '+' || string[i] == '-' || string[i] == '*' || string[i] == '%' || string[i] == ';' || string[i] == '('
                    || string[i] == ')' || string[i] == '{' || string[i] == '}' || string[i] == ',' || string[i] == '.' || string[i] == '[' || string[i] == ']') {
                String temp = "" + string[i];
                if(string[i] == '*'){
                    lineNum++;
                }
                System.out.println(String.format("%1$-14s line %2$d !cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
                unknown_char = false;
            }

            if (string[i] == '=' || string[i] == '!' || string[i] == '<' || string[i] == '>') {
                String temp = "" + string[i];
                if (string[i+1] == '=') {
                    temp += string[i+1];
                    i++;
                    colNum++;
                }
                System.out.println(String.format("%1$-14s line %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum - temp.length() + 1, colNum, temp));
                unknown_char = false;
            }

            if (string[i] == '&') {
                String temp = "" + string[i];
                if (string[i+1] == '&') {
                    temp += string[i + 1];
                    i++;
                    colNum++;
                    System.out.println(String.format("%1$-14s line %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum - temp.length() + 1, colNum, temp));
                    unknown_char = false;
                }
            }

            if (string[i] == '|') {
                String temp = "" + string[i];
                if (string[i+1] == '|') {
                    temp += string[i+1];
                    i++;
                    colNum++;
                    System.out.println(String.format("%1$-14s line %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
                    unknown_char = false;
                }
            }

            // How to deal with single-line and multi-line comments?
            if (string[i] == '/' ) {
                unknown_char = false;            
                String temp = "" + string[i];                
                if (string[i + 1] == '*') {                      
                    i+=2;   
                    colNum += 2;                   
                    while(i < string.length - 1) {
                        if (string[i] == '*' && string[i+1] =='/') {     
                            i++;
                            colNum++;
                            break;
                        }
                        else if(string[i+1] == '\n'){
                            i++;
                            colNum++;
                            lineNum++;
                        }
                        else {  
                            temp += string[i];
                            i++;
                            colNum++;
                        }                   
                    }
                }
                else if(string[i + 1] == '/'){
                    i+=2;   
                    colNum += 2;
                    while(i < string.length ) {
                        if (string[i+1] =='\n') {     
                            i++;
                            colNum++;
                            break;
                        }
                        else {  
                            i++;
                            colNum++;
                        } 
                    } 
                }
                else{
                    System.out.println(String.format("%1$-14s line %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
                }
                
                //System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
            }
            
            
            if (string[i] == '\n' ) {
                colNum = 0;
                lineNum++;
            } else {
                if (unknown_char && string[i] != ' ') {
                    System.out.println(String.format("\n*** Error line %d.", lineNum));
                    System.out.println(String.format("*** Unrecognized char: '%c'\n", string[i]));
                }
            }
        }
    }
}
