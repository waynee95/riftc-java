package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class FuncCall extends Expr {
    public final String id;
    public final List<Expr> args;

    public FuncCall(String id, List<Expr> args, ParserRuleContext ctx) {
        super("call", ctx);
        this.id = id;
        this.args = args;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            case 1 -> args;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitFuncCall(this, ctx);
    }
}
