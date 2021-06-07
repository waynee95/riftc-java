package me.waynee95.rift.type;

import me.waynee95.rift.error.NotImplementedException;

import java.util.List;

public class RecordType extends Type {
    public final List<Type> fieldTypes;

    public RecordType(List<Type> fieldTypes) {
        this.fieldTypes = fieldTypes;
    }

    @Override
    public boolean eq(Type that) {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("{ ");

        var first = true;
        for (var fieldType : fieldTypes) {
            if (first) {
                sb.append(fieldType);
                first = false;
            } else {
                sb.append(fieldType).append(", ");
            }
        }

        sb.append(" }");
        return sb.toString();
    }
}
