public class DistributedApp {
    public static void main(String[] args) throws InterruptedException {

        long retentionTimeMillis = 10 * 1000;

        Node node1 = new Node("Node 1", true, retentionTimeMillis);
        Node node2 = new Node("Node 2", false, retentionTimeMillis);


        node1.addOtherNode(node2);

        node2.addOtherNode(node1);



        String content = "message";

        Message message = new Message(content,0L, retentionTimeMillis);
        Message message2 = new Message(content,1L, retentionTimeMillis);
        Message message3 = new Message(content,2L, retentionTimeMillis);

        int partitionId1 = PartitioningStrategy.getPartitionForKey(message.getMessageContent(), 3);
        int partitionId2 = PartitioningStrategy.getPartitionForKey(message2.getMessageContent(), 3);
        int partitionId3 = PartitioningStrategy.getPartitionForKey(message3.getMessageContent(), 3);


        Topic myTopic = new Topic("MyTopic", 2, node1, retentionTimeMillis);


        myTopic.addMessage(message.getMessageContent(), 0);

        Consumer consumer = new Consumer(0);
        consumer.processMessage(myTopic);

    }
}
