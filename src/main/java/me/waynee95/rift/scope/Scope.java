package me.waynee95.rift.scope;

import me.waynee95.rift.ast.Tree;
import me.waynee95.rift.error.RiftException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Scope {
    private Map<String, Tree.Decl> symbols = new HashMap<>();
    public final Scope parent;

    public Scope(Scope parent) {
        this.parent = parent;
    }

    public void declare(String name, Tree.Decl decl) {
        if (symbols.containsKey(name)) {
            throw new RiftException("Identifier " + name + " already exists!", decl.getLine(),
                    decl.getCol());
        }
        symbols.put(name, decl);
    }

    public Optional<Tree.Decl> lookup(String name) {
        Scope scope = this;
        while (scope != null) {
            if (scope.symbols.containsKey(name)) {
                return Optional.of(scope.symbols.get(name));
            }
            scope = scope.parent;
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        Scope scope = this;
        sb.append("----").append("\n");
        while (scope != null) {
            for (Map.Entry<String, Tree.Decl> symbol : symbols.entrySet()) {
                var id = symbol.getKey();
                var decl = symbol.getValue();
                sb.append(id).append(" -> ").append(decl.displayName).append("\n");
            }

            sb.append("----");
            scope = scope.parent;
        }
        return sb.toString();
    }
}
