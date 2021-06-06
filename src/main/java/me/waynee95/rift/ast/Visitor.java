package me.waynee95.rift.ast;

import me.waynee95.rift.ast.node.*;
import me.waynee95.rift.ast.node.Record;
import me.waynee95.rift.ast.node.decl.*;
import me.waynee95.rift.ast.node.literal.BoolLit;
import me.waynee95.rift.ast.node.literal.IntLit;
import me.waynee95.rift.ast.node.literal.Literal;
import me.waynee95.rift.ast.node.literal.StringLit;
import me.waynee95.rift.ast.node.pattern.*;
import me.waynee95.rift.ast.node.reference.FieldAccess;
import me.waynee95.rift.ast.node.reference.FuncCall;
import me.waynee95.rift.ast.node.reference.Index;
import me.waynee95.rift.ast.node.reference.Name;
import me.waynee95.rift.ast.node.type.*;

import java.util.List;
import java.util.Optional;

public interface Visitor<C> {

    default void visit(Program node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Literal node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(IntLit node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(BoolLit node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(StringLit node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Binary node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Unary node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Array node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Record node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Constructor node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Name node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(FuncCall node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(FieldAccess node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Index node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Assign node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(If node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Body node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(While node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Break node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Let node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(TInt node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(TBool node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(TString node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(TCustom node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(TArray node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(RecordTypeDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(EnumTypeDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(VariantDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(VarDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(FuncDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(ExternDecl node, C ctx) {
        visitOthers(node, ctx);
    }

    default void visit(Match node, C ctx) {
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
