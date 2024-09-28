# lahmacunMQ
Simulates the basic functions of a distributed messaging system.

# Descriptions:
Message Class: Stores the content, timestamp, offset value and whether or not each message has been processed.

messageContent: The content of the message.
timestamp: The creation time of the message.
offset: Determines the position of the message.
isProcessed: Tracks whether the message has been processed.
Node Class: Manages the state of each node in the distributed system and handles new leader election processes.

nodeName: Node name.
isLeader: Indicates whether the node is a leader or not.
alive: Indicates whether the node is up and running.
connectedNodes: Holds a list of connected nodes.
Partition Class: Defines the structure where messages are kept.

partitionId: Partition ID.
leaderNode: Partition leader node.
messageTail: The queue where messages are stored.
replicas: The backup nodes.
retentionTimeMillis: Determines how long messages are retained.
Topic Class: Defines the structure in which messages are grouped.

topicName: Topic name.
partitions: List of partitions belonging to the topic.
Consumer Class: Used to process messages.

partitionId: The partition to which the consumer is connected.
DistributedKafkaApp Class: The main entry point of the application. This is where nodes are created, messages are added and the consumer and message processing operations are performed.