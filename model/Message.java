package model;

import java.util.Date;

public class Message {
    private String messageContent;
    private Date timeStamp;
    private long offset;
    private boolean isProcessed;

    private long retentionTimeMillis;

    public Message(String messageContent, long offset, long retentionTimeMillis) {
        this.messageContent = messageContent;
        this.timeStamp = new Date();
        this.offset = offset;
        this.isProcessed = false;
        this.retentionTimeMillis = retentionTimeMillis;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public long getOffset() {
        return offset;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void markAsProcessed() {
        this.isProcessed = true;
    }
}
