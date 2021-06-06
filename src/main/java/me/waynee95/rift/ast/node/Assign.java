package me.waynee95.rift.ast.node;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Assign extends Node {
    public final Node lhs;
    public final Node rhs;

    public Assign(Node lhs, Node rhs, ParserRuleContext ctx) {
        super("assign", ctx);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> lhs;
            case 1 -> rhs;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitAssign(this, ctx);
    }
}
