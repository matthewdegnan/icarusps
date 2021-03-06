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
public class DailySkillBox extends CycleEvent {

	/**
	 * The item id of the mystery box required to trigger the event
	 */
	public static final int MYSTERY_BOX = 20791;

	/**
	 * A map containing a List of {@link GameItem}'s that contain items relevant to their rarity.
	 */
	private static Map<Rarity, List<GameItem>> items = new HashMap<>();

	/**
	 * Stores an array of items into each map with the corresponding rarity to the list
	 */
	static {
		items.put(Rarity.COMMON, 
			Arrays.asList(
				new GameItem(11849, 50 + Misc.random(30)),// mark of grace
				new GameItem(1518, 100 + Misc.random(50)),// maple logs-noted
				new GameItem(450, 100 + Misc.random(50)),// adamant ore-noted
				new GameItem(2360, 100 + Misc.random(50)),// mithril bar-noted
				new GameItem(2362, 75 + Misc.random(50)),// adamant bar-noted
				new GameItem(264, 50 + Misc.random(50)),// whine of zammy-noted
				new GameItem(3001, 50 + Misc.random(50)),// snapdragon-noted
				new GameItem(63, 150 + Misc.random(100)),// maple longbow (u)-noted
				new GameItem(266, 50 + Misc.random(50)),// cadantine-noted
				new GameItem(2506, 150 + Misc.random(100)),// blue-dragonhide leather-noted
				new GameItem(2508, 100 + Misc.random(100)),// red-dragonhide leather-noted
				new GameItem(258, 75 + Misc.random(50)),// grimy rannarr-noted 
				new GameItem(1632, 10 + Misc.random(50)),// uncut dragonstone-noted
				new GameItem(824, 1000 + Misc.random(300)),// rune dart tip
				new GameItem(454, 400 + Misc.random(200)),// coal noted
				new GameItem(13226, 1),// herbsack
				new GameItem(314, 10000),// feather
				new GameItem(1778, 3000),// noted bow string
				new GameItem(445, 250),// noted gold ore
				new GameItem(1516, 100 + Misc.random(50)),// yew logs-noted
				new GameItem(7409),// magic secatures
				new GameItem(5295, 50 + Misc.random(50)),//rannar seed
				new GameItem(5300, 50 + Misc.random(50)),// snapdragon seed
				new GameItem(5296, 50 + Misc.random(50)),// toadflax seed
				new GameItem(5304, 50 + Misc.random(50)),// torstol seed
				new GameItem(384, 300 + Misc.random(150)),// raw sharks-noted
				new GameItem(7937, 1000 + Misc.random(350))// pure essence noted
		));
		
	items.put(Rarity.UNCOMMON,
			Arrays.asList(
					new GameItem(11849, 100 + Misc.random(50)),// mark of grace 
					new GameItem(1518, 200 + Misc.random(100)),// noted uncut diamonds
					new GameItem(450, 150 + Misc.random(100)),// adamant ore-noted
					new GameItem(2360, 150 + Misc.random(100)),// mtihril bar-noted
					new GameItem(452, 100 + Misc.random(100)),// runite ore-noted
					new GameItem(441, 500 + Misc.random(200)),// iron ore-noted
					new GameItem(11935, 250 + Misc.random(250)),// raw dark crab-noted
					new GameItem(2364, 50 + Misc.random(50)),// rune bar-noted
					new GameItem(100, 100 + Misc.random(50)),// unf-rannar-noted
					new GameItem(112, 50 + Misc.random(50)),// unf torstol-noted
					new GameItem(3005, 75 + Misc.random(50)),// unf snapdragon-noted
					new GameItem(71, 100 + Misc.random(100)),// magic longbow(u)-noted
					new GameItem(3003, 100 + Misc.random(50)),// unf-toadflax-noted
					new GameItem(2362, 100 + Misc.random(100)),// adamant bar-noted
					new GameItem(3001, 75 + Misc.random(50)),// snapdragon-noted
					new GameItem(2510, 150 + Misc.random(100)),// black dragon leather-noted
					new GameItem(2508, 150 + Misc.random(100)),// red-dragonhide-leather-noted
					new GameItem(1632, 20 + Misc.random(50)),// uncut dragonstone-noted
					new GameItem(1514, 150 + Misc.random(100)),// magic logs-noted
					new GameItem(67, 150 + Misc.random(100)),// yew longbow(u)-noted
					new GameItem(2801, 5)// medium clue scroll
	));
		
		items.put(Rarity.RARE,
				Arrays.asList(
						new GameItem(1514, 250 + Misc.random(250)),// magic logs-noted
						new GameItem(71, 300 + Misc.random(200)),// mgic longbow(u)-noted
						new GameItem(13440, 500 + Misc.random(300)),// raw-anglerfish-noted
						new GameItem(11232, 1000 + Misc.random(1000)),// dragon dar tips
						new GameItem(452, 150 + Misc.random(100)),// rune ore-noted
						new GameItem(775),// cooking gauntlets
						new GameItem(5304, 50 + Misc.random(50)),// torstol seed
						new GameItem(112, 250 + Misc.random(250)),// unf torstol-noted
						new GameItem(454, 500 + Misc.random(300)),// coal noted
						new GameItem(445, 1000 + Misc.random(500)),// noted gold ore
						new GameItem(2364, 100 + Misc.random(100)),// rune bars-noted
						new GameItem(1624, 25 + Misc.random(100)),// uncut sapphire-noted
						new GameItem(2482, 25 + Misc.random(100)),// noted lantadyme
						new GameItem(268, 25 + Misc.random(100)),// dwarf-weed-noted
						new GameItem(270, 25 + Misc.random(100)),// torstol-noted
						new GameItem(2510, 50 + Misc.random(100)),// black dragonhide leather-leathr
						new GameItem(2722, 5)));// hard clue scroll
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
	public DailySkillBox(Player player) {
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
			PlayerHandler.executeGlobalMessage("[<col=CC0000>Skill Box</col>] @cr18@ <col=255>" + player.playerName
					+ "</col> hit the jackpot on a Skill Box!");
			switch(Misc.random(21)) {
			case 0:
				player.getItems().addItemUnderAnyCircumstance(1632, 500);
				break;
			case 1:
				player.getItems().addItemUnderAnyCircumstance(13646, 1);
				break;
			case 2:
				player.getItems().addItemUnderAnyCircumstance(13640, 1);
				break;
			case 3:
				player.getItems().addItemUnderAnyCircumstance(13642, 1);
				break;
			case 4:
				player.getItems().addItemUnderAnyCircumstance(13644, 1);
				break;
			case 5:
				player.getItems().addItemUnderAnyCircumstance(10941, 1);
				break;
			case 6:
				player.getItems().addItemUnderAnyCircumstance(10940, 1);
				break;
			case 7:
				player.getItems().addItemUnderAnyCircumstance(10939, 1);
				break;
			case 8:
				player.getItems().addItemUnderAnyCircumstance(10933, 1);
				break;
			case 9:
				player.getItems().addItemUnderAnyCircumstance(12013, 1);
				break;
			case 10:
				player.getItems().addItemUnderAnyCircumstance(12015, 1);
				break;
			case 11:
				player.getItems().addItemUnderAnyCircumstance(12014, 1);
				break;
			case 12:
				player.getItems().addItemUnderAnyCircumstance(12016, 1);
				break;
			case 13:
				player.getItems().addItemUnderAnyCircumstance(19988, 1);
				break;
			case 14:
				player.getItems().addItemUnderAnyCircumstance(20708, 1);
				break;
			case 15:
				player.getItems().addItemUnderAnyCircumstance(20706, 1);
				break;
			case 16:
				player.getItems().addItemUnderAnyCircumstance(20704, 1);
				break;
			case 17:
				player.getItems().addItemUnderAnyCircumstance(20710, 1);
				break;
			case 18:
				player.getItems().addItemUnderAnyCircumstance(13258, 1);
				break;
			case 19:
				player.getItems().addItemUnderAnyCircumstance(13260, 1);
				break;
			case 20:
				player.getItems().addItemUnderAnyCircumstance(13259, 1);
				break;
			case 21:
				player.getItems().addItemUnderAnyCircumstance(13261, 1);
				break;
			}
		}

		if (Misc.random(25) == 0) {
			player.getItems().addItem(item.getId(), item.getAmount());
			player.getItems().addItem(itemDouble.getId(), itemDouble.getAmount());
			player.sendMessage("You receive <col=255>" + item.getAmount() + " x " + ItemAssistant.getItemName(item.getId()) + "</col>.");
			player.sendMessage("You receive <col=255>" + itemDouble.getAmount() + " x " + ItemAssistant.getItemName(itemDouble.getId()) + "</col>.");
		} else {
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