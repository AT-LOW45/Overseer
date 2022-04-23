package com.k.commands;

import com.k.entities.game.Adventure;
import com.k.entities.game.Game;
import com.k.entities.game.GameHost;
import com.k.entities.game.PopCultureTrivia;
import com.k.utilities.APIService;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class AdventureCommand implements ICommand{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void handle(GuildMessageReceivedEvent messageReceivedEvent) {
        GameHost<Adventure<Game>> adventureGameHost = new Adventure<>();

    }
}
