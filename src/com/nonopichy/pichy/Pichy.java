package com.nonopichy.pichy;

import com.nonopichy.pichy.sdk.pichysyntax.PichyOut;
import com.nonopichy.pichy.sdk.pichysyntax.PichySyntax;
import com.nonopichy.pichy.sdk.syntaxes.Print;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pichy {

    public static final HashMap<String, Object> variables = new HashMap<>();
    public static final List<PichySyntax> syntaxes = new ArrayList<>();

    public static String result;
    public static String st;
    public static String lastN = null;
    public static StringBuilder lastV = null;
    public static String type = "";

    public static <T> void main(String[] args) throws IOException {

        File file = new File("test.pichy");
        BufferedReader br = new BufferedReader(new FileReader(file));

        syntaxes.add(new Print());

        while ((st = br.readLine()) != null){
            if(lastV != null){
                lastV.append(st);
            }
            if(lastN != null && st.charAt(st.length()-1) == ';'){
                String result = removeLastCharRegex(lastV.toString());
                for (PichySyntax syntax : syntaxes) {
                    syntax.playEffect(checkVariables(result));
                }
                /*
                if (type.equals("variable")) {
                    try {
                        variables.put(lastN, Double.parseDouble(result));
                    } catch (NumberFormatException e) {
                        try {
                            variables.put(lastN, Boolean.parseBoolean(result));
                        } finally {
                            variables.put(lastN, result);
                        }
                    }
                } else if (type.equals("print")){
                    for (Map.Entry<String, Object> e : variables.entrySet()) {
                        if(result.contains(e.getKey()))
                            result = result.replaceAll(e.getKey(),String.valueOf(e.getValue()));
                    }
                    System.out.println(result);
                }

                 */
                lastN = null;
                lastV = null;
                type = "";
            }
            for (PichySyntax syntax : syntaxes) {
                if(syntax.prepareEffect(st) == PichyOut.EXECUTED.getOut())
                    break;
            }
            /*
            if(st.charAt(0) == '@'){
                type = "variable";
                String[] split = st.split(" ");
                String v_name = split[0];
                if(st.contains(v_name+" = ")) {
                    st = st.replaceAll(v_name + " = ", "");
                    if (st.charAt(0) == '"') {
                        st = st.replaceFirst("\"", "");
                    }
                    if (st.charAt(st.length() - 1) != ';') {
                        lastV = new StringBuilder(st);
                        lastN = v_name;
                    } else {
                        st = removeLastCharRegex(st);
                        String a = st;
                        try{
                            if(st.contains("instance-class-arg")){
                                type = "instance-class-arg";
                                st = st.replaceFirst("instance-class-arg ", "");
                                for (Map.Entry<String, Object> e : variables.entrySet()) {
                                    if(st.contains(e.getKey()))
                                        st = st.replaceAll(e.getKey(),String.valueOf(e.getValue()));
                                }

                                    st = removeLastCharRegex(st);

                                    String[] info = st.split(":")[1].split("/");


                                        Constructor[] ctors =  Class.forName(st.split(":")[0]).getDeclaredConstructors();
                                        Constructor ctor = null;
                                        for (int i = 0; i < ctors.length; i++) {
                                            ctor = ctors[i];
                                            if (ctor.getGenericParameterTypes().length == 0)
                                                break;
                                        }
                                        variables.put(v_name, ctor.newInstance(info));

                            } else {
                                st = a;

                                try {
                                    variables.put(v_name, Double.parseDouble(st));
                                } catch (NumberFormatException p) {
                                    try {
                                        variables.put(v_name, Boolean.parseBoolean(st));
                                    } finally {
                                        variables.put(v_name, st);
                                    }
                                }

                            }
                        }catch (Exception e){

                        }

                    }
                } else if(st.contains("++")){
                    try {
                        v_name = v_name.replaceAll("\\+\\+", "");
                        double var = (double) isVariable(v_name);
                        variables.put(v_name, var + 1);
                    }catch (Exception e){

                    }
                    type = "";

                }else if(st.contains("--")){
                    try {
                    v_name = v_name.replaceAll("--","");
                    double var = (double) isVariable(v_name);
                    variables.put(v_name, var-1);
                }catch (Exception e){

                }
                    type = "";
                }
            }
            if(st.startsWith("print")){
                type = "print";
                st = st.replaceFirst("print ", "");

                if(st.charAt(st.length()-1) != ';') {
                    lastV = new StringBuilder(st);
                    lastN = "";
                } else {
                    System.out.println(removeLastCharRegex(st));
                    type = "";
                }
            }
            if(st.startsWith("instance-class-arg")){
                type = "instance-class-arg";
                st = st.replaceFirst("instance-class-arg ", "");
                for (Map.Entry<String, Object> e : variables.entrySet()) {
                    if(st.contains(e.getKey()))
                        st = st.replaceAll(e.getKey(),String.valueOf(e.getValue()));
                }
                if(st.charAt(st.length()-1) != ';') {
                    lastV = new StringBuilder(st);
                    lastN = "";
                } else {
                    st = removeLastCharRegex(st);

                    String[] info = st.split(":")[1].split("/");

                    try {
                        Constructor[] ctors =  Class.forName(st.split(":")[0]).getDeclaredConstructors();
                        Constructor ctor = null;
                        for (int i = 0; i < ctors.length; i++) {
                            ctor = ctors[i];
                            if (ctor.getGenericParameterTypes().length == 0)
                                break;
                        }
                        ctor.newInstance(info);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    type = "";
                }
            }

             */
        }
    }

    public Pichy(String a, String b){
        System.out.println("a >"+a);
        System.out.println("b >"+b);
    }
    public static String checkVariables(String in){
        for (Map.Entry<String, Object> e : variables.entrySet()) {
            if(in.contains(e.getKey()))
                in = in.replaceAll(e.getKey(),String.valueOf(e.getValue()));
        }
        return in;
    }
    public static Object isVariable(String key){
        return variables.get(key);
    }
    public static String removeLastCharRegex(String s) {
        return (s == null) ? null : s.replaceAll(".$", "");
    }
}
