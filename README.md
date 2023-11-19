<h1 align="center" style="text-align:center;">
  van
</h1>
<p align="center">
	<strong>类似git操作的eventbus</strong>
</p>

<p align="center">
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.txt">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
    <a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8+-green.svg" alt="jdk-8+" />
	</a>
    <br />
</p>

<br/>

<hr />

# 特点

* 非常轻量，代码简单，大小只有8k
* git命令操作，api友好
* 支持编程式和注解
* 支持jdk8+

# 使用方式

Maven

```xml

<dependency>
    <groupId>io.github.kongweiguang</groupId>
    <artifactId>van</artifactId>
    <version>0.1</version>
</dependency>
```

Gradle

```xml
implementation 'io.github.kongweiguang:van:0.1'
```

Gradle-Kotlin

```xml
implementation("io.github.kongweiguang:van:0.1")
```

# 简单介绍

详情看test的例子

## 编程式

```java
public class PushTopicTest {
    String topic = "topic.test1";

    @Test
    void test() throws Exception {
        //拉取消息
        hub().pull(topic, m -> {
            System.out.println(m.id());
            System.out.println(m.content());
        });

        //推送消息
        hub().push(topic, "content");
        hub().push(topic, "content", e -> System.out.println("callback 1 -> " + e));
        hub().push(Action.of(topic, "content"));
        hub().push(Action.of(topic, "content"), e -> System.out.println("callback 2 -> " + e));
    }
}

```

## 注解式

```java
public class PushObjMethodTest {
    @Test
    void test1() throws Exception {
        //设置拉取消息的处理
        hub().pull(new MyHandler());

        //推送tipic为bala的消息
        hub().push(Action.of("bala", new User(1, "k", new String[]{"h"})), object -> System.out.println("object = " + object));

        //推送topic为bala1的消息
        hub().push("bala1", new User(1, "k", new String[]{"h"}), object -> System.out.println("object = " + object));

        //推送user类的topic
        hub().push(new User(1, "k", new String[]{"h"}), object -> System.out.println("object = " + object));

        Van.<User, String>hub().push(new User(1, "k", new String[]{"h"}), object -> System.out.println("object = " + object));

    }
}

```
