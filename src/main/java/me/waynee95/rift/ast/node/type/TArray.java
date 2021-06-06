package me.waynee95.rift.ast.node.type;

import me.waynee95.rift.ast.Visitor;
import org.antlr.v4.runtime.ParserRuleContext;

public class TArray extends TypeLit {
    public final TypeLit elemType;

    public TArray(TypeLit elemType, ParserRuleContext ctx) {
        super("type_array", ctx);
        this.elemType = elemType;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> elemType;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 1;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitTArray(this, ctx);
    }
}
