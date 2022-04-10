package com.nonopichy.pichy.sdk.syntaxes;

import com.nonopichy.pichy.sdk.pichysyntax.PichySyntax;
import com.nonopichy.pichy.sdk.pichysyntax.PichyType;

public class MathPi implements PichySyntax {
    @Override
    public String getName() {
        return "3.1415";
    }

    @Override
    public PichyType getType() {
        return PichyType.RETURN;
    }

    @Override
    public int prepareEffect(String in) {
        return PichySyntax.super.prepareEffect(in);
    }

    @Override
    public void playEffect(String in){
    }

    @Override
    public Object playReturn(String in) {
        return Math.PI;
    }
}
