package com.k;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class Config {

    private static final Dotenv dotenv = Dotenv.load();

    public static String get(String key) {
        return dotenv.get(key.toUpperCase());
    }



    public static void initSlashCommands(CommandListUpdateAction cmdUpdate) {

        cmdUpdate.addCommands(new CommandData("poll", "creates a new poll")
                .addOptions(new OptionData(OptionType.STRING,
                        "title",
                        "the title of the poll")
                        .setRequired(true))
                .addOptions(new OptionData(OptionType.INTEGER,
                        "duration",
                        "duration of the poll")
                        .setRequired(true))
                .addOptions(new OptionData(OptionType.STRING,
                        "anonymous",
                        "let others know voters' choices?")
                        .addChoice("Yes", "true")
                        .addChoice("No", "false").setRequired(true))
                .addOptions(new OptionData(OptionType.STRING,
                        "option1",
                        "a poll option (required)")
                        .setRequired(true))
                .addOptions(new OptionData(OptionType.STRING,
                        "option2",
                        "a poll option (required)")
                        .setRequired(true))
                .addOptions(new OptionData(OptionType.STRING,
                        "option3",
                        "a poll option")
                        .setRequired(false))
                .addOptions(new OptionData(OptionType.STRING,
                        "option4",
                        "a poll option").
                        setRequired(false))
                .addOptions(new OptionData(OptionType.STRING,
                        "option5",
                        "a poll option")
                        .setRequired(false))).complete();

        cmdUpdate.addCommands(new CommandData("endpoll", "ends the ongoing poll")
                .addOptions(new OptionData(
                        OptionType.STRING,
                        "polltoken",
                        "the poll token required to end the poll")
                        .setRequired(true))).complete();
    }
}
