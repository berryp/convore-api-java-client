package convore.api;

public class Group {
    
    public int topics_count;
    public User creator;
    public boolean is_member;
    public boolean is_admin;
    public int id;
    public String kind;
    public int members_count;
    public String name;
    public String url;
    public String slug;
    public long date_latest_message;
    public long date_created;
    public int unread;
    
    public Group() {
    }

}
