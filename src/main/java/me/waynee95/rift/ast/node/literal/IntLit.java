package me.waynee95.rift.ast.node.literal;

import org.antlr.v4.runtime.ParserRuleContext;

public class IntLit extends Literal<Long> {
    public IntLit(Long value, ParserRuleContext ctx) {
        super("int_lit", value, ctx);
    }
}
