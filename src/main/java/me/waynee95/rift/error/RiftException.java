package me.waynee95.rift.error;

import me.waynee95.rift.ast.node.Node;

public class RiftException extends RuntimeException {
    public final Node node;

    public RiftException(String message, Node node) {
        super(message);
        this.node = node;
    }

    public RiftException(String message) {
        super(message);
        this.node = null;
    }

    @Override
    public String getMessage() {
        if (node == null) {
            return super.getMessage();
        }
        return "(" + node.getLine() + ":" + node.getCol() + ") " + super.getMessage();
    }
}
