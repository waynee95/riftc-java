package me.waynee95.rift.scope;

import me.waynee95.rift.ast.Tree;
import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.error.RiftException;

import java.util.Optional;

public class ScopeVisitor implements Visitor<Scope> {

    public void visit(Tree.Let node, Scope ctx) {
        Scope current = new Scope(ctx);
        visitOthers(node, current);
    }

    public void visit(Tree.VarDecl node, Scope ctx) {
        ctx.declare(node.id, node);
    }

    public void visit(Tree.FuncDecl node, Scope ctx) {
        ctx.declare(node.id, node);
        Scope functionScope = new Scope(ctx);
        visitOthers(node, functionScope);
    }

    public void visit(Tree.RecordTypeDecl node, Scope ctx) {
        ctx.declare(node.id, node);
    }

    public void visit(Tree.Name node, Scope ctx) {
        Optional<Tree.Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined variable: " + node.id, node.getLine(),
                    node.getCol());
        }
    }

    public void visit(Tree.FuncCall node, Scope ctx) {
        Optional<Tree.Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined function: " + node.id, node.getLine(),
                    node.getCol());
        }
        visitOthers(node, ctx);
    }

    // TODO: Custom Types
    // TODO: Match
}

