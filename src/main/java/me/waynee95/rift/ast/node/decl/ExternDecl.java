package me.waynee95.rift.ast.node.decl;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.type.TypeLit;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;
import java.util.Optional;

public class ExternDecl extends Decl {
    public List<VarDecl> params;
    public Optional<TypeLit> returnType;

    public ExternDecl(String id, List<VarDecl> params, Optional<TypeLit> returnType,
            ParserRuleContext ctx) {
        super(id, "extern_decl", ctx);
        this.params = params;
        this.returnType = returnType;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            case 1 -> params;
            case 2 -> returnType;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 3;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitExternDecl(this, ctx);
    }
}
