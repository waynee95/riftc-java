package me.waynee95.rift.ast;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Iterator;

public abstract class Node implements Iterable<Object> {
    public final ParserRuleContext ctx;
    public final String displayName;

    public Node(String displayName, ParserRuleContext ctx) {
        this.displayName = displayName;
        this.ctx = ctx;
    }

    public int getLine() {
        return ctx.getStart().getLine();
    }

    public int getCol() {
        return ctx.getStop().getCharPositionInLine();
    }

    public abstract Object getChild(int index);

    public abstract int childCount();

    public abstract <C> void accept(Visitor<C> v, C ctx);

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < childCount();
            }

            @Override
            public Object next() {
                return getChild(index++);
            }
        };
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append(displayName);
        sb.append('(');
        var iter = iterator();
        while (iter.hasNext()) {
            sb.append(iter.next());
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
