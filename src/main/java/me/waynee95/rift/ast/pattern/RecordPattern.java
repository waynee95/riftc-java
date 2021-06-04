package me.waynee95.rift.ast.pattern;

import me.waynee95.rift.ast.Tree;
import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class RecordPattern extends Pattern {
    public final List<Tree.VarDecl> fields;

    public RecordPattern(List<Tree.VarDecl> fields, ParserRuleContext context) {
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
