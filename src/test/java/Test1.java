import io.github.kongweiguang.van.core.IdGen;

public class Test1 {
    public static void main(String[] args) {

        final long start = System.currentTimeMillis();
        for (int i = 0; i < 500_000_000; i++) {
//            System.out.println(IdGen.of.next());
            IdGen.of.next();
        }


        final long end = System.currentTimeMillis();
        System.out.println("use time -> " + (end - start) + "ms");

    }
}
