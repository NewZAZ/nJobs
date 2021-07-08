package fr.newzproject.njobs.storage.mongo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;

import java.util.Optional;
import java.util.stream.Stream;

public interface MongoService<T> {

    void set(T object) throws JsonProcessingException;
    void set(Stream<T> objects);

    Optional<T> get(BasicDBObject basicDBObject);
    void delete(T object);
    Stream<T> getWithFilter(BasicDBObject basicDBObject) throws JsonProcessingException;
    Stream<T> get() throws JsonProcessingException;
    boolean isExist(BasicDBObject basicDBObject);
}
