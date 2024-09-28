public class Consumer {
    private long lastProcessedOffset;
    private int partitionId;

    public Consumer(int partitionId) {
        this.partitionId = partitionId;
        this.lastProcessedOffset = -1;
    }

    public void processMessage(Topic topic) {
        long offset = this.lastProcessedOffset;
        long nextOffset = getLastProcessedOffset() + 1;
        Message message = topic.getMessage(partitionId, nextOffset);
        if (message != null) {
            System.out.println("Consumer processed the message " + message.getMessageContent() + " from partition: " + partitionId);
            lastProcessedOffset = message.getOffset();
            if (nextOffset <= offset) {
                throw new RuntimeException("Mismatch offset: " + offset);
            }
        } else {
            System.out.println("No new message in partition.");
        }
    }

    public long getLastProcessedOffset() {
        return lastProcessedOffset;
    }
}
