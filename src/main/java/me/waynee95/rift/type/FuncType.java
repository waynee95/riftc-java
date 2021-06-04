package me.waynee95.rift.type;

import org.antlr.v4.runtime.misc.Pair;

import java.util.List;
import java.util.Optional;

public class FuncType {
    public final List<Pair<String, Type>> params;
    public final Optional<Type> returnType;

    public FuncType(List<Pair<String, Type>> params, Optional<Type> returnType) {
        this.params = params;
        this.returnType = returnType;
    }
}
