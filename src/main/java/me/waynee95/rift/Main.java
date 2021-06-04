package me.waynee95.rift;

import me.waynee95.rift.ast.BuildAstVisitor;
import me.waynee95.rift.ast.Node;
import me.waynee95.rift.error.ParseExceptionListener;
import me.waynee95.rift.error.RiftException;
import me.waynee95.rift.parse.RiftLexer;
import me.waynee95.rift.parse.RiftParser;
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

    // TODO: --scopes dumps symbol table for each scope
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
            }

            RiftParser parser = new RiftParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new ParseExceptionListener());

            try {
                RiftParser.ProgramContext prog = parser.program();
                Node ast = new BuildAstVisitor().visitProgram(prog);

                if (printAst) {
                    System.out.println(ast);
                }

            } catch (RiftException e) {
                onError(e.msg, e.line, e.col);
            }
        } catch (IOException e) {
            onError(e.getMessage());
        }

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

    private static void onError(String msg, int line, int col) {
        onError(msg + " at " + line + ":" + col);
    }
}
