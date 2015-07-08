package misc;

import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

/**
 * ver1: <code>class SerializableHoge { String hoge1, hoge2; }</code>
 * ver2: <code>class SerializableHoge { String hoge2, hoge3; }</code>
 * serializedhoge.dat: hoge1="HOGE", hoge2="FUGA"
 *
 * @author irof
 */
public class SerializeTest {

//    @Test
//    public void write() throws Exception {
//        SerializableHoge hoge = new SerializableHoge();
//        hoge.hoge1 = "HOGE";
//        hoge.hoge2 = "FUGA";
//
//        File file = new File("hoge.dat");
//        try (OutputStream out = Files.newOutputStream(file.toPath());
//             ObjectOutputStream stream = new ObjectOutputStream(out)) {
//            stream.writeObject(hoge);
//        }
//    }

    @Test
    public void readVer1() throws Exception {
        File file = new File("serializedhoge.dat");
        try (InputStream in = Files.newInputStream(file.toPath());
             ObjectInputStream stream = classLoadingObjectInputStream(in, "ver1")) {
            Object o = stream.readObject();
            System.out.println(o);
        }
    }

    @Test
    public void readVer2() throws Exception {
        File file = new File("serializedhoge.dat");
        try (InputStream in = Files.newInputStream(file.toPath());
             ObjectInputStream stream = classLoadingObjectInputStream(in, "ver2")) {
            Object o = stream.readObject();
            System.out.println(o);
        }
    }

    private ObjectInputStream classLoadingObjectInputStream(InputStream in, String version) throws IOException {
        return new ObjectInputStream(in) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                File path = new File(version);
                URL[] urls = {path.toURI().toURL()};
                try (URLClassLoader loader = new URLClassLoader(urls)) {
                    return loader.loadClass(desc.getName());
                }
            }
        };
    }
}
