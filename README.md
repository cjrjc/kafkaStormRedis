# kafkaStormRedis
[The version of applications]:
CentOS65
jdk1.8.0_121
storm1.1.1
zookeeper3.4.10
kafka2.12-1.0.0
redis4.0.5

[Cluster]:
CentOS65App
storm nimbus、redis

CentOS65M1、CentOS65M2、CentOS65M3
storm supervisor、zookeeper、kafka

[Functional description]:
storm1.1.1 consume the data from kafka_2.12-1.0.0, then save result to redis

[Order of execution]:
1、create zkRoot on zookeeper
zkCli.sh -server CentOS65M1:2181
create /kafka kafka
create /kafka/kafkaStormRedis kafkaStormRedis

2、create topic kafkaStormRedis
kafka-topics.sh --create --zookeeper CentOS65M1:2181 --replication-factor 3 --partitions 1 --topic kafkaStormRedis 

3、build project with maven 
we need the jar with dependences

4、submit topology to storm
storm jar /opt/soft/storm_jars/kafkaStormRedis-1.0-jar-with-dependencies.jar com.storm.topo.KafkaStormTopo

5、product data with the testcase WordsProducer.testSendKafkaStorm()

6、check result on redis
redis-cli
keys *
