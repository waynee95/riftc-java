package me.waynee95.rift.ast.node;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class While extends Node {
    public final Node cond;
    public final Node body;

    public While(Node cond, Node body, ParserRuleContext ctx) {
        super("while", ctx);
        this.cond = cond;
        this.body = body;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> cond;
            case 1 -> body;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visit(this, ctx);
    }
}
