package me.waynee95.rift.ast.node.reference;

import me.waynee95.rift.ast.Node;
import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.RecordTypeDecl;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public class FieldAccess extends Node {
    public final Node location;
    public final String fieldName;
    public Optional<RecordTypeDecl> decl = Optional.empty();

    public FieldAccess(Node location, String fieldName, ParserRuleContext ctx) {
        super("field_access", ctx);
        this.location = location;
        this.fieldName = fieldName;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> location;
            case 1 -> fieldName;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitFieldAccess(this, ctx);
    }
}
