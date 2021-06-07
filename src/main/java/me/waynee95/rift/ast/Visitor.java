package me.waynee95.rift.ast;

import me.waynee95.rift.ast.node.Node;
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

import java.util.List;
import java.util.Optional;

public interface Visitor<C> {

    default void visitProgram(Program node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitIntLit(IntLit node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitBoolLit(BoolLit node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitStringLit(StringLit node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitBinary(Binary node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitUnary(Unary node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitArray(Array node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitRecord(Record node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitConstructor(Constructor node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitName(Name node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitFuncCall(FuncCall node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitFieldAccess(FieldAccess node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitIndex(Index node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitAssign(Assign node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitIf(If node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitBody(Body node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitWhile(While node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitBreak(Break node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitLet(Let node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitTInt(TInt node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitTBool(TBool node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitTString(TString node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitTCustom(TCustom node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitTArray(TArray node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitRecordTypeDecl(RecordTypeDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitEnumTypeDecl(EnumTypeDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitVariantDecl(VariantDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitVarDecl(VarDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitFuncDecl(FuncDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitExternDecl(ExternDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitMatch(Match node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitMatchCase(MatchCase node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitConstructorPattern(ConstructorPattern node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitRecordPattern(RecordPattern node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitValuePattern(ValuePattern node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitVariablePattern(VariablePattern node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitWildCard(WildCard node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitOthers(Node node, C ctx) {
        var iter = node.iterator();
        while (iter.hasNext()) {
            var child = iter.next();
            if (child instanceof Node) {
                ((Node) child).accept(this, ctx);
            }
            if (child instanceof Optional) {
                if (((Optional<Node>) child).isPresent()) {
                    ((Optional<Node>) child).get().accept(this, ctx);
                }
            } else if (child instanceof List) {
                for (Object n : ((List) child)) {
                    ((Node) n).accept(this, ctx);
                }
            }
        }
    }
}
