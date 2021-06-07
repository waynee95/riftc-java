package me.waynee95.rift.type;

public class ArrayType extends Type {
    public final Type elemType;

    public ArrayType(Type elemType) {
        this.elemType = elemType;
    }

    @Override
    public boolean eq(Type that) {
        if (that instanceof ArrayType) {
            return ((ArrayType) that).elemType.eq(elemType);
        }
        return false;
    }
}
