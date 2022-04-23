package com.k.commands;

import com.k.Config;
import com.k.entities.game.GameHost;
import com.k.entities.game.*;
import com.k.utilities.APIService;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PopCultureTriviaCommand implements ICommand{
    @Override
    public String getName() {
        return "anime";
    }

    @Override
    public String getDescription() {
        return "starts an anime trivia";
    }

    @Override
    public void handle(GuildMessageReceivedEvent messageReceivedEvent) {

        GameHost<PopCultureTrivia> pctGameHost = new Trivia<>(new PopCultureTrivia(), messageReceivedEvent.getAuthor());
        APIService triviaService = new APIService(Config.get("trivia"));

        triviaService.setParam("amount", String.valueOf(pctGameHost.getGameSessionType().getNoOfQuestions()))
                .setParam("type", pctGameHost.getGameSessionType().getQuestionType())
                .setParam("encode", pctGameHost.getGameSessionType().getEncoding())
                .buildURI();

        pctGameHost.prepareSession(messageReceivedEvent, "test without type");
        pctGameHost.getGameSessionType().prepareSession(messageReceivedEvent, "test with type");




    }
}
