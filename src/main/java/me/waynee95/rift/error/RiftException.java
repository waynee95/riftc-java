package me.waynee95.rift.error;

public class RiftException extends RuntimeException {
    public final String msg;
    public final int line;
    public final int col;

    public RiftException(String msg, int line, int col) {
        this.msg = msg;
        this.line = line;
        this.col = col;
    }
}
