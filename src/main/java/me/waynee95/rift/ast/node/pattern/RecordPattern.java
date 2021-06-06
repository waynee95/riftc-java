package me.waynee95.rift.ast.node.pattern;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.VarDecl;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class RecordPattern extends Pattern {
    public final List<VarDecl> fields;

    public RecordPattern(List<VarDecl> fields, ParserRuleContext context) {
        super("record_pattern", context);
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
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visit(this, ctx);
    }
}
