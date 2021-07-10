package fr.newzproject.njobs.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.newzproject.njobs.boosters.Booster;
import fr.newzproject.njobs.jobs.Job;
import fr.newzproject.njobs.serializer.JPlayerDeserializer;
import fr.newzproject.njobs.serializer.JPlayerSerializer;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@JsonDeserialize(using = JPlayerDeserializer.class)
@JsonSerialize(using = JPlayerSerializer.class)
public class JPlayer {

    @Getter
    private final Set<Job> jobs = new HashSet<>();
    private ObjectId _id;
    @Getter
    private UUID uuid;
    @Setter
    private Booster booster;

    public JPlayer() {
    }

    public JPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public Optional<Booster> getBooster() {
        return Optional.ofNullable(booster);
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }
}
