package me.waynee95.rift.ast.node.literal;

import org.antlr.v4.runtime.ParserRuleContext;

public class StringLit extends Literal<String> {
    public StringLit(String value, ParserRuleContext ctx) {
        super("string_lit", value, ctx);
    }
}
