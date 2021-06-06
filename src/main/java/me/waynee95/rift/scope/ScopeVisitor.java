package me.waynee95.rift.scope;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.Let;
import me.waynee95.rift.ast.node.decl.Decl;
import me.waynee95.rift.ast.node.decl.FuncDecl;
import me.waynee95.rift.ast.node.decl.RecordTypeDecl;
import me.waynee95.rift.ast.node.decl.VarDecl;
import me.waynee95.rift.ast.node.reference.FuncCall;
import me.waynee95.rift.ast.node.reference.Name;
import me.waynee95.rift.error.RiftException;

import java.util.Optional;

public class ScopeVisitor implements Visitor<Scope> {

    public void visit(Let node, Scope ctx) {
        Scope current = new Scope(ctx);
        visitOthers(node, current);
    }

    public void visit(VarDecl node, Scope ctx) {
        ctx.declare(node.id, node);
    }

    public void visit(FuncDecl node, Scope ctx) {
        ctx.declare(node.id, node);
        Scope functionScope = new Scope(ctx);
        visitOthers(node, functionScope);
    }

    public void visit(RecordTypeDecl node, Scope ctx) {
        ctx.declare(node.id, node);
    }

    public void visit(Name node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined variable: " + node.id, Optional.of(node));
        }
    }

    public void visit(FuncCall node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined function: " + node.id, Optional.of(node));
        }
        visitOthers(node, ctx);
    }

    // TODO: Custom Types
    // TODO: Match
}

