package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.location.Location;
import org.antlr.v4.runtime.ParserRuleContext;

public class Assign extends Expr {
    public final Location lhs;
    public final Expr rhs;

    public Assign(Location lhs, Expr rhs, ParserRuleContext ctx) {
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
    public boolean hasReturnValue() {
        return false;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitAssign(this, ctx);
    }
}
