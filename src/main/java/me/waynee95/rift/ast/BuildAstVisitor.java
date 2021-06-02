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
            return new Tree.IntLit(value, ctx);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Integer too big.");
        }
    }

    @Override
    public Node visitBoolLit(RiftParser.BoolLitContext ctx) {
        boolean value = Boolean.parseBoolean(ctx.BOOL_LIT().getText());
        return new Tree.BoolLit(value, ctx);
    }

    @Override
    public Node visitStringLit(RiftParser.StringLitContext ctx) {
        return new Tree.StringLit(ctx.STRING_LIT().getText(), ctx);
    }

    @Override
    public Node visitUnary(RiftParser.UnaryContext ctx) {
        var operand = visit(ctx.expr());
        return new Tree.Unary(ctx.op.getText(), operand, ctx);
    }

    @Override
    public Node visitBinary(RiftParser.BinaryContext ctx) {
        var lhs = visit(ctx.left);
        var rhs = visit(ctx.right);
        return new Tree.Binary(lhs, rhs, ctx.op.getText(), ctx);
    }

    @Override
    public Node visitParen(RiftParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Node visitArray(RiftParser.ArrayContext ctx) {
        List<Node> exprs = new ArrayList<>();
        for (var expr : ctx.expr()) {
            exprs.add((Node) visit(expr));
        }
        return new Tree.Array(exprs, ctx);
    }

    @Override
    public Node visitRecord(RiftParser.RecordContext ctx) {
        List<Node> params = new ArrayList<>();
        for (var expr : ctx.expr()) {
            params.add((Node) visit(expr));
        }
        return new Tree.Record(ctx.TYPE_ID().getText(), params, ctx);
    }

    @Override
    public Node visitName(RiftParser.NameContext ctx) {
        return new Tree.Name(ctx.ID().getText(), ctx);
    }

    @Override
    public Node visitFuncCall(RiftParser.FuncCallContext ctx) {
        List<Node> args = new ArrayList<>();
        for (RiftParser.ExprContext arg : ctx.expr()) {
            args.add((Node) visit(arg));
        }
        return new Tree.FuncCall(ctx.ID().getText(), args, ctx);
    }

    @Override
    public Node visitFieldAccess(RiftParser.FieldAccessContext ctx) {
        var location = visit(ctx.lvalue());
        return new Tree.FieldAccess(location, ctx.ID().getText(), ctx);
    }

    @Override
    public Node visitIndex(RiftParser.IndexContext ctx) {
        var location = visit(ctx.lvalue());
        var index = visit(ctx.expr());
        return new Tree.Index(location, index, ctx);
    }

    @Override
    public Node visitAssign(RiftParser.AssignContext ctx) {
        var lhs = visit(ctx.lvalue());
        var rhs = visit(ctx.expr());
        return new Tree.Assign(lhs, rhs, ctx);
    }

    @Override
    public Node visitIf(RiftParser.IfContext ctx) {
        var cond = visit(ctx.cond);
        var trueBranch = visit(ctx.trueBranch);
        Node falseBranch = null;
        if (ctx.falseBranch != null) {
            falseBranch = visit(ctx.falseBranch);
        }
        return new Tree.If(cond, trueBranch, Optional.ofNullable(falseBranch), ctx);
    }

    @Override
    public Node visitExprs(RiftParser.ExprsContext ctx) {
        List<Node> exprs = new ArrayList<>();
        // for (RiftParser.ExprContext expr : ctx.expr()) {
        // exprs.add((Node) visit(expr));
        // }
        return new Tree.Body(exprs, ctx);
    }

    @Override
    public Node visitWhile(RiftParser.WhileContext ctx) {
        var cond = visit(ctx.cond);
        var body = visit(ctx.exprs());
        return new Tree.While(cond, body, ctx);
    }

    @Override
    public Node visitBreak(RiftParser.BreakContext ctx) {
        return new Tree.Break(ctx);
    }

    @Override
    public Node visitLet(RiftParser.LetContext ctx) {
        List<Node> decls = new ArrayList<>();
        for (RiftParser.DeclContext decl : ctx.decl()) {
            decls.add((Node) visit(decl));
        }
        var body = visit(ctx.exprs());
        return new Tree.Let(decls, body, ctx);
    }

    @Override
    public Node visitType(RiftParser.TypeContext ctx) {
        if (ctx.INT() != null) {
            return new Tree.TInt(ctx);
        } else if (ctx.BOOL() != null) {
            return new Tree.TBool(ctx);
        } else if (ctx.STRING() != null) {
            return new Tree.TString(ctx);
        } else if (ctx.TYPE_ID() != null) {
            return new Tree.TCustom(ctx.TYPE_ID().getText(), ctx);
        } else {
            return new Tree.TArray((Tree.TypeLit) visit(ctx.type()), ctx);
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
