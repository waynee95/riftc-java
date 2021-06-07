package me.waynee95.rift.typecheck;

import me.waynee95.rift.ast.Operator;
import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.VarDecl;
import me.waynee95.rift.ast.node.expr.Binary;
import me.waynee95.rift.ast.node.expr.Unary;
import me.waynee95.rift.ast.node.literal.BoolLit;
import me.waynee95.rift.ast.node.literal.IntLit;
import me.waynee95.rift.ast.node.literal.StringLit;
import me.waynee95.rift.ast.node.location.Name;
import me.waynee95.rift.ast.node.type.TBool;
import me.waynee95.rift.ast.node.type.TInt;
import me.waynee95.rift.ast.node.type.TString;
import me.waynee95.rift.error.RiftException;
import me.waynee95.rift.scope.Scope;
import me.waynee95.rift.type.DefaultType;
import me.waynee95.rift.type.Type;

public class TypeCheckVisitor implements Visitor<Scope> {
    @Override
    public void visitIntLit(IntLit node, Scope ctx) {
        node.setType(DefaultType.INT);
    }

    @Override
    public void visitBoolLit(BoolLit node, Scope ctx) {
        node.setType(DefaultType.BOOL);
    }

    @Override
    public void visitStringLit(StringLit node, Scope ctx) {
        node.setType(DefaultType.STRING);
    }

    @Override
    public void visitTInt(TInt node, Scope ctx) {
        node.setType(DefaultType.INT);
    }

    @Override
    public void visitTBool(TBool node, Scope ctx) {
        node.setType(DefaultType.BOOL);
    }

    @Override
    public void visitTString(TString node, Scope ctx) {
        node.setType(DefaultType.STRING);
    }

    @Override
    public void visitUnary(Unary node, Scope ctx) {
        node.operand.accept(this, ctx);

        if (node.operand.hasNoType()) {
            throw new RiftException("Cannot determine type", node.operand);
        }

        var type = node.operand.getType().get();
        if (!isCompatible(node.op, type)) {
            throw new RiftException("Type mismatch", node.operand);
        }

        node.setType(determineType(node.op));
    }

    @Override
    public void visitBinary(Binary node, Scope ctx) {
        node.left.accept(this, ctx);
        node.right.accept(this, ctx);

        if (node.left.hasNoType() || node.right.hasNoType()) {
            throw new RiftException("Cannot determine type", node);
        }

        var leftType = node.left.getType().get();
        var rightType = node.right.getType().get();
        if (!isCompatible(node.op, leftType, rightType)) {
            throw new RiftException("Type mismatch", node);
        }

        node.setType(determineType(node.op));
    }

    @Override
    public void visitVarDecl(VarDecl node, Scope ctx) {
        if (node.hasValue()) {
            var value = node.value.get();
            value.accept(this, ctx);

            if (value.hasNoType()) {
                throw new RiftException("Cannot determine type", value);
            }

            var valueType = value.getType().get();

            // If variable declaration has a type specified, check if value type == type specified
            if (node.hasTypeSpecified()) {
                node.typeLit.get().accept(this, ctx);
                var variableType = node.typeLit.get().getType();

                if (variableType.isEmpty()) {
                    throw new RiftException("Cannot determine type", node.typeLit.get());
                }

                if (!valueType.eq(variableType.get())) {
                    throw new RiftException("Type mismatch", node);
                }
            }

            node.setType(valueType);
        }
    }

    @Override
    public void visitName(Name node, Scope ctx) {
        node.setType(node.getDecl().get().getType().get());
    }

    private boolean isCompatible(Operator.Op op, Type type) {
        return switch (op) {
            case SUB -> type == DefaultType.INT;
            case NOT -> type == DefaultType.BOOL;
            default -> throw new IllegalArgumentException("Unexpected operator: " + op);
        };
    }

    private Type determineType(Operator.Op op) {
        return switch (op) {
            case ADD, SUB, MULT, DIV, REM -> DefaultType.INT;
            case AND, OR, NOT -> DefaultType.BOOL;
            default -> throw new IllegalArgumentException("Unexpected operator: " + op);
        };
    }

    private boolean isCompatible(Operator.Op op, Type leftType, Type rightType) {
        return switch (op) {
            case ADD, SUB, MULT, DIV, REM -> leftType == DefaultType.INT
                    && rightType == DefaultType.INT;
            case AND, OR -> leftType == DefaultType.BOOL && rightType == DefaultType.BOOL;
            default -> throw new IllegalArgumentException("Unexpected operator: " + op);
        };
    }
}
