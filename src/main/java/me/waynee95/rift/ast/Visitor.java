package me.waynee95.rift.ast;

import me.waynee95.rift.ast.node.Program;
import me.waynee95.rift.ast.node.decl.*;
import me.waynee95.rift.ast.node.expr.Record;
import me.waynee95.rift.ast.node.expr.*;
import me.waynee95.rift.ast.node.literal.BoolLit;
import me.waynee95.rift.ast.node.literal.IntLit;
import me.waynee95.rift.ast.node.literal.StringLit;
import me.waynee95.rift.ast.node.location.FieldAccess;
import me.waynee95.rift.ast.node.location.Index;
import me.waynee95.rift.ast.node.location.Name;
import me.waynee95.rift.ast.node.pattern.*;
import me.waynee95.rift.ast.node.type.*;

public interface Visitor<C> {

    default void visitProgram(Program node, C ctx) {
        node.expr.accept(this, ctx);
    }

    default void visitIntLit(IntLit node, C ctx) {
        // Do nothing
    }

    default void visitBoolLit(BoolLit node, C ctx) {
        // Do nothing
    }

    default void visitStringLit(StringLit node, C ctx) {
        // Do nothing
    }

    default void visitBinary(Binary node, C ctx) {
        node.left.accept(this, ctx);
        node.right.accept(this, ctx);
    }

    default void visitUnary(Unary node, C ctx) {
        node.operand.accept(this, ctx);
    }

    default void visitArray(Array node, C ctx) {
        node.elems.forEach(elem -> elem.accept(this, ctx));
    }

    default void visitRecord(Record node, C ctx) {
        node.params.forEach(param -> param.accept(this, ctx));
    }

    default void visitConstructor(Constructor node, C ctx) {
        node.params.forEach(param -> param.accept(this, ctx));
    }

    default void visitName(Name node, C ctx) {
        // Do nothing
    }

    default void visitFuncCall(FuncCall node, C ctx) {
        node.args.forEach(arg -> arg.accept(this, ctx));
    }

    default void visitFieldAccess(FieldAccess node, C ctx) {
        node.location.accept(this, ctx);
    }

    default void visitIndex(Index node, C ctx) {
        node.location.accept(this, ctx);
        node.index.accept(this, ctx);
    }

    default void visitAssign(Assign node, C ctx) {
        node.lhs.accept(this, ctx);
        node.rhs.accept(this, ctx);
    }

    default void visitIf(If node, C ctx) {
        node.cond.accept(this, ctx);
        node.trueBranch.accept(this, ctx);
        if (node.hasFalseBranch()) {
            node.falseBranch.get().accept(this, ctx);
        }
    }

    default void visitBody(Body node, C ctx) {
        node.exprs.forEach(expr -> expr.accept(this, ctx));
    }

    default void visitWhile(While node, C ctx) {
        node.cond.accept(this, ctx);
        node.body.accept(this, ctx);
    }

    default void visitBreak(Break node, C ctx) {
        // Do nothing
    }

    default void visitLet(Let node, C ctx) {
        node.decls.forEach(decl -> decl.accept(this, ctx));
        node.body.accept(this, ctx);
    }

    default void visitTInt(TInt node, C ctx) {
        // Do nothing
    }

    default void visitTBool(TBool node, C ctx) {
        // Do nothing
    }

    default void visitTString(TString node, C ctx) {
        // Do nothing
    }

    default void visitTCustom(TCustom node, C ctx) {
        // Do nothing
    }

    default void visitTArray(TArray node, C ctx) {
        node.elemType.accept(this, ctx);
    }

    default void visitRecordTypeDecl(RecordTypeDecl node, C ctx) {
        node.fields.forEach(field -> field.b.accept(this, ctx));
    }

    default void visitEnumTypeDecl(EnumTypeDecl node, C ctx) {
        node.constructors.forEach(constructor -> constructor.accept(this, ctx));
    }

    default void visitVariantDecl(VariantDecl node, C ctx) {
        node.fields.forEach(field -> field.accept(this, ctx));
    }

    default void visitVarDecl(VarDecl node, C ctx) {
        if (node.hasValue()) {
            node.value.get().accept(this, ctx);
        }
        if (node.hasTypeSpecified()) {
            node.typeLit.get().accept(this, ctx);
        }
    }

    default void visitFuncDecl(FuncDecl node, C ctx) {
        node.params.forEach(param -> param.accept(this, ctx));
        if (node.hasReturnTypeSpecified()) {
            node.returnType.get().accept(this, ctx);
        }
        node.body.accept(this, ctx);
    }

    default void visitExternDecl(ExternDecl node, C ctx) {
        node.params.forEach(param -> param.accept(this, ctx));
        if (node.hasReturnTypeSpecified()) {
            node.returnType.get().accept(this, ctx);
        }
    }

    default void visitMatch(Match node, C ctx) {
        node.expr.accept(this, ctx);
        node.matchCases.forEach(matchCase -> matchCase.accept(this, ctx));
    }

    default void visitMatchCase(MatchCase node, C ctx) {
        node.pattern.accept(this, ctx);
        node.body.accept(this, ctx);
    }

    default void visitConstructorPattern(ConstructorPattern node, C ctx) {
        node.fields.forEach(field -> field.accept(this, ctx));
    }

    default void visitRecordPattern(RecordPattern node, C ctx) {
        node.fields.forEach(field -> field.accept(this, ctx));
    }

    default void visitValuePattern(ValuePattern node, C ctx) {
        node.value.accept(this, ctx);
    }

    default void visitVariablePattern(VariablePattern node, C ctx) {
        node.name.accept(this, ctx);
    }

    default void visitWildCard(WildCard node, C ctx) {
        // Do nothing
    }
}
