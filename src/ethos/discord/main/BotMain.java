package ethos.discord.main;

import java.awt.Color;

import ethos.DiscordToken;
import ethos.model.players.Player;
import ethos.model.players.skills.Skill;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;

@SuppressWarnings("all")
public class BotMain {
	
	public static final String token = DiscordToken.token;
	
	public static final long channelID = 458715499556110344L;
	public static final long guildID = 418458996941258752L;
	
	public static final IDiscordClient bot = createClient(token, true);
	
	public static void main(String args[]) {
		System.out.println(bot.getApplicationClientID());
		EventDispatcher dis = bot.getDispatcher();
		dis.registerListener(new BotListener());
	}
	
	public static IDiscordClient createClient(String token, boolean login)
	{
		ClientBuilder clientBuilder = new ClientBuilder();
		clientBuilder.withToken(token);
		try {
			if(login)
				return clientBuilder.login();
			return clientBuilder.build();
		} catch (DiscordException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void sendFeed(Player c, String msg, int feedType, String[] extra) {
		IChannel feedChannel = bot.getChannelByID(channelID);
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.withTimestamp(System.currentTimeMillis());
		
		String skillName = "";
		IEmoji emoji;
		
		Long discordID = 1L;
		IUser user = null;
		
		if(c.getDiscord() != null) {
			discordID = Long.parseLong(c.getDiscord());
			user = bot.getUserByID(discordID);
		}
		
		if(user != null) {
			builder.withAuthorIcon(user.getAvatarURL());
			builder.withAuthorName(user.getDisplayName(bot.getGuildByID(guildID)));
			builder.withColor(Color.decode("#" + c.getRights().getPrimary().getColor()));
		}
		
		switch (feedType) {
			case 0: // Level up 99
				skillName = Skill.forId(Integer.parseInt(extra[0])).name().toLowerCase();
				skillName = skillName.substring(0, 1).toUpperCase() + skillName.substring(1);
				emoji = bot.getGuildByID(guildID).getEmojiByName(skillName);
				builder.appendField(emoji + " Level Up!", "**" + c.getName() + "** has reached level 99 in " + skillName, false);
				break;
			case 1: // Level up 120
				skillName = Skill.forId(Integer.parseInt(extra[0])).name().toLowerCase();
				skillName = skillName.substring(0, 1).toUpperCase() + skillName.substring(1);
				emoji = bot.getGuildByID(guildID).getEmojiByName(skillName);
				builder.appendField(emoji + " Level Up!", "**" +  c.getName() + "** has reached level 120 in " + skillName, false);
				break;
			case 2: // 99 Everything
				emoji = bot.getGuildByID(guildID).getEmojiByName("Overall");
				builder.appendField(emoji + " Maxed!", "**" +  c.getName() + "** has reached level 99 in **ALL** skills", false);
				break;
			case 3: // Rare Drop
				emoji = bot.getGuildByID(guildID).getEmojiByName("Money");
				builder.appendField(emoji + " Rare Drop!", "**" +  c.getName() + "** just found " + Integer.parseInt(extra[0]) + " x " + extra[1], false);
				break;
			case 4: // Pet
				emoji = bot.getGuildByID(guildID).getEmojiByName("Pet");
				builder.appendField(emoji + " Pet!", "**" +  c.getName() + "** just got a " + extra[0] + " pet", false);
				break;
			case 5: // Update!
				emoji = bot.getGuildByID(guildID).getEmojiByName("Clock");
				builder.appendField(emoji + " Update!", "**" +  c.getName() + "** has set an update for " + extra[0] + " seconds", false);
				break;				
		}
		
		feedChannel.sendMessage(builder.build());
		
	}
	
	public static void startingUp() {
		bot.changePresence(StatusType.ONLINE, null, "Server starting up");
	}
	
	public static void newPlayer(int counter) {
		bot.changePresence(StatusType.ONLINE, null, "Players online: " + counter);
	}
	
}
