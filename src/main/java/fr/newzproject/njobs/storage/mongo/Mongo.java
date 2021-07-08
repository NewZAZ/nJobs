package fr.newzproject.njobs.storage.mongo;


import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Collections;

public class Mongo {
    private static Mongo instance;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    @Getter
    private MongoCollection<BasicDBObject> jobsCollection;

    private Mongo() {
        instance = this;
    }

    public void connect(DBCredentials dbCredentials) {
        MongoCredential mongoCredential = MongoCredential.createCredential(dbCredentials.getUser(), dbCredentials.getDbName(), dbCredentials.getPass().toCharArray());

        mongoClient = new MongoClient(new ServerAddress(dbCredentials.getHost(), 27017), Collections.singletonList(mongoCredential));
        mongoDatabase = mongoClient.getDatabase(dbCredentials.getDbName());
        jobsCollection = mongoDatabase.getCollection("jobs", BasicDBObject.class);

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Connected to MongoDB !");

    }

    public static Mongo getInstance() {
        return instance == null ? instance = new Mongo() : instance;
    }
}
