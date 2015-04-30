Seleniumを軽く試す
==============================

SeleniumのサンプルコードなどではGoogleにアクセスしたりしてるけれど、
手元で試すには少々重量。なので、軽く試すとしたらこうすると言うのを。

単に初期ページをリソースから取得してるだけだけど、これできるだけでも捗る。

「軽く試す」ためのものなので、gradlew入れとく。

## 環境

* Java SE 8
* Firefox 37.0.2

## 動かし方

1. `./gradlew run`

MacでもWinでも。

## 動いたら

一瞬Firefoxが出てすぐ消える。ログはこんな感じ。

```
% ./gradlew run                                                                                                              (git)-[master]
:compileJava
:processResources
:classes
:run
index page
target page

BUILD SUCCESSFUL

Total time: 10.714 secs
```
