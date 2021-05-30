package me.waynee95.rift.ast;

import java.util.Iterator;

public abstract class Node implements Iterable<Object> {
    public final String displayName;
    public final Position pos;

    public Node(String displayName, Position pos) {
        this.displayName = displayName;
        this.pos = pos;
    }

    public abstract Object getChild(int index);

    public abstract int childCount();

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
