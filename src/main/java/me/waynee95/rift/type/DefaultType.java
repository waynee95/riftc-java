package me.waynee95.rift.type;

public class DefaultType extends Type {
    public static final DefaultType INT = new DefaultType("i64");
    public static final DefaultType BOOL = new DefaultType("bool");
    public static final DefaultType STRING = new DefaultType("string");
    public static final DefaultType UNIT = new DefaultType("unit");

    public final String name;

    public DefaultType(String name) {
        this.name = name;
    }

    @Override
    public boolean isDefaultType() {
        return true;
    }

    @Override
    public boolean eq(Type that) {
        return this == that;
    }

    @Override
    public String toString() {
        return name;
    }
}
