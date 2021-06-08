package me.waynee95.rift.ast.node.decl;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.Node;
import me.waynee95.rift.ast.node.type.TypeLit;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;
import java.util.Optional;

public class FuncDecl extends Decl {
    public final List<VarDecl> params;
    public final Optional<TypeLit> returnType;
    public final Node body;

    public FuncDecl(String id, List<VarDecl> params, Optional<TypeLit> returnType, Node body,
            ParserRuleContext ctx) {
        super(id, "func_decl", ctx);
        this.params = params;
        this.returnType = returnType;
        this.body = body;
    }

    public boolean hasReturnTypeSpecified() {
        return returnType.isPresent();
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            case 1 -> params;
            case 2 -> returnType;
            case 3 -> body;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 4;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitFuncDecl(this, ctx);
    }
}
