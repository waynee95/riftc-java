package me.waynee95.rift.ast.node.literal;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class StringLit extends Literal<String> {
    public StringLit(String value, ParserRuleContext ctx) {
        super("string_lit", value, ctx);
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitStringLit(this, ctx);
    }
}
