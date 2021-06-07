package me.waynee95.rift.ast.node.pattern;

import me.waynee95.rift.ast.node.Node;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Pattern extends Node {

    public Pattern(String displayName, ParserRuleContext context) {
        super(displayName, context);
    }

}
