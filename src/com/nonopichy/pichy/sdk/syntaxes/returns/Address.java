package com.nonopichy.pichy.sdk.syntaxes.returns;

import com.nonopichy.pichy.sdk.pichysyntax.PichySyntax;
import com.nonopichy.pichy.sdk.pichysyntax.PichyType;

import java.net.InetAddress;

public class Address implements PichySyntax {
    @Override
    public String getName() {
        return "machine-ip";
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
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e){
            return "0.0.0.0";
        }
    }
}
