package joecord.seal.clapbot;

import joecord.seal.clapbot.commands.CommandHandler;
import joecord.seal.clapbot.commands.EchoCommand;
import joecord.seal.clapbot.commands.NotACultCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class ClapBot {

    private static ClapBot instance;

    public static ClapBot getInstance() {
        return instance;
    }

    private CommandHandler commandHandler;
    private String token;
    private JDA api;

    public ClapBot(String token){
        instance = this;
        this.token = token;
        System.out.println(token);
        this.commandHandler = new CommandHandler("!");
        this.commandHandler.registerCommand(new EchoCommand());
        this.commandHandler.registerCommand(new NotACultCommand());
        try {
            this.api = buildAPI();
        } catch (LoginException e) {
            System.out.println("Error creating bot API");
            System.out.println(e);
            System.exit(0);
        }
    }

    public ClapBot(){
        this(System.getenv("DISCORD_CLAPBOT_TOKEN"));
    }

    public JDA buildAPI() throws LoginException {
        return JDABuilder.createDefault(this.token)
                .setActivity(Activity.playing("TTT"))
                .setStatus(OnlineStatus.IDLE)
                .addEventListeners(new MessageListener(this.commandHandler))
                .build();
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public JDA getApi() {
        return api;
    }
}
