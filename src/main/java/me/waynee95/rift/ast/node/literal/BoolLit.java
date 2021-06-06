package me.waynee95.rift.ast.node.literal;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class BoolLit extends Literal<Boolean> {
    public BoolLit(Boolean value, ParserRuleContext ctx) {
        super("bool_lit", value, ctx);
    }


    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitBoolLit(this, ctx);
    }
}
