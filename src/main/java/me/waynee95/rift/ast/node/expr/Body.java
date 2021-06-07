package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class Body extends Expr {
    public final List<Expr> exprs;

    public Body(List<Expr> exprs, ParserRuleContext ctx) {
        super("body", ctx);
        this.exprs = exprs;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> exprs;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 1;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitBody(this, ctx);
    }
}
