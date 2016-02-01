Flyway on Servlet
========================================

Servlet上でFlywayを動かすサンプル。

とりあえず

- `DataSource` を何らの手段でサーバーに登録して
- `ServletContextListener` に
  - `DataSource` を `@Resource` でとってきて
  - `Flyway#migrate()` して
- `Servlet` で
  - `DataSource` を `@Resource` でとってきて
  - テーブルからデータ読んで

ます。


## 動かし方

今の所JettyさんとPayaraさんだけ。

```
gradle jettyRun
```

もしくは

```
gradle war
java -jar payara-micro.jar --deploy build/lib/flyway-servlet.war
```

