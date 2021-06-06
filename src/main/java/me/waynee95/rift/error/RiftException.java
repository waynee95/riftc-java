package me.waynee95.rift.error;

import me.waynee95.rift.ast.Node;

import java.util.Optional;

public class RiftException extends RuntimeException {
    public final Optional<Node> node;

    public RiftException(String message, Optional<Node> node) {
        super(message);
        this.node = node;
    }

    public RiftException(String message) {
        super(message);
        this.node = Optional.empty();
    }

    @Override
    public String getMessage() {
        if (node.isEmpty()) {
            return super.getMessage();
        }
        return "(" + node.get().getLine() + ":" + node.get().getCol() + ") " + super.getMessage();
    }
}
