package me.waynee95.rift.ast.node.literal;

import org.antlr.v4.runtime.ParserRuleContext;

public class BoolLit extends Literal<Boolean> {
    public BoolLit(Boolean value, ParserRuleContext ctx) {
        super("bool_lit", value, ctx);
    }
}
