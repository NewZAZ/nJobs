package fr.newzproject.njobs.boosters;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Booster {

    @Getter
    @Setter
    private double multiplier;
    @Getter
    @Setter
    private int time;

    public Booster() {
    }

}
