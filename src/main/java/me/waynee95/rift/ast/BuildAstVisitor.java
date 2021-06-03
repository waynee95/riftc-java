package me.waynee95.rift.ast;

import me.waynee95.rift.ast.pattern.*;
import me.waynee95.rift.parse.RiftParser;
import me.waynee95.rift.parse.RiftParserBaseVisitor;
import org.antlr.v4.runtime.misc.Pair;
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
        var op = Operator.fromString(ctx.op.getText());
        return new Tree.Unary(op, operand, ctx);
    }

    @Override
    public Node visitBinary(RiftParser.BinaryContext ctx) {
        var lhs = visit(ctx.left);
        var rhs = visit(ctx.right);
        var op = Operator.fromString(ctx.op.getText());
        return new Tree.Binary(lhs, rhs, op, ctx);
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
        return new Tree.Array(exprs, ctx);
    }

    @Override
    public Node visitRecord(RiftParser.RecordContext ctx) {
        List<Node> params = new ArrayList<>();
        for (var expr : ctx.expr()) {
            params.add(visit(expr));
        }
        return new Tree.Record(ctx.TYPE_ID().getText(), params, ctx);
    }

    @Override
    public Node visitCons(RiftParser.ConsContext ctx) {
        List<Node> params = new ArrayList<>();
        for (var expr : ctx.expr()) {
            params.add(visit(expr));
        }
        return new Tree.Constructor(ctx.TYPE_ID().getText(), params, ctx);
    }

    @Override
    public Node visitName(RiftParser.NameContext ctx) {
        return new Tree.Name(ctx.ID().getText(), ctx);
    }

    @Override
    public Node visitFuncCall(RiftParser.FuncCallContext ctx) {
        List<Node> args = new ArrayList<>();
        for (RiftParser.ExprContext arg : ctx.expr()) {
            args.add(visit(arg));
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
        for (RiftParser.ExprContext expr : ctx.expr()) {
            exprs.add(visit(expr));
        }
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

        return new Tree.VarDecl(ctx.ID().getText(), Optional.of(value), Optional.ofNullable(type),
                immutable, ctx);
    }

    @Override
    public Node visitFuncDecl(RiftParser.FuncDeclContext ctx) {
        List<Tree.VarDecl> params = new ArrayList<>();
        var id = ctx.ID().getText();
        var body = visit(ctx.exprs());

        Tree.TypeLit returnType = null;
        if (ctx.type() != null) {
            returnType = (Tree.TypeLit) visit(ctx.type());
        }

        for (int i = 0; i < ctx.typefields().ID().size(); i++) {
            var param = ctx.typefields().ID(i).getText();
            Tree.TypeLit type = (Tree.TypeLit) visit(ctx.typefields().type(i));
            params.add(new Tree.VarDecl(param, Optional.of(type), ctx));
        }

        return new Tree.FuncDecl(id, params, Optional.ofNullable(returnType), body, ctx);
    }

    @Override
    public Node visitExternDecl(RiftParser.ExternDeclContext ctx) {
        List<Tree.VarDecl> params = new ArrayList<>();
        var id = ctx.ID().getText();

        Tree.TypeLit returnType = null;
        if (ctx.type() != null) {
            returnType = (Tree.TypeLit) visit(ctx.type());
        }

        for (int i = 0; i < ctx.typefields().ID().size(); i++) {
            var param = ctx.typefields().ID(i).getText();
            Tree.TypeLit type = (Tree.TypeLit) visit(ctx.typefields().type(i));
            params.add(new Tree.VarDecl(param, Optional.of(type), ctx));
        }

        return new Tree.ExternDecl(id, params, Optional.ofNullable(returnType), ctx);
    }

    @Override
    public Node visitTypeDecl(RiftParser.TypeDeclContext ctx) {
        var id = ctx.TYPE_ID().getText();

        if (ctx.typedec().LCURLY() != null) {
            List<Pair<String, Tree.TypeLit>> fields = new ArrayList<>();

            for (int i = 0; i < ctx.typedec().typefields().ID().size(); i++) {
                var param = ctx.typedec().typefields().ID(i).getText();
                Tree.TypeLit type = (Tree.TypeLit) visit(ctx.typedec().typefields().type(i));
                fields.add(new Pair<>(param, type));
            }

            return new Tree.RecordTypeDecl(id, fields, ctx);
        } else {
            List<Pair<String, List<Tree.TypeLit>>> constructors = new ArrayList<>();

            for (RiftParser.ConstructorContext constructor : ctx.typedec().constructor()) {
                List<Tree.TypeLit> types = new ArrayList<>();
                for (RiftParser.TypeContext type : constructor.type()) {
                    types.add((Tree.TypeLit) visit(type));
                }
                constructors.add(new Pair<>(constructor.TYPE_ID().getText(), types));
            }

            return new Tree.EnumTypeDecl(id, constructors, ctx);
        }
    }

    @Override
    public Node visitLiteralPattern(RiftParser.LiteralPatternContext ctx) {
        return new ValuePattern(visit(ctx.literal()), ctx);
    }

    @Override
    public Node visitConstructorPattern(RiftParser.ConstructorPatternContext ctx) {
        List<Pattern> fields = new ArrayList<>();
        var id = ctx.TYPE_ID().getText();

        if (ctx.LPAREN() != null) {
            for (RiftParser.PatternContext pattern : ctx.pattern()) {
                fields.add((Pattern) visit(pattern));
            }
        }

        return new ConstructorPattern(id, fields, ctx);
    }

    @Override
    public Node visitRecordPattern(RiftParser.RecordPatternContext ctx) {
        List<Tree.VarDecl> fields = new ArrayList<>();
        for (TerminalNode fieldName : ctx.ID()) {
            fields.add(new Tree.VarDecl(fieldName.getText(), Optional.empty(), ctx));
        }
        return new RecordPattern(fields, ctx);
    }

    @Override
    public Node visitVariablePattern(RiftParser.VariablePatternContext ctx) {
        return new VariablePattern(new Tree.VarDecl(ctx.ID().getText(), Optional.empty(), ctx),
                ctx);
    }

    @Override
    public Node visitMatch(RiftParser.MatchContext ctx) {
        List<MatchCase> cases = new ArrayList<>();
        var expr = visit(ctx.expr());

        for (int i = 0; i < ctx.matchcases().pattern().size(); i++) {
            var pattern = visit(ctx.matchcases().pattern(i));
            var body = visit(ctx.matchcases().exprs(i));
            cases.add(new MatchCase((Pattern) pattern, body, ctx));
        }

        if (ctx.ELSE() != null) {
            var body = visit(ctx.exprs());
            cases.add(new MatchCase(new WildCard(ctx), body, ctx));
        }

        return new Tree.Match(expr, cases, ctx);
    }
}
