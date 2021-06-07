package me.waynee95.rift.scope;

import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.*;
import me.waynee95.rift.ast.node.expr.Record;
import me.waynee95.rift.ast.node.expr.*;
import me.waynee95.rift.ast.node.location.Name;
import me.waynee95.rift.ast.node.pattern.*;
import me.waynee95.rift.ast.node.type.TCustom;
import me.waynee95.rift.ast.node.type.TypeLit;
import me.waynee95.rift.error.RiftException;

import java.util.List;
import java.util.Optional;

public class ScopeVisitor implements Visitor<Scope> {

    @Override
    public void visitLet(Let node, Scope ctx) {
        Scope current = new Scope(ctx);
        visitOthers(node, current);
    }

    @Override
    public void visitVarDecl(VarDecl node, Scope ctx) {
        ctx.declare(node.id, node);
        visitOthers(node, ctx);
    }

    @Override
    public void visitFuncDecl(FuncDecl node, Scope ctx) {
        ctx.declare(node.id, node);
        Scope functionScope = new Scope(ctx);
        visitOthers(node, functionScope);
    }

    @Override
    public void visitExternDecl(ExternDecl node, Scope ctx) {
        ctx.declare(node.id, node);
    }

    @Override
    public void visitRecordTypeDecl(RecordTypeDecl node, Scope ctx) {
        ctx.declare(node.id, node);
    }

    @Override
    public void visitName(Name node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined variable: " + node.id, node);
        }
        node.setDecl(decl.get());
    }

    @Override
    public void visitFuncCall(FuncCall node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined function: " + node.id, node);
        }
        visitOthers(node, ctx);
    }

    @Override
    public void visitRecord(Record node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined record type: " + node.id, node);
        }
    }

    @Override
    public void visitEnumTypeDecl(EnumTypeDecl node, Scope ctx) {
        ctx.declare(node.id, node);
        for (VariantDecl variantDecl : node.constructors) {
            ctx.declare(variantDecl.id, variantDecl);
            visitVariantDecl(variantDecl, ctx);
        }
    }

    @Override
    public void visitVariantDecl(VariantDecl node, Scope ctx) {
        for (TypeLit field : node.fields) {
            if (field instanceof TCustom) {
                field.accept(this, ctx);
            }
        }
    }

    @Override
    public void visitConstructor(Constructor node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined constructor: " + node.id, node);
        }
    }

    @Override
    public void visitTCustom(TCustom node, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(node.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined custom type: " + node.id, node);
        }
    }

    @Override
    public void visitMatch(Match node, Scope ctx) {
        for (MatchCase matchCase : node.cases) {
            matchCase.accept(this, ctx);
        }
    }

    @Override
    public void visitMatchCase(MatchCase node, Scope ctx) {
        Scope matchCaseScope = new Scope(ctx);
        node.pattern.accept(this, ctx);
        node.body.accept(this, ctx);
    }

    @Override
    public void visitConstructorPattern(ConstructorPattern pattern, Scope ctx) {
        Optional<Decl> decl = ctx.lookup(pattern.id);
        if (decl.isEmpty()) {
            throw new RiftException("Undefined constructor: " + pattern.id, pattern);
        }
        for (Pattern field : pattern.fields) {
            field.accept(this, ctx);
        }
    }

    @Override
    public void visitRecordPattern(RecordPattern pattern, Scope ctx) {
        List<VarDecl> fields = pattern.fields;
        for (VarDecl field : fields) {
            ctx.declare(field.id, field);
        }
    }

    @Override
    public void visitVariablePattern(VariablePattern node, Scope ctx) {
        ctx.declare(node.name.id, node.name);
    }
}

