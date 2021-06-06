package me.waynee95.rift.ast.node.decl;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class EnumTypeDecl extends Decl {
    public final List<VariantDecl> constructors;

    public EnumTypeDecl(String id, List<VariantDecl> constructors, ParserRuleContext ctx) {
        super(id, "enum_type_decl", ctx);
        this.constructors = constructors;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            case 1 -> constructors;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitEnumTypeDecl(this, ctx);
    }
}
