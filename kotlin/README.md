Kotlin
==============================

Kotlinをはじめてみよう。

## インストールしてみる

sdkmanでいけるんじゃないかと思って、`sdk install kotlin`を叩いてみる。

```
% sdk install kotlin

Downloading: kotlin 1.0.1

  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
100   601    0   601    0     0    359      0 --:--:--  0:00:01 --:--:--   558
100 19.6M  100 19.6M    0     0   984k      0  0:00:20  0:00:20 --:--:-- 1586k

Installing: kotlin 1.0.1
Done installing!

Do you want kotlin 1.0.1 to be set as default? (Y/n): y

Setting kotlin 1.0.1 as default.
```

入ったわ。

## げってぃんぐすたーてっどを探す

文法もわからんからとりあえずググる。

- https://www.google.co.jp/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=kotlin%20getting%20started

一番上に公式が出てきてくれた時の安心感。

開いたら当然のように「IDEAでやろうぜ！」って言われる。
まあわかるけど、私はとりあえずコマンドラインとvimでやりたいんだーと思ったら、それっぽいのがある。

- https://kotlinlang.org/docs/tutorials/command-line.html

……ん。sdkmanで入れろって書いてる。
いいや。とりあえずはろーわーるど書いてみよう。

## はろわ！

書くぜー。

- [hello.kt](tutorials/command-line/hello.kt)

ふむ。
そしてこんぱいr……ん？

```
$ kotlinc hello.kt -include-runtime -d hello.jar
```

なんかコマンド長いなーと思った。
まあいいか。実行したらhello.jar ができた。

サイズは80kbくらい、そこそこでかい。
`-include-runtime`とかついてるし、実行可能jarなんだろと思って実行。

```
% java -jar hello.jar
Hello, World!
```

できたできた。

## zip展開してjavap

どうせ `hello.class` とかあるんだろうと思ってunzipしたら、なんか `HelloKt.class` ってのが入ってた。

```
% javap HelloKt
Compiled from "hello.kt"
public final class HelloKt {
    public static final void main(java.lang.String[]);
}
```

こいつそのまま実行したらいけるんだろうなーと。

```
% java HelloKt
Hello, World!
```

いけた。
んー、これだけならクラスだけでも動くのかな。
って思って、クラスだけ移動して実行。

```
% java HelloKt
Exception in thread "main" java.lang.NoClassDefFoundError: kotlin/jvm/internal/Intrinsics
  at HelloKt.main(hello.kt)
  Caused by: java.lang.ClassNotFoundException: kotlin.jvm.internal.Intrinsics
    at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
    at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
    at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
    at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
    ... 1 more
```

だめか（当たり前だ

mainメソッドの中でクラスロードが入ってる。
なんだろう。

```
$ javap -v HelloKt
...略
  #15 = Methodref          #11.#14        // kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
...略
public static final void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL
    Code:
      stack=2, locals=3, args_size=1
         0: aload_0
         1: ldc           #9                  // String args
         3: invokestatic  #15                 // Method kotlin/jvm/internal/Intrinsics.checkParameterIsNotNull:(Ljava/lang/Object;Ljava/lang/String;)V
         6: ldc           #17                 // String Hello, World!
         8: astore_1
         9: nop
        10: getstatic     #23                 // Field java/lang/System.out:Ljava/io/PrintStream;
        13: aload_1
        14: invokevirtual #29                 // Method java/io/PrintStream.println:(Ljava/lang/Object;)V
        17: return
...略
```

パラメーターの`null`チェックをメソッドの先頭でやってるんだなーと。

javap見た感じ、そいつあったら動く気がする。
適当に作ってみる。

- [Intrinsics.java](tutorials/command-line/kotlin/jvm/internal/Intrinsics.java)

そして実行。

```
% java HelloKt
[Ljava.lang.String;@7852e922
args
Hello, World!
```

動いた。謎の自己満足。

## そんなわけで

- 4/2(土) Kotlin 1.0リリース記念勉強会 in 京都
  - http://kanjava.connpass.com/event/27758/

Kotlin勉強会の裏で書いてました。


