package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class While extends Expr {
    public final Expr cond;
    public final Body body;

    public While(Expr cond, Body body, ParserRuleContext ctx) {
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
    public boolean hasReturnValue() {
        return false;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitWhile(this, ctx);
    }
}
