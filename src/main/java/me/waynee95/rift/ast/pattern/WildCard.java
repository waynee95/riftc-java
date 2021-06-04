package me.waynee95.rift.ast.pattern;


import me.waynee95.rift.ast.Visitor;
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

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visit(this, ctx);
    }
}
