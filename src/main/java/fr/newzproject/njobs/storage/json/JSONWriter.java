package fr.newzproject.njobs.storage.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {
    JSONArray objects;

    public JSONWriter() {
        this.objects = new JSONArray();
    }

    public void addObject(final JSONObject obj) {
        this.objects.add(obj);
    }

    public void writeFile(final File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            final FileWriter writer = new FileWriter(file);
            writer.write(this.objects.toJSONString());
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        this.objects.clear();
    }
}
