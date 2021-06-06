package me.waynee95.rift.ast.node.decl;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.type.TypeLit;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class VariantDecl extends Decl {
    public final List<TypeLit> fields;

    public VariantDecl(String id, List<TypeLit> fields, ParserRuleContext ctx) {
        super(id, "variant_decl", ctx);
        this.fields = fields;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> fields;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 1;
    }

    @Override
    public String toString() {
        return displayName + "(" + id + ", " + fields + ")";
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitVariantDecl(this, ctx);
    }
}
