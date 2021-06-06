package me.waynee95.rift.ast.node;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class Constructor extends Node {
    public final String id;
    public final List<Node> params;


    public Constructor(String id, List<Node> params, ParserRuleContext ctx) {
        super("constructor", ctx);
        this.id = id;
        this.params = params;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            case 1 -> params;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitConstructor(this, ctx);
    }
}
