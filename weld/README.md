Weld
===================

CDIの参照実装のWeldさんです。
Java EEの仕様ですが、手軽に確認したいのでJava SEで動かします。

## Weld SE を動かすまでのメモ

Weld SE は `beans.xml` が必須な感じ。
`beans.xml` がある箇所の `*.class` を勝手に舐めるけれど、IDEとかビルドツールとかの都合で必ずしもクラスがある場所に `beans.xml` がいるとは限らない。と言うか、いなくて動かなかった。

Gradle だと `build/classes/main` に `*.class` がいて、 `beans.xml` は `build.resources/main` に入ってしまう。
これだと自分で作ったクラスに Weld が気付いてくれない。とても悲しい。

なんとかして Weld さんに私のクラスたちを気付かせたいのだけれど、どうしたものか。

Gradle でやる分にはビルド時にコピーしちゃう手もあるのだけれど、これだとIDEAでやってる時は動かない。一度コピれば動くのだけど、それだと `beans.xml` に何か書くたびに更新しなきゃいけないことになるので嫌な感じ。

`o.j.w.e.se.Weld` とか `o.j.w.e.d.d.DiscoveryStrategy` あたりをいじったらなんとかなるかなと思ったのだけれど、`beans.xml`レスにするのは骨が折れそうだったし、うまくいきそうな感じもしなかったので、コピーする方向でいくことにした。そのうち `beans.xml` は無くなるだろうし。

と、言うことで。Weldを立ち上げる( `Weld#initialize()` を実行する) 前に、`beans.xml`をコピーする。
コピー先は動かしたい `*.class` がある場所。
決め打ちでもいいのだけれど、それはそれでなんか嫌なので、Weldの認識している方法で取得したクラスのパスを取得して置いてやることにした。

これで Gradle で動かしても IDEA から動かしても動く感じになった。

## 参考

* 公式 http://weld.cdi-spec.org/
* ドキュメント http://docs.jboss.org/weld/reference/latest-2.2/en-US/html/
  * 18.4. Java SE http://docs.jboss.org/weld/reference/latest-2.2/en-US/html/environments.html#_java_se
* GradleでWeldSE使うときはcopyしちゃえという話
 * https://discuss.gradle.org/t/application-plugin-run-task-should-first-consolidate-classes-and-resources-folder-or-depend-on-installapp-or-stuff-like-weld-se-wont-work/1248

### JSR

* JSR 299 CDI 1.0 https://jcp.org/en/jsr/detail?id=299
* JSR 346 CDI 1.1 https://jcp.org/en/jsr/detail?id=346
  * Maintenance Release で 1.2 になってる
* JSR 365 CDI 2.0 https://jcp.org/en/jsr/detail?id=365
  * まだ。

