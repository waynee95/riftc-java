package me.waynee95.rift.type;

import me.waynee95.rift.error.NotImplementedException;

import java.util.List;

public class EnumType extends Type {
    public final List<Type> constructorTypes;

    public EnumType(List<Type> constructorTypes) {
        this.constructorTypes = constructorTypes;
    }

    @Override
    public boolean eq(Type that) {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        var first = true;
        for (var constructorType : constructorTypes) {
            if (first) {
                sb.append(constructorType);
                first = false;
            } else {
                sb.append("| ").append(constructorType);
            }
        }

        return sb.toString();
    }
}
