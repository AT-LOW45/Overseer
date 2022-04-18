package com.k.commands;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.k.utilities.APIService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.awt.*;

@RequiredArgsConstructor
public class MemeCommand implements ICommand {

    private final ObjectMapper objectMapper;

    @Override
    public String getName() {
        return "meme";
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {

        event.getChannel().sendTyping().queue();
        String[] source = parseMessageArgs(event);
        APIService memeService = new APIService("https://meme-api.herokuapp.com/gimme");

        memeService.sendRequest("ef", false).serveRequest(response -> {
            try {
                JsonNode parsedResponse = objectMapper.readTree(response);

                EmbedBuilder memeEmbed = new EmbedBuilder();
                memeEmbed.setTitle(parsedResponse.get("title").asText())
                        .setDescription("Source: " + parsedResponse.get("postLink").asText())
                        .setImage(parsedResponse.get("url").asText())
                        .setColor(new Color(252, 186, 3));
                event.getChannel().sendMessageEmbeds(memeEmbed.build()).queue();

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                event.getChannel().sendMessage("Unable to get your fresh meme. This may be due to a server problem, " +
                        "please try again later").queue();
            }

        });
    }

    @Override
    public String[] parseMessageArgs(GuildMessageReceivedEvent event) {
        return ICommand.super.parseMessageArgs(event);
    }
}
