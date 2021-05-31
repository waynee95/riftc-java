package me.waynee95.rift.ast;

import me.waynee95.rift.parse.RiftParser;
import me.waynee95.rift.parse.RiftParserBaseVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuildAstVisitor extends RiftParserBaseVisitor<Node> {
    @Override
    public Node visitProgram(RiftParser.ProgramContext ctx) {
        return super.visitProgram(ctx);
    }

    @Override
    public Node visitIntLit(RiftParser.IntLitContext ctx) {
        try {
            long value = Long.parseLong(ctx.INT_LIT().getText());
            var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
            return new Tree.IntLit(value, pos);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Integer too big.");
        }
    }

    @Override
    public Node visitBoolLit(RiftParser.BoolLitContext ctx) {
        boolean value = Boolean.parseBoolean(ctx.BOOL_LIT().getText());
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.BoolLit(value, pos);
    }

    @Override
    public Node visitStringLit(RiftParser.StringLitContext ctx) {
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.StringLit(ctx.STRING_LIT().getText(), pos);
    }

    @Override
    public Node visitUnary(RiftParser.UnaryContext ctx) {
        var operand = visit(ctx.expr());
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Unary(ctx.op.getText(), operand, pos);
    }

    @Override
    public Node visitBinary(RiftParser.BinaryContext ctx) {
        var lhs = visit(ctx.left);
        var rhs = visit(ctx.right);
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Binary(lhs, rhs, ctx.op.getText(), pos);
    }

    @Override
    public Node visitParen(RiftParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Node visitArray(RiftParser.ArrayContext ctx) {
        List<Node> exprs = new ArrayList<>();
        for (var expr : ctx.expr()) {
            exprs.add(visit(expr));
        }
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Array(exprs, pos);
    }

    @Override
    public Node visitRecord(RiftParser.RecordContext ctx) {
        List<Node> params = new ArrayList<>();
        for (var expr : ctx.expr()) {
            params.add(visit(expr));
        }
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Record(ctx.TYPE_ID().getText(), params, pos);
    }

    @Override
    public Node visitName(RiftParser.NameContext ctx) {
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Name(ctx.ID().getText(), pos);
    }

    @Override
    public Node visitFuncCall(RiftParser.FuncCallContext ctx) {
        List<Node> args = new ArrayList<>();
        for (RiftParser.ExprContext arg : ctx.expr()) {
            args.add(visit(arg));
        }
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.FuncCall(ctx.ID().getText(), args, pos);
    }

    @Override
    public Node visitFieldAccess(RiftParser.FieldAccessContext ctx) {
        var location = visit(ctx.lvalue());
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.FieldAccess(location, ctx.ID().getText(), pos);
    }

    @Override
    public Node visitIndex(RiftParser.IndexContext ctx) {
        var location = visit(ctx.lvalue());
        var index = visit(ctx.expr());
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Index(location, index, pos);
    }

    @Override
    public Node visitAssign(RiftParser.AssignContext ctx) {
        var lhs = visit(ctx.lvalue());
        var rhs = visit(ctx.expr());
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Assign(lhs, rhs, pos);
    }

    @Override
    public Node visitIf(RiftParser.IfContext ctx) {
        var cond = visit(ctx.cond);
        var trueBranch = visit(ctx.trueBranch);
        Node falseBranch = null;
        if (ctx.falseBranch != null) {
            falseBranch = visit(ctx.falseBranch);
        }
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.If(cond, trueBranch, Optional.ofNullable(falseBranch), pos);
    }

    @Override
    public Node visitExprs(RiftParser.ExprsContext ctx) {
        List<Node> exprs = new ArrayList<>();
        for (RiftParser.ExprContext expr : ctx.expr()) {
            exprs.add(visit(expr));
        }
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Body(exprs, pos);
    }

    @Override
    public Node visitWhile(RiftParser.WhileContext ctx) {
        var cond = visit(ctx.cond);
        var body = visit(ctx.exprs());
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.While(cond, body, pos);
    }

    @Override
    public Node visitBreak(RiftParser.BreakContext ctx) {
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Break(pos);
    }

    @Override
    public Node visitLet(RiftParser.LetContext ctx) {
        List<Node> decls = new ArrayList<>();
        for (RiftParser.DeclContext decl : ctx.decl()) {
            decls.add(visit(decl));
        }
        var body = visit(ctx.exprs());
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.Let(decls, body, pos);
    }

    @Override
    public Node visitType(RiftParser.TypeContext ctx) {
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        if (ctx.INT() != null) {
            return new Tree.TInt(pos);
        } else if (ctx.BOOL() != null) {
            return new Tree.TBool(pos);
        } else if (ctx.STRING() != null) {
            return new Tree.TString(pos);
        } else if (ctx.TYPE_ID() != null) {
            return new Tree.TCustom(pos);
        } else {
            return new Tree.TArray((Tree.TypeLit) visit(ctx.type()), pos);
        }
    }

    @Override
    public Node visitVarDecl(RiftParser.VarDeclContext ctx) {
        Tree.TypeLit type = ctx.type() != null ? (Tree.TypeLit) visit(ctx.type()) : null;
        var immutable = ctx.VAL() != null ? true : false;
        var value = visit(ctx.expr());
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.VarDecl(ctx.ID().getText(), Optional.of(value), Optional.ofNullable(type),
                immutable, pos);
    }

    @Override
    public Node visitFuncDecl(RiftParser.FuncDeclContext ctx) {
        List<Tree.VarDecl> params = new ArrayList<>();
        for (TerminalNode param : ctx.typefields().ID()) {
            var pos = new Position(param.getSymbol().getLine(),
                    param.getSymbol().getCharPositionInLine());
            Tree.TypeLit type = (Tree.TypeLit) visit(ctx.type());
            params.add(new Tree.VarDecl(param.getText(), Optional.of(type), pos));
        }
        Tree.TypeLit returnType = ctx.type() != null ? (Tree.TypeLit) visit(ctx.type()) : null;
        Node body = visit(ctx.exprs());
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.FuncDecl(ctx.ID().getText(), params, Optional.ofNullable(returnType), body,
                pos);
    }

    @Override
    public Node visitExternDecl(RiftParser.ExternDeclContext ctx) {
        List<Tree.VarDecl> params = new ArrayList<>();
        for (TerminalNode param : ctx.typefields().ID()) {
            var pos = new Position(param.getSymbol().getLine(),
                    param.getSymbol().getCharPositionInLine());
            Tree.TypeLit type = (Tree.TypeLit) visit(ctx.type());
            params.add(new Tree.VarDecl(param.getText(), Optional.of(type), pos));
        }
        Tree.TypeLit returnType = ctx.type() != null ? (Tree.TypeLit) visit(ctx.type()) : null;
        var pos = new Position(ctx.start.getLine(), ctx.start.getCharPositionInLine());
        return new Tree.ExternDecl(ctx.ID().getText(), params, Optional.ofNullable(returnType),
                pos);
    }
}
