package me.waynee95.rift.ast.pattern;

import me.waynee95.rift.ast.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public class MatchCase extends Node {
    public final Pattern pattern;
    public final Node body;

    public MatchCase(Pattern pattern, Node body, ParserRuleContext context) {
        super("match_case", context);
        this.pattern = pattern;
        this.body = body;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> pattern;
            case 1 -> body;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }
}
