package me.waynee95.rift.ast.node.pattern;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.VarDecl;
import org.antlr.v4.runtime.ParserRuleContext;

public class VariablePattern extends Pattern {
    public final VarDecl id;

    public VariablePattern(VarDecl id, ParserRuleContext context) {
        super("var_pattern", context);
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
