package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        String inputText = "";
        List<String> memory = new ArrayList<>();

        File file = new File("tests/samples/comment.frag");
        Scanner scanner = new Scanner(file);
        

        inputText = scanner.nextLine();
        while (scanner.hasNextLine()) {
            inputText = inputText + "\n" + scanner.nextLine();
        }

        int lineNum = 1;
        int colNum = 1;

        char[] string = inputText.toCharArray();

        for (int i = 0; i < string.length; i++) {

            if (string[i] == '\n') {
                lineNum++;
                colNum = 1;
            }

            if ((string[i] >= 'A' && string[i] <= 'Z') || string[i] == '_' || (string[i] >= 'a' && string[i] <= 'z')) {
                String temp = "";
                temp += string[i];

                i++;
                colNum++;
                while ((string[i] >= 'A' && string[i] <= 'Z') || string[i] == '_'
                        || (string[i] >= 'a' && string[i] <= 'z')
                        || (string[i] >= '0' && string[i] <= '9')) {
                    temp += string[i];
                    i++;
                    colNum++;
                }

                // 식별자, 키워드 구분
                if (temp.equals("if")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_If", temp, lineNum, colNum - temp.length(), colNum - 1));
                }
                else if (temp.equals("else")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Else", temp, lineNum, colNum - temp.length(), colNum - 1));
                }
                else if (temp.equals("for")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_For", temp, lineNum, colNum - temp.length(), colNum - 1));
                }
                else if (temp.equals("while")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_While", temp, lineNum, colNum - temp.length(), colNum - 1));
                }
                else if (temp.equals("void")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Void", temp, lineNum, colNum - temp.length(), colNum - 1));
                }
                else if (temp.equals("class")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Class", temp, lineNum, colNum - temp.length(), colNum - 1));
                }
                else if (temp.equals("extends")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Extends", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("implements")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Implements", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("interface")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Interface", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("Print")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Print", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("break")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Break", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("return")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Return", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("this")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_This", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("new")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_New", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("ReadInteger")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_ReadInteger", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("ReadLine")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_ReadLine", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("NewArray")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_NewArray", temp, lineNum, colNum, colNum + temp.length() - 1));
                }
                else if (temp.equals("true") || temp.equals("false")) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_BoolConstant (token value: %5$s)", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
                }
                else {  // 키워드(타입), 식별자
                    if (temp.equals("int")) {
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Int", temp, lineNum, colNum - temp.length(), colNum - 1));
                    }
                    else if (temp.equals("double")) {
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Double", temp, lineNum, colNum, colNum + temp.length() - 1));
                    }
                    else if (temp.equals("bool")) {
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Bool", temp, lineNum, colNum, colNum + temp.length() - 1));
                    }
                    else if (temp.equals("string")) {
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_String", temp, lineNum, colNum, colNum + temp.length() - 1));
                    }
                    else if (temp.equals("id")) {
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier", temp, lineNum, colNum, colNum + temp.length() - 1));
                    }
                    else if (temp.equals("null")) {
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Null", temp, lineNum, colNum, colNum + temp.length() - 1));
                    }
                    else {
                        if (!memory.contains(temp)) {
                            memory.add(temp);   // 새로운 식별자는 메모리에 저장
                        }
                        // 식별자의 메모리 주소 판별 후 출력
                        System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_Identifier (token value: %5$d)", temp, lineNum, colNum, colNum + temp.length() - 1, memory.indexOf(temp) + 1));
                    }
                }
            }

            if (string[i] == '"') {
                String temp = "";
                i++;
                colNum++;
                while (string[i] != '"') {
                    temp += string[i];
                    i++;
                    colNum++;
                }
                System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_StringConstant", temp, lineNum, colNum, colNum + temp.length() - 1));
            }

            if (string[i] >= '0' && string[i] <= '9') {
                String temp = "";
                boolean isDouble = false;

                while (string[i] >= '0' && string[i] <= '9' ) {
                    temp += string[i];
                    i++;
                    colNum++;
                }

                if (string[i] == '.') {
                    isDouble = true;
                    temp += string[i];

                    i++;
                    colNum++;
                    while (string[i] >= '0' && string[i] <= '9' ) {
                        temp += string[i];
                        i++;
                        colNum++;
                    }
                }
                if (isDouble) {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_DoubleConstant (token value: %5$s)", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
                }else {
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is T_IntConstant (token value: %5$s)", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
                }

            }

            if (string[i] == '+' || string[i] == '-' || string[i] == '*' || string[i] == '%' || string[i] == ';' || string[i] == '('
                    || string[i] == ')' || string[i] == '{' || string[i] == '}' || string[i] == ',' || string[i] == '.' || string[i] == '[' || string[i] == ']') {
                String temp = "" + string[i];
                System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
            }


            if (string[i] == '=') {
                String temp = "" + string[i];
                if (string[i+1] == '=') {
                    temp += string[i+1];
                    i++;
                    colNum++;
                }
                System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
            }

            if (string[i] == '!') {
                String temp = "" + string[i];
                if (string[i+1] == '=') {
                    temp += string[i+1];
                    i++;
                    colNum++;
                }
                System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
            }

            if (string[i] == '<') {
                String temp = "" + string[i];
                if (string[i+1] == '=') {
                    temp += string[i+1];
                    i++;
                    colNum++;
                }
                System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
            }

            if (string[i] == '>') {
                String temp = "" + string[i];
                if (string[i+1] == '=') {
                    temp += string[i+1];
                    i++;
                    colNum++;
                }
                System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
            }

            if (string[i] == '&') {
                String temp = "" + string[i];
                if (string[i+1] == '&') {
                    temp += string[i+1];
                    i++;
                    colNum++;
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
                }
            }

            if (string[i] == '|') {
                String temp = "" + string[i];
                if (string[i+1] == '|') {
                    temp += string[i+1];
                    i++;
                    colNum++;
                    System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
                }
            }

            // 주석과 '/' 오퍼레이터 구분
            if (string[i] == '/') {
                String temp = "" + string[i];
                if (string[i + 1] == '*') {
                    i += 2;
                    colNum += 2;
                    while(i < string.length - 1) {
                        if (string[i] == '*' && string[i+1] =='/') {
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
                System.out.println(String.format("%1$-14sline %2$d cols %3$d-%4$d is '%5$s'", temp, lineNum, colNum, colNum + temp.length() - 1, temp));
            }
        }
    }
}
