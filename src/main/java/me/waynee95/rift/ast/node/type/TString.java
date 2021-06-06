package me.waynee95.rift.ast.node.type;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class TString extends TypeLit {
    public TString(ParserRuleContext ctx) {
        super("type_string", ctx);
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
