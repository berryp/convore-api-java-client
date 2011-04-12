package convore.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

public class Client {

    private static final String API_ROOT_PATH = "https://convore.com/api/";
    private static final String VERIFY_PATH = "account/verify.json";
    private static final String USER_PATH = "users/%s.json";
    private static final String GROUPS_PATH = "groups.json";
    private static final String TOPICS_PATH = "groups/%s/topics.json";
    private static final String MESSAGES_PATH = "topics/%s/messages.json";
    
    public static User initialize(final String username, final String password) {
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        });
        
        User user = getObject(User.class, VERIFY_PATH);
        return user;
    }
    
    public static <T> T getObject(Class<?> clazz, String path) {
        return getObject(clazz, path, new Object[]{});
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getObject(Class<?> clazz, String path, Object[] args) {
        Gson gson = new Gson();
        String json = callApi(String.format(path, args));

        Object obj = gson.fromJson(json, clazz);
        return (T)obj;
    }
    
    public static User getUser(int id) {
        Object[] args = {id};
        User user = getObject(User.class, USER_PATH, args);
        return user;
    }
    
    public static List<Group> getGroups() {
        GroupCollection groupCollection = getObject(
                GroupCollection.class, GROUPS_PATH);
        return groupCollection.groups;
    }
    
    public static List<Topic> getTopics(int groupId) {
        Object[] args = {groupId};
        TopicCollection topicCollection = getObject(
                TopicCollection.class, TOPICS_PATH, args);
        return topicCollection.topics;
    }
    
    public static List<Message> getMessages(int topicId) {
        Object[] args = {topicId};
        MessageCollection messageCollection = getObject(
                MessageCollection.class, MESSAGES_PATH, args);
        return messageCollection.messages;
    }

    private static String callApi(String path) {
        URL url = null;
        HttpsURLConnection urlConnection = null;
        StringBuffer content = new StringBuffer();
        
        try {
            url = new URL(API_ROOT_PATH + path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        
        if (url != null) {
            try {
                urlConnection = (HttpsURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            if (urlConnection != null) {
                try {
                    InputStream instream = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(instream));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        content.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
        }
        
        return content.toString();
    }

}