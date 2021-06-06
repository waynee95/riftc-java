package me.waynee95.rift.ast.node.reference;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.VarDecl;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public class Name extends Node {
    public final String id;
    public Optional<VarDecl> decl = Optional.empty();

    public Name(String id, ParserRuleContext ctx) {
        super("name", ctx);
        this.id = id;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 1;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visit(this, ctx);
    }
}
