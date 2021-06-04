package me.waynee95.rift.type;

public class ArrayType extends Type {
    public final Type elemType;

    public ArrayType(Type elemType) {
        this.elemType = elemType;
    }
}
