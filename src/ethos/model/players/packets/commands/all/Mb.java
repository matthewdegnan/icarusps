package ethos.model.players.packets.commands.all;

import java.util.Optional;

import ethos.Server;
import ethos.model.players.Player;
import ethos.model.players.packets.commands.Command;

/**
 * Teleport the player to the mage bank.
 * 
 * @author Emiel
 */
public class Mb extends Command {

	@Override
	public void execute(Player c, String input) {
		if (Server.getMultiplayerSessionListener().inAnySession(c)) {
			return;
		}
		if (c.inClanWars() || c.inClanWarsSafe()) {
			c.sendMessage("@cr10@You can not teleport from here, speak to the doomsayer to leave.");
			return;
		}
		if (c.inWild()) {
			return;
		}
		c.getPA().spellTeleport(2539, 4716, 0);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teleports you to the mage bank");
	}

}
