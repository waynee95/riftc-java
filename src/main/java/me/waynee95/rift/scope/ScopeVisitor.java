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

import java.util.Optional;

public class ScopeVisitor implements Visitor<Scope> {

    @Override
    public void visitLet(Let node, Scope ctx) {
        Scope current = new Scope(ctx);
        node.decls.forEach(decl -> decl.accept(this, current));
        node.body.accept(this, current);
    }

    @Override
    public void visitVarDecl(VarDecl node, Scope ctx) {
        ctx.declare(node.id, node);
        node.value.ifPresent(value -> value.accept(this, ctx));
        node.typeLit.ifPresent(type -> type.accept(this, ctx));
    }

    @Override
    public void visitFuncDecl(FuncDecl node, Scope ctx) {
        ctx.declare(node.id, node);
        Scope functionScope = new Scope(ctx);
        node.params.forEach(param -> param.accept(this, functionScope));
        node.returnType.ifPresent(returnType -> returnType.accept(this, functionScope));
        node.body.accept(this, functionScope);
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
        Decl decl = ctx.lookup(node.id)
                .orElseThrow(() -> new RiftException("Undefined variable: " + node.id, node));
        node.setDecl(decl);
    }

    @Override
    public void visitFuncCall(FuncCall node, Scope ctx) {
        Decl decl = ctx.lookup(node.id)
                .orElseThrow(() -> new RiftException("Undefined function: " + node.id, node));
        node.args.forEach(arg -> arg.accept(this, ctx));
        node.setDecl(decl);
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
        node.constructors.forEach(variantDecl -> {
            ctx.declare(variantDecl.id, variantDecl);
            variantDecl.accept(this, ctx);
        });
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
        ctx.lookup(node.id)
                .orElseThrow(() -> new RiftException("Undefined constructor: " + node.id, node));
    }

    @Override
    public void visitTCustom(TCustom node, Scope ctx) {
        ctx.lookup(node.id)
                .orElseThrow(() -> new RiftException("Undefined custom type: " + node.id, node));
    }

    @Override
    public void visitMatch(Match node, Scope ctx) {
        node.matchCases.forEach(matchCase -> matchCase.accept(this, ctx));
    }

    @Override
    public void visitMatchCase(MatchCase node, Scope ctx) {
        Scope matchCaseScope = new Scope(ctx);
        node.pattern.accept(this, matchCaseScope);
        node.body.accept(this, matchCaseScope);
    }

    @Override
    public void visitConstructorPattern(ConstructorPattern pattern, Scope ctx) {
        ctx.lookup(pattern.id).orElseThrow(
                () -> new RiftException("Undefined constructor: " + pattern.id, pattern));
        pattern.fields.forEach(field -> field.accept(this, ctx));
    }

    @Override
    public void visitRecordPattern(RecordPattern pattern, Scope ctx) {
        pattern.fields.forEach(field -> ctx.declare(field.id, field));
    }

    @Override
    public void visitVariablePattern(VariablePattern node, Scope ctx) {
        ctx.declare(node.name.id, node.name);
    }
}

