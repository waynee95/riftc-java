package me.waynee95.rift.ast;

import java.util.List;
import java.util.Optional;

public abstract class Tree {
    public static class Program extends Node {
        public final Node Node;

        public Program(Node Node, Position pos) {
            super("prog", pos);
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

        public IntLit(long value, Position pos) {
            super("int_lit", pos);
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

        public BoolLit(boolean value, Position pos) {
            super("bool_lit", pos);
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

        public StringLit(String value, Position pos) {
            super("string_lit", pos);
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

        public Binary(Node left, Node right, String op, Position pos) {
            super("binary", pos);
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

        public Unary(String op, Node operand, Position pos) {
            super("unary", pos);
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
        public final List<Node> Nodes;

        public Array(List<Node> Nodes, Position pos) {
            super("array", pos);
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

    public static class Record extends Node {
        public final String id;
        public final List<Node> params;

        public Record(String id, List<Node> params, Position pos) {
            super("record", pos);
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

        public Name(String id, Position pos) {
            super("name", pos);
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

    public static class FieldAccess extends Node {
        public final Node location;
        public final String fieldName;

        public FieldAccess(Node location, String fieldName, Position pos) {
            super("field_access", pos);
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

        public Index(Node location, Node index, Position pos) {
            super("index", pos);
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

        public Assign(Node lhs, Node rhs, Position pos) {
            super("assign", pos);
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

        public If(Node cond, Node trueBranch, Optional<Node> falseBranch, Position pos) {
            super("if", pos);
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

        public Body(List<Node> Nodes, Position pos) {
            super("body", pos);
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

        public While(Node cond, Node body, Position pos) {
            super("while", pos);
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
        public Break(Position pos) {
            super("break", pos);
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

        public Let(List<Node> decls, Node body, Position pos) {
            super("let", pos);
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

    // TODO: VarDecl
    // TODO: TypeDecl
    // TODO: FuncDecl
    // TODO: ExternDecl

    // TODO: TypeName
    // TODO: RecordType
    // TODO: ArrayType

    // TODO: Create distinct test files for all cases!

}
