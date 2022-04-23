package com.k.entities.game;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;

@Getter
@Setter
public class Game {

    protected User player;
    protected boolean isHosting;
    protected int allowedIdleTimeInSeconds;

    protected void kickPlayer(String reason) {

    }

    protected void endGame() {

    }


}
