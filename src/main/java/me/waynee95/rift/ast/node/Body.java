package me.waynee95.rift.ast.node;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class Body extends Node {
    public final List<Node> nodes;

    public Body(List<Node> Nodes, ParserRuleContext ctx) {
        super("body", ctx);
        this.nodes = Nodes;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> nodes;
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
