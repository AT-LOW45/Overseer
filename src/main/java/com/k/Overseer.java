package com.k;

import com.k.config.SlashCommandInitialiser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

@Component
public class Overseer {

    public static JDA JDAinstance;
    private final Listener listener;

    public Overseer(Listener listener) {
        this.listener = listener;
    }

    public static JDA getJDAinstance() {
        if(Overseer.JDAinstance != null) {
            return Overseer.JDAinstance;
        } else {
            throw new NullPointerException("JDA hasn't started?");
        }
    }

    public void boot() throws LoginException {
        JDABuilder jdaBuilder = JDABuilder.createDefault(
                        SlashCommandInitialiser.get("token"),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS
                ).setActivity(Activity.watching("The Crown Tundra"))
                .addEventListeners(listener);

        CommandListUpdateAction cmdUpdate = jdaBuilder.build().updateCommands();
        SlashCommandInitialiser.initSlashCommands(cmdUpdate);
        JDAinstance = cmdUpdate.getJDA();
    }

}