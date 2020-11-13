# tx-manager

LCN 分布式事务协调器

创建zip安装包

`mvn clean install `
修改parent：spring boot --> tx-lcn
修改spring cloud版本：Dalston.SR1 --> Finchley.SR2
修改对应的eureka-server依赖
修改服务端口server.port
修改注册中心地址
修改redis ip地址
com.codingapi.tm.ServletInitializer 重新导包