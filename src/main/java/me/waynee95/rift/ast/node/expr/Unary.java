package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Operator;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Unary extends Expr {
    public final Operator.Op op;
    public final Expr operand;

    public Unary(Operator.Op op, Expr operand, ParserRuleContext ctx) {
        super("unary", ctx);
        this.op = op;
        this.operand = operand;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> op;
            case 1 -> operand;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitUnary(this, ctx);
    }
}
