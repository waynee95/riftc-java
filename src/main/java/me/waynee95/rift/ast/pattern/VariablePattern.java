package me.waynee95.rift.ast.pattern;

import me.waynee95.rift.ast.Tree;
import org.antlr.v4.runtime.ParserRuleContext;

public class VariablePattern extends Pattern {
    public final Tree.VarDecl id;

    public VariablePattern(Tree.VarDecl id, ParserRuleContext context) {
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
}
