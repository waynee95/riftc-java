package me.waynee95.rift.ast;

import me.waynee95.rift.type.Type;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;
import java.util.Optional;

public abstract class Tree {
    public static class Program extends Node {
        public final Node Node;

        public Program(Node Node, ParserRuleContext context) {
            super("prog", context);
            this.Node = Node;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> Node;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }
    }

    public static class IntLit extends Node {
        public long value;

        public IntLit(long value, ParserRuleContext context) {
            super("int_lit", context);
            this.value = value;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> value;
                default -> new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }
    }

    public static class BoolLit extends Node {
        public boolean value;

        public BoolLit(boolean value, ParserRuleContext context) {
            super("bool_lit", context);
            this.value = value;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> value;
                default -> new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }
    }

    public static class StringLit extends Node {
        public String value;

        public StringLit(String value, ParserRuleContext context) {
            super("string_lit", context);
            this.value = value;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> value;
                default -> new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }
    }

    public static class Binary extends Node {
        public final String op;
        public final Node left;
        public final Node right;

        public Binary(Node left, Node right, String op, ParserRuleContext context) {
            super("binary", context);
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
    }

    public static class Unary extends Node {
        public final String op;
        public final Node operand;

        public Unary(String op, Node operand, ParserRuleContext context) {
            super("unary", context);
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
    }

    public static class Array extends Node {
        public final List<Node> elems;

        public Array(List<Node> elems, ParserRuleContext context) {
            super("array", context);
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
    }

    public static class Record extends Node {
        public final String id;
        public final List<Node> params;

        public Record(String id, List<Node> params, ParserRuleContext context) {
            super("record", context);
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
    }

    public static class Name extends Node {
        public final String id;

        public Name(String id, ParserRuleContext context) {
            super("name", context);
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
    }

    public static class FuncCall extends Node {
        public final String id;
        public final List<Node> args;

        public FuncCall(String id, List<Node> args, ParserRuleContext context) {
            super("call", context);
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
    }

    public static class FieldAccess extends Node {
        public final Node location;
        public final String fieldName;

        public FieldAccess(Node location, String fieldName, ParserRuleContext context) {
            super("field_access", context);
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
    }

    public static class Index extends Node {
        public final Node location;
        public final Node index;

        public Index(Node location, Node index, ParserRuleContext context) {
            super("index", context);
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
    }

    public static class Assign extends Node {
        public final Node lhs;
        public final Node rhs;

        public Assign(Node lhs, Node rhs, ParserRuleContext context) {
            super("assign", context);
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
    }

    public static class If extends Node {
        public final Node cond;
        public final Node trueBranch;
        public final Optional<Node> falseBranch;

        public If(Node cond, Node trueBranch, Optional<Node> falseBranch,
                ParserRuleContext context) {
            super("if", context);
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
    }

    public static class Body extends Node {
        public final List<Node> Nodes;

        public Body(List<Node> Nodes, ParserRuleContext context) {
            super("body", context);
            this.Nodes = Nodes;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> Nodes;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }
    }

    public static class While extends Node {
        public final Node cond;
        public final Node body;

        public While(Node cond, Node body, ParserRuleContext context) {
            super("while", context);
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
    }

    public static class Break extends Node {
        public Break(ParserRuleContext context) {
            super("break", context);
        }

        @Override
        public Object getChild(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int childCount() {
            return 0;
        }
    }

    public static class Let extends Node {
        public final List<Node> decls;
        public final Node body;

        public Let(List<Node> decls, Node body, ParserRuleContext context) {
            super("let", context);
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
    }

    public static abstract class TypeLit extends Node {
        public Type type;

        public TypeLit(String displayName, ParserRuleContext context) {
            super(displayName, context);
        }
    }

    public static final class TInt extends TypeLit {
        public TInt(ParserRuleContext context) {
            super("type_int", context);
        }

        @Override
        public Object getChild(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int childCount() {
            return 0;
        }
    }

    public static final class TBool extends TypeLit {
        public TBool(ParserRuleContext context) {
            super("type_bool", context);
        }

        @Override
        public Object getChild(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int childCount() {
            return 0;
        }
    }

    public static final class TString extends TypeLit {
        public TString(ParserRuleContext context) {
            super("type_string", context);
        }

        @Override
        public Object getChild(int index) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public int childCount() {
            return 0;
        }
    }

    public static final class TCustom extends TypeLit {
        public final String id;

        public TCustom(String id, ParserRuleContext context) {
            super("type_custom", context);
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
    }

    public static final class TArray extends TypeLit {
        public final TypeLit elemType;

        public TArray(TypeLit elemType, ParserRuleContext context) {
            super("type_array", context);
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
    }

    public static class RecordTypeDecl extends Node {
        public final String id;
        public final List<Pair<String, TypeLit>> fields;
        public Type type;

        public RecordTypeDecl(String id, List<Pair<String, TypeLit>> fields,
                ParserRuleContext context) {
            super("record_type_decl", context);
            this.id = id;
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
    }

    public static class EnumTypeDecl extends Node {
        public final String id;
        public final List<Pair<String, List<TypeLit>>> constructors;

        public EnumTypeDecl(String id, List<Pair<String, List<TypeLit>>> constructors,
                ParserRuleContext context) {
            super("enum_type_decl", context);
            this.id = id;
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
    }

    public static class VarDecl extends Node {
        public final String id;
        public final Optional<Node> value;
        public final Optional<TypeLit> typeLit;
        public final boolean immutable;

        public VarDecl(String id, Optional<Node> value, Optional<TypeLit> typeLit,
                boolean immutable, ParserRuleContext context) {
            super("var_decl", context);
            this.id = id;
            this.value = value;
            this.typeLit = typeLit;
            this.immutable = immutable;
        }

        public VarDecl(String id, Optional<TypeLit> typeLit, ParserRuleContext context) {
            super("var_decl", context);
            this.id = id;
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
    }

    public static class FuncDecl extends Node {
        public final String id;
        public final List<VarDecl> params;
        public final Optional<TypeLit> returnType;
        public final Node body;

        public FuncDecl(String id, List<VarDecl> params, Optional<TypeLit> returnType, Node body,
                ParserRuleContext context) {
            super("func_decl", context);
            this.id = id;
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
    }

    public static class ExternDecl extends Node {
        public final String id;
        public List<VarDecl> params;
        public Optional<TypeLit> returnType;

        public ExternDecl(String id, List<VarDecl> params, Optional<TypeLit> returnType,
                ParserRuleContext context) {
            super("extern_decl", context);
            this.id = id;
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
    }

    // TODO: Decls
    // TODO: Pattern
    // TODO: Match
}
