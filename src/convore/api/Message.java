package convore.api;

import java.util.Collection;

public class Message {

	public Collection<Embed> embeds;
	public User user;
	public Collection<Star> stars;
	public long date_created;
	public String message;
	public int id;
	
	public Message() {
	}
	
}
