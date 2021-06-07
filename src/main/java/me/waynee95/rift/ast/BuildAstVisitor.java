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
import me.waynee95.rift.ast.node.location.Location;
import me.waynee95.rift.ast.node.location.Name;
import me.waynee95.rift.ast.node.pattern.*;
import me.waynee95.rift.ast.node.type.*;
import me.waynee95.rift.error.RiftException;
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
        return new Program(visit(ctx.expr()), ctx);
    }

    @Override
    public Node visitIntLit(RiftParser.IntLitContext ctx) {
        try {
            long value = Long.parseLong(ctx.INT_LIT().getText());
            return new IntLit(value, ctx);
        } catch (NumberFormatException e) {
            throw new RiftException("(" + ctx.getStart().getLine() + ":"
                    + ctx.getStop().getCharPositionInLine() + ") Integer value is too big");
        }
    }

    @Override
    public Node visitBoolLit(RiftParser.BoolLitContext ctx) {
        boolean value = Boolean.parseBoolean(ctx.BOOL_LIT().getText());
        return new BoolLit(value, ctx);
    }

    @Override
    public Node visitStringLit(RiftParser.StringLitContext ctx) {
        return new StringLit(ctx.STRING_LIT().getText(), ctx);
    }

    @Override
    public Node visitUnary(RiftParser.UnaryContext ctx) {
        var operand = (Expr) visit(ctx.expr());
        var op = Operator.fromString(ctx.op.getText());
        return new Unary(op, operand, ctx);
    }

    @Override
    public Node visitBinary(RiftParser.BinaryContext ctx) {
        var lhs = (Expr) visit(ctx.left);
        var rhs = (Expr) visit(ctx.right);
        var op = Operator.fromString(ctx.op.getText());
        return new Binary(lhs, rhs, op, ctx);
    }

    @Override
    public Node visitParen(RiftParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Node visitArray(RiftParser.ArrayContext ctx) {
        List<Expr> exprs = new ArrayList<>();
        for (var expr : ctx.expr()) {
            exprs.add((Expr) visit(expr));
        }
        return new Array(exprs, ctx);
    }

    @Override
    public Node visitRecord(RiftParser.RecordContext ctx) {
        List<Expr> params = new ArrayList<>();
        for (var expr : ctx.expr()) {
            params.add((Expr) visit(expr));
        }
        return new Record(ctx.TYPE_ID().getText(), params, ctx);
    }

    @Override
    public Node visitCons(RiftParser.ConsContext ctx) {
        List<Expr> params = new ArrayList<>();
        for (var expr : ctx.expr()) {
            params.add((Expr) visit(expr));
        }
        return new Constructor(ctx.TYPE_ID().getText(), params, ctx);
    }

    @Override
    public Node visitName(RiftParser.NameContext ctx) {
        return new Name(ctx.ID().getText(), ctx);
    }

    @Override
    public Node visitFuncCall(RiftParser.FuncCallContext ctx) {
        List<Expr> args = new ArrayList<>();
        for (RiftParser.ExprContext arg : ctx.expr()) {
            args.add((Expr) visit(arg));
        }
        return new FuncCall(ctx.ID().getText(), args, ctx);
    }

    @Override
    public Node visitFieldAccess(RiftParser.FieldAccessContext ctx) {
        var location = visit(ctx.lvalue());
        return new FieldAccess(location, ctx.ID().getText(), ctx);
    }

    @Override
    public Node visitIndex(RiftParser.IndexContext ctx) {
        var location = visit(ctx.lvalue());
        var index = visit(ctx.expr());
        return new Index(location, index, ctx);
    }

    @Override
    public Node visitAssign(RiftParser.AssignContext ctx) {
        var lhs = (Location) visit(ctx.lvalue());
        var rhs = (Expr) visit(ctx.expr());
        return new Assign(lhs, rhs, ctx);
    }

    @Override
    public Node visitIf(RiftParser.IfContext ctx) {
        var cond = visit(ctx.cond);
        var trueBranch = visit(ctx.trueBranch);

        Node falseBranch = null;
        if (ctx.falseBranch != null) {
            falseBranch = visit(ctx.falseBranch);
        }

        return new If(cond, trueBranch, Optional.ofNullable(falseBranch), ctx);
    }

    @Override
    public Node visitExprs(RiftParser.ExprsContext ctx) {
        List<Expr> exprs = new ArrayList<>();
        for (RiftParser.ExprContext expr : ctx.expr()) {
            exprs.add((Expr) visit(expr));
        }
        return new Body(exprs, ctx);
    }

    @Override
    public Node visitWhile(RiftParser.WhileContext ctx) {
        var cond = (Expr) visit(ctx.cond);
        var body = (Body) visit(ctx.exprs());
        return new While(cond, body, ctx);
    }

    @Override
    public Node visitBreak(RiftParser.BreakContext ctx) {
        return new Break(ctx);
    }

    @Override
    public Node visitLet(RiftParser.LetContext ctx) {
        List<Node> decls = new ArrayList<>();
        for (RiftParser.DeclContext decl : ctx.decl()) {
            decls.add((Node) visit(decl));
        }
        var body = visit(ctx.exprs());
        return new Let(decls, body, ctx);
    }

    @Override
    public Node visitType(RiftParser.TypeContext ctx) {
        if (ctx.INT() != null) {
            return new TInt(ctx);
        } else if (ctx.BOOL() != null) {
            return new TBool(ctx);
        } else if (ctx.STRING() != null) {
            return new TString(ctx);
        } else if (ctx.TYPE_ID() != null) {
            return new TCustom(ctx.TYPE_ID().getText(), ctx);
        } else {
            return new TArray((TypeLit) visit(ctx.type()), ctx);
        }
    }

    @Override
    public Node visitVarDecl(RiftParser.VarDeclContext ctx) {
        TypeLit type = ctx.type() != null ? (TypeLit) visit(ctx.type()) : null;
        var immutable = ctx.VAL() != null ? true : false;
        var value = (Expr) visit(ctx.expr());

        return new VarDecl(ctx.ID().getText(), Optional.of(value), Optional.ofNullable(type),
                immutable, ctx);
    }

    @Override
    public Node visitFuncDecl(RiftParser.FuncDeclContext ctx) {
        List<VarDecl> params = new ArrayList<>();
        var id = ctx.ID().getText();
        var body = visit(ctx.exprs());

        TypeLit returnType = null;
        if (ctx.type() != null) {
            returnType = (TypeLit) visit(ctx.type());
        }

        for (int i = 0; i < ctx.typefields().ID().size(); i++) {
            var param = ctx.typefields().ID(i).getText();
            TypeLit type = (TypeLit) visit(ctx.typefields().type(i));
            params.add(new VarDecl(param, Optional.of(type), ctx));
        }

        return new FuncDecl(id, params, Optional.ofNullable(returnType), body, ctx);
    }

    @Override
    public Node visitExternDecl(RiftParser.ExternDeclContext ctx) {
        List<VarDecl> params = new ArrayList<>();
        var id = ctx.ID().getText();

        TypeLit returnType = null;
        if (ctx.type() != null) {
            returnType = (TypeLit) visit(ctx.type());
        }

        for (int i = 0; i < ctx.typefields().ID().size(); i++) {
            var param = ctx.typefields().ID(i).getText();
            TypeLit type = (TypeLit) visit(ctx.typefields().type(i));
            params.add(new VarDecl(param, Optional.of(type), ctx));
        }

        return new ExternDecl(id, params, Optional.ofNullable(returnType), ctx);
    }

    @Override
    public Node visitTypeDecl(RiftParser.TypeDeclContext ctx) {
        var id = ctx.TYPE_ID().getText();

        if (ctx.typedec().LCURLY() != null) {
            List<Pair<String, TypeLit>> fields = new ArrayList<>();

            for (int i = 0; i < ctx.typedec().typefields().ID().size(); i++) {
                var param = ctx.typedec().typefields().ID(i).getText();
                TypeLit type = (TypeLit) visit(ctx.typedec().typefields().type(i));
                fields.add(new Pair<>(param, type));
            }

            return new RecordTypeDecl(id, fields, ctx);
        } else {
            List<VariantDecl> constructors = new ArrayList<>();

            for (RiftParser.ConstructorContext constructor : ctx.typedec().constructor()) {
                List<TypeLit> types = new ArrayList<>();
                for (RiftParser.TypeContext type : constructor.type()) {
                    types.add((TypeLit) visit(type));
                }
                constructors.add(new VariantDecl(constructor.TYPE_ID().getText(), types, ctx));
            }

            return new EnumTypeDecl(id, constructors, ctx);
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
        List<VarDecl> fields = new ArrayList<>();
        for (TerminalNode fieldName : ctx.ID()) {
            fields.add(new VarDecl(fieldName.getText(), Optional.empty(), ctx));
        }
        return new RecordPattern(fields, ctx);
    }

    @Override
    public Node visitVariablePattern(RiftParser.VariablePatternContext ctx) {
        return new VariablePattern(new VarDecl(ctx.ID().getText(), Optional.empty(), ctx), ctx);
    }

    @Override
    public Node visitMatch(RiftParser.MatchContext ctx) {
        List<MatchCase> cases = new ArrayList<>();
        var expr = visit(ctx.expr());

        for (int i = 0; i < ctx.matchcases().pattern().size(); i++) {
            var pattern = visit(ctx.matchcases().pattern(i));
            var body = visit(ctx.matchcases().exprs(i));
            cases.add(new MatchCase((Pattern) pattern, (Body) body, ctx));
        }

        if (ctx.ELSE() != null) {
            var body = visit(ctx.exprs());
            cases.add(new MatchCase(new WildCard(ctx), (Body) body, ctx));
        }

        return new Match(expr, cases, ctx);
    }
}
