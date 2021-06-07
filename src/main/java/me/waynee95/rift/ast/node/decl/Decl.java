package me.waynee95.rift.ast.node.decl;

import me.waynee95.rift.ast.node.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Decl extends Node {
    public final String id;

    public boolean isVar() {
        return false;
    }

    public Decl(String id, String displayName, ParserRuleContext ctx) {
        super(displayName, ctx);
        this.id = id;
    }
}
