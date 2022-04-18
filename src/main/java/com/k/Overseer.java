package com.k;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import javax.security.auth.login.LoginException;

public class Overseer {

    public static JDA JDAinstance;

    private Overseer() throws LoginException {

        JDABuilder jdaBuilder = JDABuilder.createDefault(
            Config.get("token"),
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS
        ).setActivity(Activity.watching("The Crown Tundra"))
                .addEventListeners(new Listener());

        CommandListUpdateAction cmdUpdate = jdaBuilder.build().updateCommands();
        Config.initSlashCommands(cmdUpdate);
        JDAinstance = cmdUpdate.getJDA();

    }

    public static void main(String[] args) throws LoginException {
        new Overseer();
    }

}


