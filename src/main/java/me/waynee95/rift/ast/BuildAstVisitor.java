package me.waynee95.rift.ast;

import me.waynee95.rift.parse.RiftParser;
import me.waynee95.rift.parse.RiftParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

public class BuildAstVisitor extends RiftParserBaseVisitor<Node> {
    @Override
    public Node visitProgram(RiftParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }

    @Override
    public Node visitIntLit(RiftParser.IntLitContext ctx) {
        try {
            long value = Long.parseLong(ctx.INT_LIT().getText());
            Position pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
            return new Tree.IntLit(value, pos);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Integer too big.");
        }
    }

    @Override
    public Node visitBoolLit(RiftParser.BoolLitContext ctx) {
        boolean value = Boolean.parseBoolean(ctx.BOOL_LIT().getText());
        Position pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.BoolLit(value, pos);
    }

    @Override
    public Node visitStringLit(RiftParser.StringLitContext ctx) {
        Position pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.StringLit(ctx.STRING_LIT().getText(), pos);
    }

    @Override
    public Node visitUnary(RiftParser.UnaryContext ctx) {
        Tree.Expr operand = (Tree.Expr) visit(ctx.expr());
        Position pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Unary(ctx.op.getText(), operand, pos);
    }

    @Override
    public Node visitBinary(RiftParser.BinaryContext ctx) {
        Tree.Expr lhs = (Tree.Expr) visit(ctx.left);
        Tree.Expr rhs = (Tree.Expr) visit(ctx.right);
        Position pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Binary(lhs, rhs, ctx.op.getText(), pos);
    }

    @Override
    public Node visitParen(RiftParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Node visitArray(RiftParser.ArrayContext ctx) {
        List<Tree.Expr> exprs = new ArrayList<>();
        for (RiftParser.ExprContext expr : ctx.expr()) {
            exprs.add((Tree.Expr) visit(expr));
        }
        Position pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Array(exprs, pos);
    }

    @Override
    public Node visitRecord(RiftParser.RecordContext ctx) {
        List<Tree.Expr> params = new ArrayList<>();
        for (RiftParser.ExprContext expr : ctx.expr()) {
            params.add((Tree.Expr) visit(expr));
        }
        Position pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Record(ctx.ID().getText(), params, pos);
    }
}
