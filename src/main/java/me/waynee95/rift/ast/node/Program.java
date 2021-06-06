package me.waynee95.rift.ast.node;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Program extends Node {
    public final Node expr;

    public Program(Node expr, ParserRuleContext ctx) {
        super("prog", ctx);
        this.expr = expr;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> expr;
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
