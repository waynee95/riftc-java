package me.waynee95.rift.ast.pattern;


import org.antlr.v4.runtime.ParserRuleContext;

public class WildCard extends Pattern {

    public WildCard(ParserRuleContext context) {
        super("else_pattern", context);
    }

    @Override
    public Object getChild(int index) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public int childCount() {
        return 0;
    }
}
