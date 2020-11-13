##使用：
将依赖加入pom文件
```

<dependency>
    <groupId>com.blockchain</groupId>
    <artifactId>blockchain-server-base</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>

```

##注意事项：
1. 需要将MessagesBundle_default.properties内容拷贝到相应国际化文件，目录位于resource下面的i18n文件夹
2. 服务端使用@SpringBootApplication(scanBasePackageClasses={BaseConf.class})将所有配置扫描并交给到spring容器管理
3. 如果不需要Xss拦截，可以使用@SpringBootApplication(scanBasePackageClasses={BaseConf.class},exclude={XssConfig.class})
4. 每个微服务应该设置自己独立的登录拦截器,指定哪些接口需要登录才能使用，哪些接口不需要
5. 对于分布式事务，发起方的service需要加上注解@TxTransaction(isStart = true)，
   接收方的service需要实现ITxTransaction，并使用@Transactional
6. 微服务文件目录固定为
```
   com.blockchain.server.服务名
       --common
           --conf 登录拦截器、自定义配置等
           --
           ...
       --controller
       --service
       --feign
       --mapper
       --entity
       --dto
       ...
```

