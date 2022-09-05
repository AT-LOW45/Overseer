package com.k.commands;

import java.awt.Color;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.k.config.SlashCommandInitialiser;
import com.k.utilities.APIService;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

//@RequiredArgsConstructor
@Component
public class MemeCommand implements IMessageCommand {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getName() {
        return "meme";
    }

    @Override
    public String getDescription() {
        return "retrieves a random meme (source can be optionally provided)";
    }

    @Override
    public void handle(MessageReceivedEvent event) {

        event.getChannel().sendTyping().queue();
        String[] source = parseMessageArgs(event);
        APIService memeService = new APIService(SlashCommandInitialiser.get("meme"));

        memeService.buildURI();

        memeService.sendRequest().serveRequest(response -> {
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
    public String[] parseMessageArgs(MessageReceivedEvent event) {
        return IMessageCommand.super.parseMessageArgs(event);
    }
}
