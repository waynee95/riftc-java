package me.waynee95.rift.ast;

import me.waynee95.rift.ast.pattern.MatchCase;
import me.waynee95.rift.type.Type;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;
import java.util.Optional;

public abstract class Tree {
    public static class Program extends Node {
        public final Node expr;

        public Program(Node expr, ParserRuleContext ctx) {
            super("prog", ctx);
            this.expr = expr;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> expr;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public abstract static class Literal<T> extends Node {
        public final T value;

        public Literal(String displayName, T value, ParserRuleContext ctx) {
            super(displayName, ctx);
            this.value = value;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> value;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class IntLit extends Literal<Long> {

        public IntLit(long value, ParserRuleContext ctx) {
            super("int_lit", value, ctx);
        }
    }

    public static class BoolLit extends Literal<Boolean> {

        public BoolLit(Boolean value, ParserRuleContext ctx) {
            super("bool_lit", value, ctx);
        }
    }

    public static class StringLit extends Literal<String> {

        public StringLit(String value, ParserRuleContext ctx) {
            super("string_lit", value, ctx);
        }
    }

    public static class Binary extends Node {
        public final Operator.Op op;
        public final Node left;
        public final Node right;

        public Binary(Node left, Node right, Operator.Op op, ParserRuleContext ctx) {
            super("binary", ctx);
            this.op = op;
            this.left = left;
            this.right = right;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> op;
                case 1 -> left;
                case 2 -> right;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 3;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Unary extends Node {
        public final Operator.Op op;
        public final Node operand;

        public Unary(Operator.Op op, Node operand, ParserRuleContext ctx) {
            super("unary", ctx);
            this.op = op;
            this.operand = operand;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> op;
                case 1 -> operand;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Array extends Node {
        public final List<Node> elems;

        public Array(List<Node> elems, ParserRuleContext ctx) {
            super("array", ctx);
            this.elems = elems;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> elems;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Record extends Node {
        public final String id;
        public final List<Node> params;

        public Record(String id, List<Node> params, ParserRuleContext ctx) {
            super("record", ctx);
            this.id = id;
            this.params = params;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                case 1 -> params;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Constructor extends Node {
        public final String id;
        public final List<Node> params;


        public Constructor(String id, List<Node> params, ParserRuleContext ctx) {
            super("constructor", ctx);
            this.id = id;
            this.params = params;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                case 1 -> params;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Name extends Node {
        public final String id;
        public Optional<VarDecl> decl = Optional.empty();

        public Name(String id, ParserRuleContext ctx) {
            super("name", ctx);
            this.id = id;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class FuncCall extends Node {
        public final String id;
        public final List<Node> args;

        public FuncCall(String id, List<Node> args, ParserRuleContext ctx) {
            super("call", ctx);
            this.id = id;
            this.args = args;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                case 1 -> args;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class FieldAccess extends Node {
        public final Node location;
        public final String fieldName;
        public Optional<Tree.RecordTypeDecl> decl = Optional.empty();

        public FieldAccess(Node location, String fieldName, ParserRuleContext ctx) {
            super("field_access", ctx);
            this.location = location;
            this.fieldName = fieldName;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> location;
                case 1 -> fieldName;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Index extends Node {
        public final Node location;
        public final Node index;
        public Optional<Tree.VarDecl> decl = Optional.empty();

        public Index(Node location, Node index, ParserRuleContext ctx) {
            super("index", ctx);
            this.location = location;
            this.index = index;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> location;
                case 1 -> this.index;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Assign extends Node {
        public final Node lhs;
        public final Node rhs;

        public Assign(Node lhs, Node rhs, ParserRuleContext ctx) {
            super("assign", ctx);
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> lhs;
                case 1 -> rhs;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class If extends Node {
        public final Node cond;
        public final Node trueBranch;
        public final Optional<Node> falseBranch;

        public If(Node cond, Node trueBranch, Optional<Node> falseBranch, ParserRuleContext ctx) {
            super("if", ctx);
            this.cond = cond;
            this.trueBranch = trueBranch;
            this.falseBranch = falseBranch;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> cond;
                case 1 -> trueBranch;
                case 2 -> falseBranch;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 3;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Body extends Node {
        public final List<Node> nodes;

        public Body(List<Node> Nodes, ParserRuleContext ctx) {
            super("body", ctx);
            this.nodes = Nodes;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> nodes;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class While extends Node {
        public final Node cond;
        public final Node body;

        public While(Node cond, Node body, ParserRuleContext ctx) {
            super("while", ctx);
            this.cond = cond;
            this.body = body;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> cond;
                case 1 -> body;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Break extends Node {
        public Break(ParserRuleContext ctx) {
            super("break", ctx);
        }

        @Override
        public Object getChild(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int childCount() {
            return 0;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Let extends Node {
        public final List<Node> decls;
        public final Node body;

        public Let(List<Node> decls, Node body, ParserRuleContext ctx) {
            super("let", ctx);
            this.decls = decls;
            this.body = body;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> decls;
                case 1 -> body;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static abstract class TypeLit extends Node {
        public Type type;

        public TypeLit(String displayName, ParserRuleContext ctx) {
            super(displayName, ctx);
        }
    }

    public static final class TInt extends TypeLit {
        public TInt(ParserRuleContext ctx) {
            super("type_int", ctx);
        }

        @Override
        public Object getChild(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int childCount() {
            return 0;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static final class TBool extends TypeLit {
        public TBool(ParserRuleContext ctx) {
            super("type_bool", ctx);
        }

        @Override
        public Object getChild(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int childCount() {
            return 0;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static final class TString extends TypeLit {
        public TString(ParserRuleContext ctx) {
            super("type_string", ctx);
        }

        @Override
        public Object getChild(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int childCount() {
            return 0;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static final class TCustom extends TypeLit {
        public final String id;
        public Optional<Decl> decl = Optional.empty();

        public TCustom(String id, ParserRuleContext ctx) {
            super("type_custom", ctx);
            this.id = id;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static final class TArray extends TypeLit {
        public final TypeLit elemType;

        public TArray(TypeLit elemType, ParserRuleContext ctx) {
            super("type_array", ctx);
            this.elemType = elemType;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> elemType;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public abstract static class Decl extends Node {
        public final String id;
        public Optional<Type> type = Optional.empty();

        public Decl(String id, String displayName, ParserRuleContext ctx) {
            super(displayName, ctx);
            this.id = id;
        }
    }

    public static class RecordTypeDecl extends Decl {
        public final List<Pair<String, TypeLit>> fields;

        public RecordTypeDecl(String id, List<Pair<String, TypeLit>> fields,
                ParserRuleContext ctx) {
            super(id, "record_type_decl", ctx);
            this.fields = fields;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                case 1 -> fields;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class EnumTypeDecl extends Decl {
        public final List<Pair<String, List<TypeLit>>> constructors;

        public EnumTypeDecl(String id, List<Pair<String, List<TypeLit>>> constructors,
                ParserRuleContext ctx) {
            super(id, "enum_type_decl", ctx);
            this.constructors = constructors;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                case 1 -> constructors;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class VarDecl extends Decl {
        public final Optional<Node> value;
        public final Optional<TypeLit> typeLit;
        public final boolean immutable;

        public VarDecl(String id, Optional<Node> value, Optional<TypeLit> typeLit,
                boolean immutable, ParserRuleContext ctx) {
            super(id, "var_decl", ctx);
            this.value = value;
            this.typeLit = typeLit;
            this.immutable = immutable;
        }

        public VarDecl(String id, Optional<TypeLit> typeLit, ParserRuleContext ctx) {
            super(id, "var_decl", ctx);
            this.value = Optional.empty();
            this.typeLit = typeLit;
            this.immutable = false;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                case 1 -> value;
                case 2 -> typeLit;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 3;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class FuncDecl extends Decl {
        public final List<VarDecl> params;
        public final Optional<TypeLit> returnType;
        public final Node body;

        public FuncDecl(String id, List<VarDecl> params, Optional<TypeLit> returnType, Node body,
                ParserRuleContext ctx) {
            super(id, "func_decl", ctx);
            this.params = params;
            this.returnType = returnType;
            this.body = body;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                case 1 -> params;
                case 2 -> returnType;
                case 3 -> body;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 4;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class ExternDecl extends Decl {
        public List<VarDecl> params;
        public Optional<TypeLit> returnType;

        public ExternDecl(String id, List<VarDecl> params, Optional<TypeLit> returnType,
                ParserRuleContext ctx) {
            super(id, "extern_decl", ctx);
            this.params = params;
            this.returnType = returnType;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> id;
                case 1 -> params;
                case 2 -> returnType;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 3;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }

    public static class Match extends Node {
        public final Node expr;
        public final List<MatchCase> cases;

        public Match(Node expr, List<MatchCase> cases, ParserRuleContext ctx) {
            super("match", ctx);
            this.expr = expr;
            this.cases = cases;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> expr;
                case 1 -> cases;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 2;
        }

        @Override
        public <C> void accept(Visitor<C> v, C ctx) {
            v.visit(this, ctx);
        }
    }
}
