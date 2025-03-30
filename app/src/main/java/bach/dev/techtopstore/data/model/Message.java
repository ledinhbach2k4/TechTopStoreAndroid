package bach.dev.techtopstore.data.model;

public class Message {
    private String text;
    private String senderId;
    private String senderName;
    private String timestamp;
    private boolean isAdmin;

    // Empty constructor required for Firebase
    public Message() {}

    // Getters and setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }
}