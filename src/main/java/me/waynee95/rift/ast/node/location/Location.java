package me.waynee95.rift.ast.node.location;

import me.waynee95.rift.ast.node.decl.Decl;
import me.waynee95.rift.ast.node.expr.Expr;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public abstract class Location extends Expr {
    public Optional<Decl> decl = Optional.empty();

    public Location(String displayName, ParserRuleContext ctx) {
        super(displayName, ctx);
    }

    public void setDecl(Decl decl) {
        this.decl = Optional.of(decl);
    }
}
