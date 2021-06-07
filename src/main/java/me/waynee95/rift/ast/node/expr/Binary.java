package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Operator;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Binary extends Expr {
    public final Operator.Op op;
    public final Expr left;
    public final Expr right;

    public Binary(Expr left, Expr right, Operator.Op op, ParserRuleContext ctx) {
        super("binary", ctx);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> op;
            case 1 -> left;
            case 2 -> right;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 3;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitBinary(this, ctx);
    }
}
