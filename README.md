Using Kafka Version ~ "kafka_2.12-3.9.1"

Start Kafka before running Application.
Steps ==>
Step 1 - bin/zookeeper-server-start.sh config/zookeeper.properties
Step 2 - bin/kafka-server-start.sh config/server.properties
Step 3 - bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

Splunk Logs ~
Notification Service Index - "notification_service_dev"
Product Service Index - "product_service_dev"
