package me.waynee95.rift.ast;

import me.waynee95.rift.ast.pattern.*;

import java.util.List;

public interface Visitor<C> {

    default void visit(Tree.Program node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Literal node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.IntLit node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.BoolLit node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.StringLit node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Binary node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Unary node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Array node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Record node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Constructor node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Name node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.FuncCall node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.FieldAccess node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Index node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Assign node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.If node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Body node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.While node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Break node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Let node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.TInt node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.TBool node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.TString node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.TCustom node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.TArray node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.RecordTypeDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.EnumTypeDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.VarDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.FuncDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.ExternDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Tree.Match node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(MatchCase node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(ConstructorPattern node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(RecordPattern node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(ValuePattern node, C ctx) {
        visitOthers(node, ctx);
    }


    default void visit(VariablePattern node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(WildCard node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visitOthers(Node node, C ctx) {
        var iter = node.iterator();
        while (iter.hasNext()) {
            var child = iter.next();
            if (child instanceof Node) {
                ((Node) child).accept(this, ctx);
            } else if (child instanceof List) {
                for (Object n : ((List) child)) {
                    ((Node) n).accept(this, ctx);
                }
            }
        }
    }
}
