package me.waynee95.rift.type;

import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class RecordType extends Type {
    public final List<Pair<String, Type>> fields;

    public RecordType(List<Pair<String, Type>> fields) {
        this.fields = fields;
    }
}
