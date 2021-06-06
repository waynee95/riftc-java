package me.waynee95.rift.ast.node;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.pattern.MatchCase;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class Match extends Node {
    public final Node expr;
    public final List<MatchCase> cases;

    public Match(Node expr, List<MatchCase> cases, ParserRuleContext ctx) {
        super("match", ctx);
        this.expr = expr;
        this.cases = cases;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> expr;
            case 1 -> cases;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitMatch(this, ctx);
    }
}
