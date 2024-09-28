import consumer.Consumer;
import core.Node;
import model.Message;
import model.Topic;

public class DistributedApp {
    public static void main(String[] args) throws InterruptedException {

        long retentionTimeMillis = 10 * 1000;

        Node node1 = new Node("Node 1", true, retentionTimeMillis);
        Node node2 = new Node("Node 2", false, retentionTimeMillis);


        node1.addToConnectedNodes(node2);
        node2.addToConnectedNodes(node1);

        String content = "message";
        String content2 = "message2";
        String content3 = "message3";

        Message message = new Message(content, 0L, retentionTimeMillis);
        Message message2 = new Message(content2, 1L, retentionTimeMillis);
        Message message3 = new Message(content3, 2L, retentionTimeMillis);

        // int partitionId1 = PartitioningStrategy.getPartitionForKey(message.getMessageContent(), 3);
        // int partitionId2 = PartitioningStrategy.getPartitionForKey(message2.getMessageContent(), 3);
        // int partitionId3 = PartitioningStrategy.getPartitionForKey(message3.getMessageContent(), 3);


        Topic myTopic = new Topic("MyTopic", 3, node1, retentionTimeMillis);


        myTopic.addMessage(message, 0);
        myTopic.addMessage(message2, 1);
        myTopic.addMessage(message3, 2);

        Consumer consumer = new Consumer(0);
        consumer.processMessage(myTopic);
    }
}
