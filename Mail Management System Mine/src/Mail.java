import java.util.*;

public class Mail{
    private String sender;
    private String receiver;
    private String subject;
    private String content;
    private List<String> tags;
    private boolean isSpam;

    public Mail(String sender, String receiver, String subject, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.tags = new ArrayList<>();
        this.isSpam = false;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean isSpam() {
        return isSpam;
    }

    public void setSpam(boolean spam) {
        isSpam = spam;
    }

    public void addTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    // Add this inside Mail.java
    public boolean matches(String query) {
        String q = query.toLowerCase();

        // Check simple string fields
        if (sender.toLowerCase().contains(q)) return true;
        if (receiver.toLowerCase().contains(q)) return true;
        if (subject.toLowerCase().contains(q)) return true;
        if (content.toLowerCase().contains(q)) return true;

        // Check tags (Classic loop is cleaner here than a stream)
        for (String tag : tags) {
            if (tag.toLowerCase().contains(q)) return true;
        }

        return false;
    }

    // Add inside Mail.java
    public boolean matchesWildcard(String regexPattern) {
        // We assume the pattern is already lowercased and formatted as Regex

        // Check text fields
        if (sender.toLowerCase().matches(regexPattern)) return true;
        if (receiver.toLowerCase().matches(regexPattern)) return true;
        if (subject.toLowerCase().matches(regexPattern)) return true;
        if (content.toLowerCase().matches(regexPattern)) return true;

        // Check tags (Classic loop)
        for (String tag : tags) {
            if (tag.toLowerCase().matches(regexPattern)) return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "From: " + sender + "\n" +
                "To: " + receiver + "\n" +
                "Subject: " + subject + "\n" +
                "Content: " + content + "\n" +
                "Tags: " + tags + "\n" +
                "Spam: " + isSpam;
    }
}