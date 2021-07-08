package fr.newzproject.njobs.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.newzproject.njobs.entity.JPlayer;
import fr.newzproject.njobs.jobs.Job;

import java.io.IOException;
import java.util.UUID;

public class JPlayerSerializer extends StdSerializer<JPlayer> {

    protected JPlayerSerializer() {
        super(JPlayer.class);
    }

    @Override
    public void serialize(JPlayer jPlayer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("uuid", jPlayer.getUuid());
        jsonGenerator.writeArrayFieldStart("jobs");
        for(Job jobs : jPlayer.getJobs()){
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("name",jobs.getJobName());
            jsonGenerator.writeNumberField("xp",jobs.getXp());
            jsonGenerator.writeNumberField("level",jobs.getCurrentLvl());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
