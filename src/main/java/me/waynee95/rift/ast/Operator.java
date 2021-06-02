package me.waynee95.rift.ast;

public class Operator {
    enum Op {
        ADD("+"),
        SUB("-"),
        MULT("*"),
        DIV("/"),
        REM("%"),
        AND("&&"),
        OR("||"),
        ASSIGN("="),
        LT("<"),
        LE("<="),
        GT(">"),
        GE(">="),
        EQ("=="),
        NOT_EQ("!="),
        NOT("!");

        private String label;

        Op(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public static Operator.Op fromString(String s) {
        return switch (s) {
            case "+" -> Op.ADD;
            case "-" -> Op.SUB;
            case "*" -> Op.MULT;
            case "/" -> Op.DIV;
            case "%" -> Op.REM;
            case "&&" -> Op.AND;
            case "||" -> Op.OR;
            case "=" -> Op.ASSIGN;
            case "<" -> Op.LT;
            case "<=" -> Op.LE;
            case ">" -> Op.GT;
            case ">=" -> Op.GE;
            case "==" -> Op.EQ;
            case "!=" -> Op.EQ;
            case "!" -> Op.NOT;
            default -> throw new IllegalArgumentException();
        };
    }
}
