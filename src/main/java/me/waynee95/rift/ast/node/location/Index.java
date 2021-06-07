package me.waynee95.rift.ast.node.location;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public class Index extends Location {
    public final Node location;
    public final Node index;

    public Index(Node location, Node index, ParserRuleContext ctx) {
        super("index", ctx);
        this.location = location;
        this.index = index;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> location;
            case 1 -> this.index;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitIndex(this, ctx);
    }
}
