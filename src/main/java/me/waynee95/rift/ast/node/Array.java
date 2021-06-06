package me.waynee95.rift.ast.node;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class Array extends Node {
    public final List<Node> elems;

    public Array(List<Node> elems, ParserRuleContext ctx) {
        super("array", ctx);
        this.elems = elems;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> elems;
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
