package me.waynee95.rift.typecheck;

import me.waynee95.rift.ast.Operator;
import me.waynee95.rift.ast.Visitor;
import me.waynee95.rift.ast.node.decl.FuncDecl;
import me.waynee95.rift.ast.node.decl.VarDecl;
import me.waynee95.rift.ast.node.expr.Binary;
import me.waynee95.rift.ast.node.expr.FuncCall;
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
import me.waynee95.rift.type.FuncType;
import me.waynee95.rift.type.Type;

import java.util.ArrayList;
import java.util.List;

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
    public void visitName(Name node, Scope ctx) {
        node.decl.ifPresent(decl -> decl.type.ifPresent(type -> node.setType(type)));
    }

    @Override
    public void visitUnary(Unary node, Scope ctx) {
        node.operand.accept(this, ctx);
        node.operand.type.orElseThrow(() -> new RiftException("Cannot determine type", node));

        var type = node.operand.type.get();
        if (!isCompatible(node.op, type)) {
            throw new RiftException("Type mismatch", node.operand);
        }

        node.setType(determineType(node.op));
    }

    @Override
    public void visitBinary(Binary node, Scope ctx) {
        node.left.accept(this, ctx);
        node.right.accept(this, ctx);
        node.left.type.orElseThrow(() -> new RiftException("Cannot determine type", node.left));
        node.right.type.orElseThrow(() -> new RiftException("Cannot determine type", node.right));

        var leftType = node.left.type.get();
        var rightType = node.right.type.get();
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

            value.type.orElseThrow(() -> new RiftException("Cannot determine type", value));
            var valueType = value.type.get();

            // If variable declaration has a type specified, check if value type == type specified
            if (node.hasTypeSpecified()) {
                node.typeLit.get().accept(this, ctx);
                var variableType = node.typeLit.get().type;

                if (variableType.isEmpty()) {
                    throw new RiftException("Cannot determine type", node.typeLit.get());
                }

                if (!valueType.eq(variableType.get())) {
                    throw new RiftException("Type mismatch", node);
                }
            }
            node.setType(valueType);
        } else {
            node.typeLit.get().accept(this, ctx);
            node.setType(node.typeLit.get().type.get());
        }
    }

    @Override
    public void visitFuncDecl(FuncDecl node, Scope ctx) {
        List<Type> paramTypes = new ArrayList<>();
        for (VarDecl param : node.params) {
            param.accept(this, ctx);
            param.type.ifPresentOrElse(type -> paramTypes.add(type),
                    () -> new RiftException("Cannot deterime type", param));
        }

        if (node.hasReturnTypeSpecified()) {
            node.returnType.get().accept(this, ctx);
            node.returnType.get().type.ifPresentOrElse(
                    returnType -> node.setType(new FuncType(paramTypes, returnType)),
                    () -> new RiftException("Cannot deterime type", node.returnType.get()));
        } else {
            node.setType(new FuncType(paramTypes));
        }

        node.body.accept(this, ctx);
    }

    @Override
    public void visitFuncCall(FuncCall node, Scope ctx) {
        var funcType = (FuncType) node.decl.get().type
                .orElseThrow(() -> new RiftException("Cannot determine type", node));

        if (funcType.arity() != node.args.size()) {
            throw new RiftException("Wrong number of arguments for: " + node.id, node);
        }

        for (int i = 0; i < node.args.size(); i++) {
            var arg = node.args.get(i);
            arg.accept(this, ctx);

            var argType =
                    arg.type.orElseThrow(() -> new RiftException("Cannot determine type", arg));

            if (!argType.eq(funcType.paramTypes.get(i))) {
                throw new RiftException("Type mismatch", arg);
            }
        }

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
