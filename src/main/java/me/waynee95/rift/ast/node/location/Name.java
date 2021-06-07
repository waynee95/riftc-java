package me.waynee95.rift.ast.node.location;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Name extends Location {
    public final String id;

    public Name(String id, ParserRuleContext ctx) {
        super("name", ctx);
        this.id = id;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 1;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitName(this, ctx);
    }
}
