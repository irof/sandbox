import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;

public class CuPrinter {

    public static void main(String[] args) throws Exception {
        // creates an input stream for the file to be parse
        try (FileInputStream in = new FileInputStream("src/main/java/CuPrinter.java")) {

            // parse the file
            CompilationUnit cu = JavaParser.parse(in);

            // prints the resulting compilation unit to default system output
            //System.out.println(cu.toString());

            cu.accept(visitor(), null);

            System.out.println(cu);
        }
    }

    private static VoidVisitorAdapter<Void> visitor() {
        return new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration n, Void arg) {
                System.out.println(n.getName());
                super.visit(n, arg);

                n.setName(n.getNameAsString().toUpperCase());
                n.addParameter("int", "value");
            }
        };
    }

    public void hoge() {
    }

    int fuga() {
        return 0;
    }
}