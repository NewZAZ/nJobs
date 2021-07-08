package fr.newzproject.njobs.storage.mongo;

public class DBCredentials {
    private final String host;
    private final String user;
    private final String pass;
    private final String dbName;

    public DBCredentials(String host, String user, String pass, String dbName) {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
    }

    public String getHost() {
        return host;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
