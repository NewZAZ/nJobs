package fr.newzproject.njobs.storage.json;

import org.json.simple.JSONObject;

public class JObject {
    private final JSONObject object;

    public JObject(final JSONObject object) {
        this.object = object;
    }

    public Integer getInt(final String key) {
        return JSONReader.intFromObject(this.getObject().get(key));
    }

    public String getString(final String key) {
        return JSONReader.stringFromObject(this.getObject().get(key));
    }

    public Double getDouble(final String key) {
        return JSONReader.doubleFromObject(this.getObject().get(key));
    }

    public Long getLong(final String key) {
        return JSONReader.longFromObject(this.getObject().get(key));
    }

    public JSONObject getObject() {
        return this.object;
    }
}
