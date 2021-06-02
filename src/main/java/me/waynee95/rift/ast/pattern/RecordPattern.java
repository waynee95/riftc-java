package me.waynee95.rift.ast.pattern;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.List;

public class RecordPattern extends Pattern {
    public final List<String> fields;

    public RecordPattern(List<String> fields, ParserRuleContext context) {
        super("record_pattern", context);
        this.fields = fields;
    }

    @Override
    public Object getChild(int index) {
        return switch (index) {
            case 0 -> fields;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    @Override
    public int childCount() {
        return 1;
    }
}
