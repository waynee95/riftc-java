package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.Decl;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;
import java.util.Optional;

public class FuncCall extends Expr {
    public final String id;
    public final List<Expr> args;
    public Optional<Decl> decl = Optional.empty();

    public FuncCall(String id, List<Expr> args, ParserRuleContext ctx) {
        super("call", ctx);
        this.id = id;
        this.args = args;
    }

    public void setDecl(Decl decl) {
        this.decl = Optional.of(decl);
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
