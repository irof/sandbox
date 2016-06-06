# JaCoCoさんでカバレッジをとってみる

Java8時代のカバレッジ取得は[JaCoCoさん](http://eclemma.org/jacoco/trunk/index.html)一強っぽい。
有償のなら他にもあるんだけど。

## Gradle

Gradleさんが提供してくれてる[JaCoCo plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)を使って出力します。

### せってー

ミニマムならプラグインの追加だけ。
```
apply plugin: 'jacoco'
```
これだと使用するJaCoCoのバージョンがGradleのバージョン依存になるので、指定したほうがよいでしょう。
ちなみにGradle2.12だと`0.7.1.201405082137`で、Grale2.13だと`0.7.6.201602180812`になります。

バージョンの指定は`jacoco.toolVersion`で。

```
jacoco {
  toolVersion = "0.7.6.201602180812"
}
```

### じっこー

```
gradle clean test jacocoTestReport
```

`test`タスク実行時に自動的にAgentがねじ込まれてカバレッジはとられます。
HTML（とか）に整形するタスクが`jacocoTestReport`です。

jacocoのtaskは`jacocoTestReport`しかないので、`gradle jacoco`とかでも実行できます。
`jacocoTestReport`って覚えられないよね。

## Maven

Gradleが使えないならMavenでがんばるしかない。

JaCoCoさんが提供してくれてる[Maven Plug-in](http://eclemma.org/jacoco/trunk/doc/maven.html)を使って出力します。

### せってー

[Example](http://eclemma.org/jacoco/trunk/doc/examples/build/pom.xml)があるのでその通りpom.xmlに書きます。
長いので引用はしない……。
executionで指定してあげないと、test実行しても勝手にAgentかまされたりはしないです。

### じっこー

```
mvn clean test jacoco:report
```
HTML（とか）に整形するgoalが`jacoco:report`です。

