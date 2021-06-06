package me.waynee95.rift.ast.node.pattern;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.VarDecl;
import org.antlr.v4.runtime.ParserRuleContext;

public class VariablePattern extends Pattern {
    public final VarDecl name;

    public VariablePattern(VarDecl name, ParserRuleContext context) {
        super("var_pattern", context);
        this.name = name;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> name;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 1;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitVariablePattern(this, ctx);
    }
}
