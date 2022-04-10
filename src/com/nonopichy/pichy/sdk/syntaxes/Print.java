package com.nonopichy.pichy.sdk.syntaxes;

import com.nonopichy.pichy.sdk.pichysyntax.PichySyntax;
import com.nonopichy.pichy.sdk.pichysyntax.PichyType;

public class Print implements PichySyntax {
    @Override
    public String getName() {
        return "print";
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
    public void playEffect(String in) {
        System.out.println(in);
    }

    @Override
    public Object playReturn(String in) {
        return null;
    }
}
