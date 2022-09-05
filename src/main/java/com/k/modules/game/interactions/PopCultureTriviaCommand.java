package com.k.modules.game.interactions;

import java.util.List;

import com.k.commands.IMessageCommand;
import com.k.config.SlashCommandInitialiser;
import com.k.modules.game.Game;
import com.k.modules.game.GameHost;
import com.k.modules.game.PopCultureTrivia;
import com.k.utilities.APIService;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PopCultureTriviaCommand implements IMessageCommand {
    @Override
    public String getName() {
        return "pop trivia";
    }

    @Override
    public String getDescription() {
        return "starts an anime trivia";
    }

    @Override
    public void handle(MessageReceivedEvent messageReceivedEvent) {

        GameHost pctGameHost = new PopCultureTrivia(new Game(List.of(messageReceivedEvent.getAuthor()), 5));

        
        // GameHost<Trivia<PopCultureTrivia>> pctGameHost = new Trivia<>(new PopCultureTrivia(), messageReceivedEvent.getAuthor());
        APIService triviaService = new APIService(SlashCommandInitialiser.get("trivia"));

        // triviaService.setParam("amount", String.valueOf(pctGameHost.getGameSessionType().getNoOfQuestions()))
        //         .setParam("type", pctGameHost.getGameSessionType().getQuestionType())
        //         .setParam("encode", pctGameHost.getGameSessionType().getEncoding())
        //         .buildURI();

        
    }
}
