package fr.newzproject.njobs.storage.mongo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import fr.newzproject.njobs.entity.JPlayer;
import fr.newzproject.njobs.storage.mongo.Mongo;

import java.util.Optional;
import java.util.stream.Stream;

public class JobsMongoService implements MongoService<JPlayer> {
    private final MongoCollection<BasicDBObject> collection;
    private final ObjectMapper mapper;

    public JobsMongoService() {
        this.collection = Mongo.getInstance().getJobsCollection();
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
    }

    @Override
    public void set(JPlayer object) throws JsonProcessingException {
        Optional<BasicDBObject> dbObject = Optional.ofNullable(collection.find(new BasicDBObject("uuid", object.getUuid())).first());

        if (dbObject.isPresent())
            collection.updateOne(dbObject.get(),
                    new BasicDBObject("$set", BasicDBObject.parse(mapper.writeValueAsString(object))));
        else
            collection.insertOne(BasicDBObject.parse(mapper.writeValueAsString(object)));
    }

    @Override
    public void set(Stream<JPlayer> objects) {
        objects.forEach(person -> {
            try {
                set(person);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Optional<JPlayer> get(BasicDBObject basicDBObject) {
        Optional<BasicDBObject> dbObject = Optional.ofNullable(collection.find(basicDBObject).first());
        return dbObject.map(object -> {
            try {
                return mapper.readValue(object.toJson(), JPlayer.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    @Override
    public void delete(JPlayer object) {
        Optional<BasicDBObject> dbObject = Optional.ofNullable(collection.find(new BasicDBObject("uuid", object.getUuid())).first());
        dbObject.ifPresent(collection::findOneAndDelete);
    }

    @Override
    public Stream<JPlayer> getWithFilter(BasicDBObject basicDBObject) throws JsonProcessingException {
        Stream.Builder<JPlayer> personBuilder = Stream.builder();
        for (BasicDBObject object : collection.find(basicDBObject))
            personBuilder.add(mapper.readValue(object.toJson(), JPlayer.class));

        return personBuilder.build();
    }

    @Override
    public Stream<JPlayer> get() throws JsonProcessingException {
        return getWithFilter(new BasicDBObject());
    }

    @Override
    public boolean isExist(BasicDBObject basicDBObject) {
        return Optional.ofNullable(collection.find(basicDBObject).first()).isPresent();
    }
}
