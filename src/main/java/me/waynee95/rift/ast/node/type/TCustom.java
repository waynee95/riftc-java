package me.waynee95.rift.ast.node.type;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.Decl;
import me.waynee95.rift.ast.node.decl.VarDecl;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public class TCustom extends TypeLit {
    public final String id;
    private VarDecl decl;

    public TCustom(String id, ParserRuleContext ctx) {
        super("type_custom", ctx);
        this.id = id;
    }

    public Optional<Decl> getDecl() {
        return Optional.of(decl);
    }

    public void setDecl(VarDecl decl) {
        this.decl = decl;
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
        v.visitTCustom(this, ctx);
    }
}
