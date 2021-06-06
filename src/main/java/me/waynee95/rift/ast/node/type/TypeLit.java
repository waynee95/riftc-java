package me.waynee95.rift.ast.node.type;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.type.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class TypeLit extends Node {
    public Type type;

    public TypeLit(String displayName, ParserRuleContext ctx) {
        super(displayName, ctx);
    }
}
