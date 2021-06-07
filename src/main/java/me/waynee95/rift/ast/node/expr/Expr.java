package me.waynee95.rift.ast.node.expr;

import me.waynee95.rift.ast.node.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Expr extends Node {
    public Expr(String displayName, ParserRuleContext ctx) {
        super(displayName, ctx);
    }

    public boolean hasReturnValue() {
        return true;
    }
}
