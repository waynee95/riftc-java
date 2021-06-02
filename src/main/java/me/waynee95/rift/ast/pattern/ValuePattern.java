package me.waynee95.rift.ast.pattern;

import me.waynee95.rift.ast.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public class ValuePattern extends Pattern {
    public final Node value;

    public ValuePattern(Node value, ParserRuleContext context) {
        super("value_pattern", context);
        this.value = value;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> value;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 1;
    }
}
