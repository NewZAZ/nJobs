package fr.newzproject.njobs.boosters;

import lombok.Getter;
import lombok.Setter;

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
