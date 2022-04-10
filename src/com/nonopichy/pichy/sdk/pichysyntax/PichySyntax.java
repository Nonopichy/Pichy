package com.nonopichy.pichy.sdk.pichysyntax;

import com.nonopichy.pichy.Pichy;

public interface PichySyntax {

    String getName();
    PichyType getType();

    default int prepareEffect(String in){
        String treat = treatPlay(getName());
        if(treat==null)
            return PichyOut.BREAK_LINE.getOut();
        if(treat.equals("%-next"))
            return PichyOut.NEXT.getOut();
        playEffect(Pichy.result);
        return PichyOut.EXECUTED.getOut();
    }

    void playEffect(String in);

    Object playReturn(String in);

    static String treatPlay(String name){
        if(!Pichy.st.split(" ")[0].equalsIgnoreCase(name))
            return "%-next";
        Pichy.type = name;
        Pichy.st = Pichy.st.replaceFirst(name+" ", "");

        if(Pichy.st.charAt(Pichy.st.length()-1) != ';') {
            Pichy.lastV = new StringBuilder(Pichy.st);
            Pichy.lastN = "";
            return null;
        } else {
            Pichy.type = "";
            return Pichy.result = Pichy.removeLastCharRegex(Pichy.st);
        }
    }

}
