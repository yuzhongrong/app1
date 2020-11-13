# transaction-springcloud 

是LCN基于springcloud的分布式事务框架

修改com.codingapi.tx.springcloud.listener.ServerListener 第28行

引入：
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>