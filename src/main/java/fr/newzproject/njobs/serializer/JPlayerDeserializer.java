package fr.newzproject.njobs.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import fr.newzproject.njobs.entity.JPlayer;
import fr.newzproject.njobs.jobs.Job;

import java.io.IOException;
import java.util.*;

public class JPlayerDeserializer extends StdDeserializer<JPlayer> {

    protected JPlayerDeserializer() {
        super(JPlayer.class);
    }

    @Override
    public JPlayer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String uuid = node.get("uuid").asText();
        System.out.println("UUID : "+uuid);
        System.out.println("JSON : "+node.get("jobs").textValue());
        System.out.println("JSON2 : "+node.get("jobs").toString());
        System.out.println("JSON3 : "+node.get("jobs").toPrettyString());

        System.out.println("test : "+node.toString());
        return new JPlayer(UUID.randomUUID());
    }
}
