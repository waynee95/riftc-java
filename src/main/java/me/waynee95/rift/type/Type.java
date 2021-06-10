package me.waynee95.rift.type;

public abstract class Type {

    public boolean isDefaultType() {
        return false;
    }

    public boolean isFuncType() {
        return false;
    }

    public abstract boolean eq(Type that);
}
