# zxx-generator
This is a tool for generating java code, including web and Vue generation templates

## 使用

- pom.xml

```xml
    <dependency>
        <groupId>com.github.zhaoxxnbsp</groupId>
        <artifactId>zxx-generator</artifactId>
        <version>1.0.0</version>
    </dependency>
```

- application.yml

```yaml
zxx:
  generator:
    tableNames:
      - 'zm_teacher'

# mysql
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    #MySQL配置
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/testdb?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: root

```

- TestController.java

```java
    @Autowired
    GeneratorService generatorService;
    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(HttpServletResponse response) throws IOException {
        byte[] data = generatorService.code();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"renren.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
```

- 下载生成的代码包

```shell script
http://localhost:8080/code
```
