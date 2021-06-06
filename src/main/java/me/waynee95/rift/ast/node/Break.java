package me.waynee95.rift.ast.node;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class Break extends Node {
    public Break(ParserRuleContext ctx) {
        super("break", ctx);
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
        v.visitBreak(this, ctx);
    }
}
