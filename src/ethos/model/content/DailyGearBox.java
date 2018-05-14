package ethos.model.content;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ethos.event.CycleEvent;
import ethos.event.CycleEventContainer;
import ethos.event.CycleEventHandler;
import ethos.model.items.GameItem;
import ethos.model.items.ItemAssistant;
import ethos.model.players.Player;
import ethos.model.players.PlayerHandler;
import ethos.util.Misc;

/**
 * Revamped a simple means of receiving a random item based on chance.
 * 
 * @author Jason MacKeigan
 * @date Oct 29, 2014, 1:43:44 PM
 */
public class DailyGearBox extends CycleEvent {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 20703;

	/**
	 * A map containing a List of {@link GameItem}'s that contain items relevant to their rarity.
	 */
	private static Map<Rarity, List<GameItem>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(Rarity.COMMON, //
			Arrays.asList(
                new GameItem(12696, 50),
				new GameItem(13307, 200),  
				new GameItem(565, 2000),  
				new GameItem(560, 2500),  
				new GameItem(6129),  
				new GameItem(6130),  
				new GameItem(2579),  
				new GameItem(990, 5),  
				new GameItem(20703, 2),
				new GameItem(20791, 2),  
				new GameItem(4109),  
				new GameItem(4113),  
				new GameItem(4111),  
				new GameItem(1632, 20),  
				new GameItem(7460),
				new GameItem(6686, 50),   
				new GameItem(4131),
				new GameItem(386, 500),   
				new GameItem(995, 500000),   
				new GameItem(2801))
		);
		
	items.put(Rarity.UNCOMMON,
			Arrays.asList(
					new GameItem(13307, 500),
					new GameItem(6199, 1),   
					new GameItem(3025, 75),  
					new GameItem(11944, 50),  
					new GameItem(9244, 50),
					new GameItem(3842),
					new GameItem(12851),
					new GameItem(11773),
					new GameItem(12873),
					new GameItem(990, 15),
					new GameItem(20791, 3),
					new GameItem(20703, 3),   
					new GameItem(3840), 
					new GameItem(3844),   
					new GameItem(6524),  
					new GameItem(6528),  
					new GameItem(6522, 500),  
					new GameItem(3204),  
					new GameItem(20000),  
					new GameItem(4214),  
					new GameItem(9245, 50),  
					new GameItem(1377),  
					new GameItem(11230, 200),  
					new GameItem(4153),  
					new GameItem(995, 1000000),    
					new GameItem(2801, 3))
	);
		
		items.put(Rarity.RARE,
				Arrays.asList(
						new GameItem(11840),
						new GameItem(13307, 1500),
						new GameItem(10551),
						new GameItem(1409),
						new GameItem(13442, 500),
						new GameItem(4675),
						new GameItem(6199, 3),
						new GameItem(990, 25),
						new GameItem(6889),
						new GameItem(11230, 250),
						new GameItem(12696, 300),
						new GameItem(4151),
						new GameItem(6585),
						new GameItem(20791, 5),
						new GameItem(20703, 5),
						new GameItem(4716),
						new GameItem(4720),
						new GameItem(4723),
						new GameItem(4718),
						new GameItem(4708),
						new GameItem(4710),
						new GameItem(4712),
						new GameItem(4714),
						new GameItem(4753),
						new GameItem(4756),
						new GameItem(4759),
						new GameItem(4757),
						new GameItem(4745),
						new GameItem(4749),
						new GameItem(4751),
						new GameItem(4747),
						new GameItem(4724),
						new GameItem(4726),
						new GameItem(4728),
						new GameItem(4730),
						new GameItem(4732),
						new GameItem(4734),
						new GameItem(4736),
						new GameItem(4738),
						new GameItem(995, 3000000),
						new GameItem(2722, 3)));
	}

	/**
	 * The player object that will be triggering this event
	 */
	private Player player;

	/**
	 * Constructs a new myster box to handle item receiving for this player and this player alone
	 * 
	 * @param player the player
	 */
	public DailyGearBox(Player player) {
		this.player = player;
	}

	/**
	 * Opens a mystery box if possible, and ultimately triggers and event, if possible.
	 * 
	 * @param player the player triggering the evnet
	 */
	public void open() {
		if (System.currentTimeMillis() - player.lastMysteryBox < 150 * 4) {
			return;
		}
		if (player.getItems().freeSlots() < 2) {
			player.sendMessage("You need atleast two free slots to open a mystery box.");
			return;
		}
		if (!player.getItems().playerHasItem(MYSTERY_BOX)) {
			player.sendMessage("You need a daily gear box to do this.");
			return;
		}
		player.getItems().deleteItem(MYSTERY_BOX, 1);
		player.lastMysteryBox = System.currentTimeMillis();
		CycleEventHandler.getSingleton().stopEvents(this);
		CycleEventHandler.getSingleton().addEvent(this, this, 2);
	}

	/**
	 * Executes the event for receiving the mystery box
	 */
	@Override
	public void execute(CycleEventContainer container) {
		if (player.disconnected || Objects.isNull(player)) {
			container.stop();
			return;
		}
		int random = Misc.random(10);
		List<GameItem> itemList = random < 5 ? items.get(Rarity.COMMON) : random >= 5 && random <= 8 ? items.get(Rarity.UNCOMMON) : items.get(Rarity.RARE);
		GameItem item = Misc.getRandomItem(itemList);
		GameItem itemDouble = Misc.getRandomItem(itemList);
		
		if (Misc.random(200) == 1) {
			PlayerHandler.executeGlobalMessage("[<col=CC0000>Daily Box</col>] @cr20@ <col=255>" + player.playerName
					+ "</col> hit the jackpot on a Daily Gear Box!");
			switch(Misc.random(2)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(12004, 1);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(11286, 1);
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(11907, 1);
				break;
			}
		}

		if (Misc.random(25) == 0) {
			player.getItems().addItem(item.getId(), item.getAmount());
			player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
			player.sendMessage("You receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col>.");
		} else {
			//player.getItems().addItem(995, coins);
			player.getItems().addItem(item.getId(), item.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
		}
		container.stop();
	}

	/**
	 * Represents the rarity of a certain list of items
	 */
	enum Rarity {
		UNCOMMON, COMMON, RARE
	}

}