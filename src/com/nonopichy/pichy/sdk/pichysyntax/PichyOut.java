package com.nonopichy.pichy.sdk.pichysyntax;

public enum PichyOut {
    BREAK_LINE(0),
    EXECUTED(1),
    NEXT(2);

    public final int n;

    private PichyOut(int n) {
        this.n = n;
    }

    public int getOut() {
        return n;
    }
}
