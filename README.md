### spring-cloud-easy-config
麻雀虽小，五脏俱全。
提供轻量级的配置及动态刷新
对于中小项目，完全没有必要硬上Nacos，也不需要服务端
只需要引入依赖，配置连接池配置，即可实现动态配置

#### 引入方法

springboot版本 < 2.4.0
```xml
<dependency>
    <groupId>icu.liufuqiang</groupId>
    <artifactId>spring-cloud-easy-config-starer</artifactId>
    <version>0.0.2</version>
</dependency>
```
springboot版本 >= 2.4.0
```xml
<dependency>
    <groupId>icu.liufuqiang</groupId>
    <artifactId>spring-cloud-easy-config-starer</artifactId>
    <version>0.1.0</version>
</dependency>
```
#### 项目配置 bootstrap.yml
```yaml
spring:
  cloud:
    easy-config:
      enabled: true
      url: jdbc:mysql://localhost:3306/config?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
      username: root
      password: 123456
```
#### 默认sql脚本
```sql
create DATABASE config;
-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
                           `id` int(0) NOT NULL AUTO_INCREMENT,
                           `data_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                           `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, 'dataId', 'test: \r\n       name: 12312311');
```
#### 使用方法
```java
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "test")
public class DynamicConfig {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DynamicConfig{" +
                "name='" + name + '\'' +
                '}';
    }
}
```
> 默认将会查询config表下面的content字段，作为配置内容，如果需要自定义查询sql，可以实现ConfigInterceptor

```java
public class CustomConfigInterceptor implements ConfigInterceptor {
    @Override
    public String configSql(String dataId) {
        return "select content from custom_table";
    }
}
```