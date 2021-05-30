package me.waynee95.rift.ast;

public class Position {
    public static final Position EMPTY = new Position(-1, -1);

    public final int line;
    public final int col;

    public Position(int line, int col) {
        this.line = line;
        this.col = col;
    }

    @Override
    public String toString() {
        return line + ":" + col;
    }
}
