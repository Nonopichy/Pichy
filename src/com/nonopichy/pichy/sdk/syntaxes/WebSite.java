package com.nonopichy.pichy.sdk.syntaxes;

import com.nonopichy.pichy.sdk.pichysyntax.PichySyntax;
import com.nonopichy.pichy.sdk.pichysyntax.PichyType;

import javax.swing.*;

public class WebSite implements PichySyntax {
    @Override
    public String getName() {
        return "website";
    }

    @Override
    public PichyType getType() {
        return PichyType.EFFECT;
    }

    @Override
    public int prepareEffect(String in) {
        return PichySyntax.super.prepareEffect(in);
    }

    @Override
    public void playEffect(String in){
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("rundll32 url.dll,FileProtocolHandler " + in);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Object playReturn(String in) {
        return null;
    }
}
