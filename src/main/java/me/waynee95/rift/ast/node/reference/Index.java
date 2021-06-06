package me.waynee95.rift.ast.node.reference;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.VarDecl;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public class Index extends Node {
    public final Node location;
    public final Node index;
    public Optional<VarDecl> decl = Optional.empty();

    public Index(Node location, Node index, ParserRuleContext ctx) {
        super("index", ctx);
        this.location = location;
        this.index = index;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> location;
            case 1 -> this.index;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visit(this, ctx);
    }
}
