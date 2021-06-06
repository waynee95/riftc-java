package me.waynee95.rift;

import me.waynee95.rift.ast.BuildAstVisitor;
import me.waynee95.rift.ast.node.Program;
import me.waynee95.rift.error.ParseExceptionListener;
import me.waynee95.rift.error.RiftException;
import me.waynee95.rift.parse.RiftLexer;
import me.waynee95.rift.parse.RiftParser;
import me.waynee95.rift.scope.Scope;
import me.waynee95.rift.scope.ScopeVisitor;
import org.antlr.v4.runtime.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Command(name = "riftc", version = "0.0.0",
        description = "A compiler for the Rift programming language.", sortOptions = false)
class Main implements Runnable {
    @Parameters(paramLabel = "FILE", description = "*.rift source file.")
    private String filePath;

    @Option(names = "--help", usageHelp = true, description = "Print this text.")
    private boolean showHelp = false;

    @Option(names = "--lex-trace", description = "Trace the result of the lexer.")
    boolean lexTrace = false;

    @Option(names = "--parse", description = "Run the parser on the input file.")
    boolean parse = false;

    @Option(names = "--print-ast", description = "Print the AST of the input file.")
    boolean printAst = false;

    @Option(names = "--scopes", description = "Print the AST of the input file.")
    boolean scopes = false;

    // TODO: --typecheck to run the type checker on the input file

    public static void main(String[] args) {
        new CommandLine(new Main()).execute(args);
    }

    @Override
    public void run() {
        if (filePath == null) {
            // TODO: Is there a _nicer_ way for this?
            new CommandLine(new Main()).usage(System.out);
        }

        try {
            if (!Files.exists(Path.of(filePath))) {
                onError("file " + filePath + " does not exist.");
            }

            if (!filePath.endsWith(".rift") || Files.isDirectory(Path.of(filePath))) {
                onError(filePath + " is not a valid input file");
            }

            CharStream input = CharStreams.fromFileName(filePath);

            // TODO: Throw error on unkown symbol
            RiftLexer lexer = new RiftLexer(input);

            if (lexTrace) {
                Token token = lexer.nextToken();
                printToken(token);
                while (token.getType() != Token.EOF) {
                    token = lexer.nextToken();
                    printToken(token);
                }
                System.out.print("\n");
            }

            RiftParser parser = new RiftParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new ParseExceptionListener());

            try {
                RiftParser.ProgramContext prog = parser.program();
                Program ast = (Program) new BuildAstVisitor().visitProgram(prog);

                if (printAst) {
                    System.out.println(ast);
                    System.out.print("\n");
                }

                // When parse flag is enabled, we just parse the file and then exit
                if (parse) {
                    onSuccess();
                }

                Scope globalScope = new Scope(null);
                ScopeVisitor sv = new ScopeVisitor();
                sv.visit(ast, globalScope);

                if (scopes) {
                    // TODO: Print scopes
                }

            } catch (RiftException e) {
                onError(e.getMessage());
            }
        } catch (IOException e) {
            onError(e.getMessage());
        }

        onSuccess();
    }

    private void onSuccess() {
        System.out.println("\nAccepted.");
        System.exit(0);
    }

    private static void printToken(Token token) {
        System.out.println(
                token.getLine() + ":" + token.getCharPositionInLine() + " " + token.getText());
    }

    private static void onError(String msg) {
        System.err.println("error: " + msg);
        System.err.println("\nRejected.");
        System.exit(1);
    }
}
