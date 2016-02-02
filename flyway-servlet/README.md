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

### Jetty

grettyで動かします。

```
gradle jettyRun
```

### Payara micro

適当にデプロイしたってください。

```
gradle war
java -jar payara-micro.jar --deploy build/lib/flyway-servlet.war
```

### WildFly

適当にデプロイしたってください。

```
# 起動はしとく
$WILDFLY_HOME/bin/standalone.sh

gradle war
$WILDFLY_HOME/bin/jboss-cli.sh -c --command="deploy build/libs/flyway-servlet.war"
```

