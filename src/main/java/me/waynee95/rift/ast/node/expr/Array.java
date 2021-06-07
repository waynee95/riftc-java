package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class Array extends Expr {
    public final List<Expr> elems;

    public Array(List<Expr> elems, ParserRuleContext ctx) {
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
        v.visitArray(this, ctx);
    }
}
