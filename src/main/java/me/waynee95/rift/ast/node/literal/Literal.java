package me.waynee95.rift.ast.node.literal;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Literal<T> extends Node {
    public final T value;

    public Literal(String displayName, T value, ParserRuleContext ctx) {
        super(displayName, ctx);
        this.value = value;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> value;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 1;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visit(this, ctx);
    }
}
