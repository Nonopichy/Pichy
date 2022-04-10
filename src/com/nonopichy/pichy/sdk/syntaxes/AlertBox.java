package com.nonopichy.pichy.sdk.syntaxes;

import com.nonopichy.pichy.sdk.pichysyntax.PichySyntax;
import com.nonopichy.pichy.sdk.pichysyntax.PichyType;

import javax.swing.*;

public class AlertBox implements PichySyntax {
    @Override
    public String getName() {
        return "alertbox";
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
        JOptionPane.showMessageDialog(null, in);
    }

    @Override
    public Object playReturn(String in) {
        return null;
    }
}
