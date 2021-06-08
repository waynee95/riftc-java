package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.Node;
import me.waynee95.rift.ast.node.pattern.MatchCase;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class Match extends Expr {
    public final Node expr;
    public final List<MatchCase> matchCases;

    public Match(Node expr, List<MatchCase> matchCases, ParserRuleContext ctx) {
        super("match", ctx);
        this.expr = expr;
        this.matchCases = matchCases;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> expr;
            case 1 -> matchCases;
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
