package me.waynee95.rift.type;

import me.waynee95.rift.error.NotImplementedException;

import java.util.List;
import java.util.Optional;

public class FuncType extends Type {
    public final List<Type> paramTypes;
    public final Optional<Type> returnType;

    public FuncType(List<Type> paramTypes, Type returnType) {
        this.paramTypes = paramTypes;
        this.returnType = Optional.of(returnType);
    }

    public FuncType(List<Type> paramTypes) {
        this.paramTypes = paramTypes;
        this.returnType = Optional.empty();
    }

    public int arity() {
        return paramTypes.size();
    }

    public boolean hasReturnType() {
        return returnType.isPresent();
    }

    @Override
    public boolean isFuncType() {
        return true;
    }

    @Override
    public boolean eq(Type that) {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("(");

        var first = true;
        for (var paramType : paramTypes) {
            if (first) {
                sb.append(paramType);
                first = false;
            } else {
                sb.append(", ").append(paramType);
            }
        }

        sb.append(")");
        if (returnType.isPresent()) {
            sb.append(" -> ").append(returnType);
        }

        return sb.toString();
    }
}
