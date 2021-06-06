package me.waynee95.rift.scope;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.Constructor;
import me.waynee95.rift.ast.node.Let;
import me.waynee95.rift.ast.node.Match;
import me.waynee95.rift.ast.node.Record;
import me.waynee95.rift.ast.node.decl.*;
import me.waynee95.rift.ast.node.pattern.ConstructorPattern;
import me.waynee95.rift.ast.node.pattern.MatchCase;
import me.waynee95.rift.ast.node.reference.FuncCall;
import me.waynee95.rift.ast.node.reference.Name;
import me.waynee95.rift.ast.node.type.TCustom;
import me.waynee95.rift.ast.node.type.TypeLit;
import me.waynee95.rift.error.RiftException;

import java.util.Optional;

public class ScopeVisitor implements Visitor<Scope> {

    public void visit(Let node, Scope ctx) {
        Scope current = new Scope(ctx);
        visitOthers(node, current);
    }

    public void visit(VarDecl node, Scope ctx) {
        ctx.declare(node.id, node);
        visitOthers(node, ctx);
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

    public void visit(Record node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined record type: " + node.id, Optional.of(node));
        }
    }

    public void visit(EnumTypeDecl node, Scope ctx) {
        ctx.declare(node.id, node);
        for (VariantDecl variantDecl : node.constructors) {
            ctx.declare(variantDecl.id, variantDecl);
            visit(variantDecl, ctx);
        }
    }

    public void visit(VariantDecl node, Scope ctx) {
        for (TypeLit field : node.fields) {
            if (field instanceof TCustom) {
                visit((TCustom) field, ctx);
            }
        }
    }

    public void visit(Constructor node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined constructor: " + node.id, Optional.of(node));
        }
    }

    public void visit(TCustom node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined custom type: " + node.id, Optional.of(node));
        }
    }

    public void visit(Match node, Scope ctx) {
        for (MatchCase matchCase : node.cases) {
            var pattern = matchCase.pattern;
            if (pattern instanceof ConstructorPattern) {
                Optional<Decl> decl = ctx.lookup(((ConstructorPattern) pattern).id);
                if (decl.isEmpty()) {
                    throw new RiftException(
                            "Undefined constructor: " + ((ConstructorPattern) pattern).id,
                            Optional.of(node));
                }
            }
        }
    }
}

