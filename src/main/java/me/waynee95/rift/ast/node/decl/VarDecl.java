package me.waynee95.rift.ast.node.decl;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.expr.Expr;
import me.waynee95.rift.ast.node.type.TypeLit;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public class VarDecl extends Decl {
    public final Optional<Expr> value;
    public final Optional<TypeLit> typeLit;
    public final boolean immutable;

    public VarDecl(String id, Optional<Expr> value, Optional<TypeLit> typeLit, boolean immutable,
            ParserRuleContext ctx) {
        super(id, "var_decl", ctx);
        this.value = value;
        this.typeLit = typeLit;
        this.immutable = immutable;
    }

    public VarDecl(String id, Optional<TypeLit> typeLit, ParserRuleContext ctx) {
        super(id, "var_decl", ctx);
        this.value = Optional.empty();
        this.typeLit = typeLit;
        this.immutable = false;
    }

    public boolean hasValue() {
        return value.isPresent();
    }

    public boolean hasTypeSpecified() {
        return typeLit.isPresent();
    }

    public boolean isImmutable() {
        return immutable;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> id;
            case 1 -> value;
            case 2 -> typeLit;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 3;
    }

    @Override
    public <C> void accept(Visitor<C> v, C ctx) {
        v.visitVarDecl(this, ctx);
    }
}
