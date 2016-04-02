package kotlin.jvm.internal;

public class Intrinsics {

  public static void checkParameterIsNotNull(Object obj, String str) {
    System.out.println(obj); // 値
    System.out.println(str); // 仮引数名
  }
}
