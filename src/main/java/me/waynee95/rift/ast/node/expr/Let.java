package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.Node;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class Let extends Expr {
    public final List<Node> decls;
    public final Node body;

    public Let(List<Node> decls, Node body, ParserRuleContext ctx) {
        super("let", ctx);
        this.decls = decls;
        this.body = body;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> decls;
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
        v.visitLet(this, ctx);
    }
}
