package me.waynee95.rift.ast.node.pattern;


import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class ConstructorPattern extends Pattern {
    public final String id;
    public final List<Pattern> fields;

    public ConstructorPattern(String id, List<Pattern> fields, ParserRuleContext context) {
        super("constructor_pattern", context);
        this.id = id;
        this.fields = fields;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            case 1 -> fields;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visit(this, ctx);
    }
}
