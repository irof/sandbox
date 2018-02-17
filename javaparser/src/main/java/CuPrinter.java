import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;

/**
 * クラスコメント
 */
public class CuPrinter {

    public static void main(String[] args) throws Exception {
        try (FileInputStream in = new FileInputStream("src/main/java/CuPrinter.java")) {
            CompilationUnit cu = JavaParser.parse(in);
            cu.accept(visitor(), null);
        }
    }

    private static VoidVisitorAdapter<Void> visitor() {
        return new VoidVisitorAdapter<Void>() {

            @Override
            public void visit(JavadocComment n, Void arg) {
                super.visit(n, arg);
            }
        };
    }

    public void hoge() {
    }

    /**
     * メソッドコメント
     */
    int fuga() {
        return 0;
    }
}