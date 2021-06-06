package me.waynee95.rift.ast.node.decl;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.type.Type;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public abstract class Decl extends Node {
    public final String id;
    public Optional<Type> type = Optional.empty();

    public Decl(String id, String displayName, ParserRuleContext ctx) {
        super(displayName, ctx);
        this.id = id;
    }
}
