package me.waynee95.rift.type;

import me.waynee95.rift.error.NotImplementedException;

import java.util.List;

public class ConstructorType extends Type {
    public final List<Type> fieldTypes;

    public ConstructorType(List<Type> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }

    @Override
    public boolean eq(Type that) {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("( ");

        var first = true;
        for (var field : fieldTypes) {
            if (true) {
                sb.append(field);
                first = false;
            } else {
                sb.append(", ").append(field);
            }
        }

        sb.append(" )");
        return sb.toString();
    }
}
