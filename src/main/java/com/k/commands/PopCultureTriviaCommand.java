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

        GameHost<PopCultureTrivia> animeTriviaGameHost = new Trivia<>(new PopCultureTrivia(), messageReceivedEvent.getAuthor());
        APIService triviaService = new APIService(Config.get("trivia"));

        triviaService.setParam("amount", String.valueOf(animeTriviaGameHost.getGameType().getNoOfQuestions()))
                .setParam("type", animeTriviaGameHost.getGameType().getQuestionType())
                .setParam("encode", animeTriviaGameHost.getGameType().getEncoding())
                .buildURI();



    }
}
