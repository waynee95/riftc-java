package me.waynee95.rift.ast.node.decl;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.type.TypeLit;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class RecordTypeDecl extends Decl {
    public final List<Pair<String, TypeLit>> fields;

    public RecordTypeDecl(String id, List<Pair<String, TypeLit>> fields, ParserRuleContext ctx) {
        super(id, "record_type_decl", ctx);
        this.fields = fields;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            case 1 -> fields;
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
