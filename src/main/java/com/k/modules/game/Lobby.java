package com.k.modules.game;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;


@Getter
@Setter
public class Lobby {

    private List<User> players;
    private int allowedIdleTimeInSeconds;
    

    public Lobby(List<User> players, int allowedIdleTimeInSeconds) {
        this.players = players;
        this.allowedIdleTimeInSeconds = allowedIdleTimeInSeconds;
        
    }

}
