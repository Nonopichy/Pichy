package com.nonopichy.pichy;

import com.nonopichy.pichy.sdk.pichysyntax.PichyOut;
import com.nonopichy.pichy.sdk.pichysyntax.PichySyntax;
import com.nonopichy.pichy.sdk.pichysyntax.PichyType;
import com.nonopichy.pichy.sdk.syntaxes.*;

import java.io.*;
import java.util.*;

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
        syntaxes.add(new AlertBox());
        syntaxes.add(new WebSite());

        syntaxes.add(new MathPi());
        syntaxes.add(new Address());

        while ((st = br.readLine()) != null){
            if(lastV != null){
                lastV.append(st);
            }
            // Execute break line
            if(lastN != null && st.length() > 0 && st.charAt(st.length()-1) == ';'){
                String result = Objects.requireNonNull(removeLastCharRegex(lastV.toString()));
                if(type.equals("variable"))
                    variables.put(lastN, result);
                else {
                    for (PichySyntax syntax : syntaxes) {
                        if (type.equals(syntax.getName())) {
                            System.out.println("-->" + result);
                            System.out.println("-->" + checkVariables(result));

                            if (syntax.getType() == PichyType.EFFECT) {
                                syntax.playEffect(checkVariables(result));
                            }
                            if (syntax.getType() == PichyType.RETURN) {
                                syntax.playReturn(checkVariables(result));
                            }
                        }
                    }
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

            defineVariable();
            // Execute without break line
            st = checkVariables(st);
            for (PichySyntax syntax : syntaxes) {
                if(syntax.prepareEffect(st) == PichyOut.EXECUTED.getOut()) {
                    break;
                }
            }
            /*

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

    public static void defineVariable(){
        if(st.charAt(0) != '@')
            return;
        type = "variable";
        String[] split = st.split(" ");
        String v_name = split[0];
        if(st.contains(v_name+" = ")) {
            st = st.replaceAll(v_name + " = ", "");
            if (st.charAt(0) == '"')
                st = st.replaceFirst("\"", "");
            if (st.charAt(st.length() - 1) != ';') {
                lastV = new StringBuilder(st);
                lastN = v_name;
            } else {
                st = checkVariables(removeLastCharRegex(st));
                try{
                    variables.put(v_name, Double.parseDouble(st));
                }catch (Exception a){
                    variables.put(v_name, st);
                }
            }
        }
    }

    public static String checkVariables(String in){
        for (Map.Entry<String, Object> e : variables.entrySet()) {
            if(in.contains(e.getKey())) {
                in = in.replaceAll(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        for (PichySyntax syntax : syntaxes) {
            if(syntax.getType() == PichyType.RETURN &&
                    in.contains("->@"+syntax.getName()))
                in = in.replaceAll("->@"+syntax.getName(), String.valueOf(syntax.playReturn(in)));
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
