package me.waynee95.rift.ast.node;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public class If extends Node {
    public final Node cond;
    public final Node trueBranch;
    public final Optional<Node> falseBranch;

    public If(Node cond, Node trueBranch, Optional<Node> falseBranch, ParserRuleContext ctx) {
        super("if", ctx);
        this.cond = cond;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> cond;
            case 1 -> trueBranch;
            case 2 -> falseBranch;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 3;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitIf(this, ctx);
    }
}
