package me.waynee95.rift.ast.node.type;

import me.waynee95.rift.ast.node.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class TypeLit extends Node {
    public TypeLit(String displayName, ParserRuleContext ctx) {
        super(displayName, ctx);
    }
}
