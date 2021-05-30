package me.waynee95.rift.ast;

import java.util.List;

public abstract class Tree {
    public static class Program extends Node {
        public final Expr expr;

        public Program(Expr expr, Position pos) {
            super("prog", pos);
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
    }

    public abstract static class Expr extends Node {
        public Expr(String displayName, Position pos) {
            super(displayName, pos);
        }
    }

    public static class IntLit extends Expr {
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

    public static class BoolLit extends Expr {
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

    public static class StringLit extends Expr {
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

    public static class NilLit extends Expr {
        public NilLit(Position pos) {
            super("nil_lit", pos);
        }

        @Override
        public Object getChild(int index) {
            return new IndexOutOfBoundsException();
        }

        @Override
        public int childCount() {
            return 0;
        }
    }

    public static class Binary extends Expr {
        public final String op;
        public final Expr left;
        public final Expr right;

        public Binary(Expr left, Expr right, String op, Position pos) {
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

    public static class Unary extends Expr {
        public final String op;
        public final Expr operand;

        public Unary(String op, Expr operand, Position pos) {
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

    public static class Array extends Expr {
        public final List<Expr> exprs;

        public Array(List<Expr> exprs, Position pos) {
            super("array", pos);
            this.exprs = exprs;
        }

        @Override
        public Object getChild(int index) {
            return switch (index) {
                case 0 -> exprs;
                default -> throw new IndexOutOfBoundsException();
            };
        }

        @Override
        public int childCount() {
            return 1;
        }
    }

    public static class Record extends Expr {
        public final String id;
        public final List<Expr> params;

        public Record(String id, List<Expr> params, Position pos) {
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
}
