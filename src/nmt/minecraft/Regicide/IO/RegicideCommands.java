package nmt.minecraft.Regicide.IO;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import nmt.minecraft.Regicide.RegicidePlugin;
import nmt.minecraft.Regicide.Game.RegicideGame;
import nmt.minecraft.Regicide.Game.Player.RPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles all commands sent to /regicide
 * @author William
 *
 */
public class RegicideCommands implements CommandExecutor{
	private static String[] commandList = {"register", "setLobby", "setSpawn", "setExit", 
		"start", "leave", "open", "firstPlace", "secondPlace", "thirdPlace", "otherPlace",
		"end","help", "kick", "loadconfig", "saveconfig", "status"};

	private String aquaChat = ChatColor.AQUA+"";
	private String blueChat = ChatColor.BLUE+"";
	private String goldChat = ChatColor.GOLD+"";
	private String greenChat = ChatColor.GREEN+"";
	private String redChat = ChatColor.RED+"";
	private String boldChat = ChatColor.BOLD+"";
	private String resetChat = ChatColor.RESET+"";
	/**
	 * This method determines how a sent command is to be interpreted.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//All commands must be sent by a player
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a playah!");
			return false;
		}
		if(args.length == 0){
			sender.sendMessage("Something went wrong... We need more arguments");
			sender.sendMessage("Valid commands are register, start, setSpawn, or setLobby");
			return false;
		}
		//The help menu
		if (args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(greenChat + boldChat + "===Help===" + resetChat);
			sender.sendMessage("Usage: " + redChat + "/regicide " + blueChat + "[command] " + goldChat + "[command arguments]" + resetChat);
			sender.sendMessage(greenChat + boldChat + "===Commands===" + resetChat);
			sender.sendMessage(blueChat + "register " + goldChat + " [game name]" + resetChat + " registers a new instance of Regicide");
			sender.sendMessage(blueChat + "start    " + goldChat + " [game name]" + resetChat + " starts the specified game");
			sender.sendMessage(blueChat + "setSpawn " + goldChat + " [game name]" + resetChat + " sets another spawn point for the specified game");
			sender.sendMessage(blueChat + "setLobby " + goldChat + " [game name]" + resetChat + " sets the lobby location");
			sender.sendMessage(blueChat + "setExit  " + goldChat + " [game name]" + resetChat + " sets the exit location of a game");
			sender.sendMessage(blueChat + "open     " + goldChat + " [game name]" + resetChat + " opens a game for registration");
			sender.sendMessage(blueChat + "leave    " + resetChat + " leave regicide");
		}
		
		//Sender must now be a player
		//Register buttons/games
		else if (args[0].equalsIgnoreCase("register")) {
			if(args.length != 3){
				sender.sendMessage("Wrong number of arguments: /regicide register [name] [mob type]");
				return false;
			}
			registerGame(sender, args);
		}
		
		//Start games
		else if (args[0].equalsIgnoreCase("start")) {
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /regicide start [name]");
				return false;
			}
			startGame(sender, args);
		}
		
		//Set a Game Spawn
		else if (args[0].equalsIgnoreCase("setSpawn")) {
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /regicide setSpawn [name]");
				return false;
			}
			setSpawn(sender, args);
		}
		
		//Set Lobby
		else if (args[0].equalsIgnoreCase("setLobby")) {
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /regicide setLobby [name]");
				return false;
			}
			setLobby(sender, args);
		}
		
		//Open Game
		else if (args[0].equalsIgnoreCase("open")) {
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /regicide open [name]");
				return false;
			}
			this.openGame(sender, args);
		}
		
		//Leave Game
		else if (args[0].equalsIgnoreCase("leave")) {
			if(args.length != 1){
				sender.sendMessage("Wrong number of arguments: /regicide leave");
				return false;
			}
			this.leaveGame(sender);
		}
		
		//Set Game Exit
		else if (args[0].equalsIgnoreCase("setExit")) {
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /regicide Exit [name]");
				return false;
			}
			setExit(sender, args);
		}
		
		else if(args[0].equalsIgnoreCase("firstPlace")){
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /regicide firstPlace [name]");
				return false;
			}
			
			setFirstPlace(sender, args);
		}
		
		else if(args[0].equalsIgnoreCase("secondPlace")){
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /regicide firstPlace [name]");
				return false;
			}
			setSecondPlace(sender, args);
		}
		else if(args[0].equalsIgnoreCase("thirdPlace")){
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /regicide firstPlace [name]");
				return false;
			}
			setThirdPlace(sender, args);
		}
		else if(args[0].equalsIgnoreCase("otherPlace")){
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /regicide firstPlace [name]");
				return false;
			}
			
			setOtherPlace(sender, args);
		}
		
		//End Game
		else if (args[0].equalsIgnoreCase("end")) {
			if (args.length != 2) {
				sender.sendMessage("Wrong number of arguments: /regicide end [name]");
				return false;
			}
			closeGame(sender,args);
		}
		else if (args[0].equalsIgnoreCase("kick")) {
			if(args.length != 3) {
				sender.sendMessage("Wrong number of arguments: /regicide kick [game] [player name]");
				return false;
			}
			kickPlayer(sender, args);
		}
		else if (args[0].equalsIgnoreCase("loadconfig")) {
			if (args.length != 3) {
				sender.sendMessage("Wrong number of arguments: /regicide loadconfig [gamename] [filename]");
				return false;
			}
			loadConfig(sender, args);
		}
		else if (args[0].equalsIgnoreCase("saveconfig")) {
			if (args.length != 3) {
				sender.sendMessage("Wrong number of arguments: /regicide savefongi [gamename] [filename]");
				return false;
			}
			saveConfig(sender, args);
		}
		else if (args[0].equalsIgnoreCase("status")) {
			if (args.length != 2) {
				sender.sendMessage("Wrong number of arguments: /regicide status [game name]");
				return false;
			}
			printStatus(sender, args[1]);
		}
		else {
			sender.sendMessage("Something went wrong...");
			sender.sendMessage("Valid commands are register, start, setSpawn, or setLobby");
			return false;
		}

		return true;
	}
	
	public static List<String> getCommandList(){
		return Arrays.asList(commandList);
	}
	
	/**
	 * A player must register a button, and which in turn registers a game.
	 * @param sender The sender.
	 * @param args The arguments to the command.
	 * @return True if the registration completed, false if otherwise.
	 */
	public boolean registerGame(CommandSender sender, String[] args) {
		
		if (args.length != 3) {
			sender.sendMessage(redChat + "Error! " + resetChat);
			sender.sendMessage("Incorrect number of arguments: " + redChat + 
								"/regicide " + blueChat + "register " + goldChat + 
								" [game name] [mob type]" + resetChat);
			return false;
		}
		
		List<RegicideGame> Games = RegicidePlugin.regicidePlugin.getGames();
		//Check to see if a Game with the same name already exists
		for (RegicideGame g : Games) {
			if (g.getName().equals(args[1])) {
				//Alert User that a game with the same name already exists.
				sender.sendMessage(redChat + boldChat + "Game with name: \"" + args[1] + "\" already exists!" + resetChat);
				return false;
			}
		}
		//Add new Game to list
		//@Debug
		RegicidePlugin.regicidePlugin.getLogger().info("Adding game: " + args[1]);
		RegicideGame game = new RegicideGame(args[1],args[2],args[2]);
		Games.add(game);
		//Register Button
		ButtonListener listen = new ButtonListener(((Player) sender).getLocation(), game);
		Bukkit.getPluginManager().registerEvents(listen, RegicidePlugin.regicidePlugin);
		sender.sendMessage(aquaChat + "Successfully registered game: " + goldChat + args[1] + resetChat);
		return true;
	}
	
	/**
	 * This method starts the specified game.
	 * @param sender The command sender.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean startGame(CommandSender sender, String[] args) {
		if (args.length > 2 || args.length == 0) {
			sender.sendMessage(redChat + "Error!" + resetChat);
			sender.sendMessage("Incorrect number of arguments: " + redChat +
								"/regicide " + blueChat + "start " + goldChat +
								"[game name]" + resetChat);
			return false;
		}
		String gameName = args[1];
		List<RegicideGame> Games = RegicidePlugin.regicidePlugin.getGames();
		for (RegicideGame g : Games) {
			if (g.getName().equals(gameName)) {
				//Check to see if game is already running
				if (g.getIsRunning()) {
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + redChat + "Game: " + g.getName() + " is already running!" + resetChat);
					return false;
				}
				//Check to see if the game has an exit location
				if(g.getExitLocation() == null){
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + redChat + "Game: " + g.getName() + " has no exit location!" + resetChat);
					return false;
				}
				
				//make sure all the places are set for the winners
				if(g.getFirstPlace() == null || g.getSecondPlace() == null || g.getThirdPlace() == null || g.getOtherPlace() == null){
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + redChat + "Game: " + g.getName() + " is missing a winner location!" + resetChat);
					return false;
				}
				
				//Check to see if game actually has players in it.
				if(g.getPlayers().isEmpty()) {
					//Game is Empty!
					sender.sendMessage(redChat + "Warning!" + resetChat);
					sender.sendMessage("No players are registered for: " + goldChat + g.getName());
					return true;
				}
				sender.sendMessage(aquaChat + "Started Game Instance: " + goldChat +  g.getName() + resetChat);

				RegicidePlugin.regicidePlugin.getLogger().info("Started Game: " + g.getName());
				sender.sendMessage(greenChat + "Started: " + g.getName() + resetChat);
				
				//Notify Players that the game is starting
				List<RPlayer> gamePlayers = g.getPlayers();
				for (RPlayer player : gamePlayers) {
					Player p = player.getPlayer();
					p.sendMessage(goldChat + "The Game Has Begun!" + resetChat);
					p.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 1, 0);
				}
				g.startGame();
				return true;
			}
		}
		RegicidePlugin.regicidePlugin.getLogger().info("Warning! Could not find: \"" + gameName + "\" in registered games!");
		sender.sendMessage(redChat + "That game is not registered!" + resetChat + " Make sure you register with /regicide register <game name>");
		return false;
	}
	
	/**
	 * This method adds a spawn point to the specified game to the location of the sender.
	 * @param sender The sender of the command.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean setSpawn(CommandSender sender, String[] args) {
		List<RegicideGame> games = RegicidePlugin.regicidePlugin.getGames();
		
		for (RegicideGame game : games) {
			if (game.getName().equalsIgnoreCase(args[1])) {
				//Check to see if game is already running
				if (game.getIsRunning()) {
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " is already running!");
					return false;
				}
				Location loc = ((Player) sender).getLocation();
				game.addSpawnLocation(loc);
				sender.sendMessage(greenChat + "Successfully registered starting position for: "+ game.getName() + resetChat);
				sender.sendMessage(aquaChat + "Number of Spawn Points: " + goldChat + game.getSpawnLocations().size() + resetChat);
				return true;
			}
		}
		
		sender.sendMessage(redChat + "Unable to locate instance: " + goldChat + args[1] + resetChat);		
		return false;
	}
	
	/**
	 * This method sets the Game lobby to the location of the sender.
	 * @param sender The command sender.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean setLobby(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("Please supply the name of the instance to add this spawn point to!");
			return false;
		}
		

		for (RegicideGame game : RegicidePlugin.regicidePlugin.getGames()) {
			if (game.getName().equalsIgnoreCase(args[1])) {
				//Check to see if game is already running
				if (game.getIsRunning()) {
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " is already running!");
					return false;
				}
				Location loc = ((Player) sender).getLocation();
				game.setLobbyLocation(loc);
				
				sender.sendMessage(greenChat + "Successfully registered lobby position for: " + goldChat + game.getName() + resetChat);
				return true;
			}
		}
		
		sender.sendMessage(redChat + "Unable to locate instance: " + goldChat + args[1] + resetChat);		
		return false;
		
	}

	/**
	 * This method allows a player to leave all games.
	 * @param sender The sender of the command
	 * @return
	 */
	public boolean leaveGame(CommandSender sender) {
		List<RegicideGame> games = RegicidePlugin.regicidePlugin.getGames();
		for (RegicideGame game : games) {
			game.removePlayer((Player) sender);
			sender.sendMessage(aquaChat + "You have left Regicide Game " + goldChat + game.getName() + resetChat);
			GameAnnouncer.GameLeave(game, (Player) sender);
		}
		return true;
	}
	
	/**
	 * This method opens a game for player registration.
	 * @param sender The command sender.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean openGame(CommandSender sender, String[] args) {
		List<RegicideGame> games = RegicidePlugin.regicidePlugin.getGames();
		for (RegicideGame game : games) {
			if (game.getName().equalsIgnoreCase(args[1])) {
				//Check to see if the game is running
				if (game.getIsRunning()) {
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " is already running!" + resetChat);
					return false;
				}else if(game.getLobbyLocation() == null){
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " has no lobby!" + resetChat);
					return false;
				}
				//Game is not running
				game.open();
				sender.sendMessage(greenChat + "Successfully opened game: " + goldChat + game.getName() + resetChat);
				GameAnnouncer.OpenGame(game);
			}
		}
		return true;
	}
	
	/**
	 * Force the conclusion of a Regicide Game
	 * @param sender The sender of the command
	 * @param args The arguments
	 * @return
	 */
	public boolean closeGame(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("Please supply the name of the instance that you wish to end!");
			return false;
		}
		List<RegicideGame> games = RegicidePlugin.regicidePlugin.getGames();
		for (RegicideGame game : games) {
			if (game.getName().equalsIgnoreCase(args[1])) {
				//check to see if we can end the game
				if(game.getIsRunning() == false){
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " is already running!");
					return false;
				}
				
				game.endGame();
				sender.sendMessage(greenChat + "You ended the game: " + goldChat + game.getName() + resetChat);
			}
		}
		return true;
	}
	
	/**
	 * This method sets a Game Exit location.
	 * @param sender The command sender.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean setExit(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("Please supply the name of the instance to add this exit point to!");
			return false;
		}
		

		for (RegicideGame game : RegicidePlugin.regicidePlugin.getGames()) {
			if (game.getName().equalsIgnoreCase(args[1])) {
				//check if game is running
				if(game.getIsRunning() == true){
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " is already running!");
					return false;
				}
				//do the stuff
				Location loc = ((Player) sender).getLocation();
				game.setExitLocation(loc);
				
				sender.sendMessage(greenChat + "Successfully registered Exit position!" + resetChat);
				return true;
			}
		}
		
		sender.sendMessage(redChat + "Unable to locate instance: " + goldChat + args[1] + resetChat);		
		return false;
		
	}
	
	/**
	 * This method sets a First Place Winner location.
	 * @param sender The command sender.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean setFirstPlace(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("Please supply the name of the instance to add this exit point to!");
			return false;
		}
		

		for (RegicideGame game : RegicidePlugin.regicidePlugin.getGames()) {
			if (game.getName().equalsIgnoreCase(args[1])) {
				//check if game is running
				if(game.getIsRunning() == true){
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " is already running!");
					return false;
				}
				
				//do the stuff
				Location loc = ((Player) sender).getLocation();
				game.setFirstPlace(loc);
				
				sender.sendMessage(greenChat + "Successfully registered first place position!" + resetChat);
				return true;
			}
		}
		
		sender.sendMessage(redChat + "Unable to locate instance: " + goldChat + args[1] + resetChat);		
		return false;
		
	}
	
	/**
	 * This method sets a First Place Winner location.
	 * @param sender The command sender.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean setSecondPlace(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("Please supply the name of the instance to add this exit point to!");
			return false;
		}
		

		for (RegicideGame game : RegicidePlugin.regicidePlugin.getGames()) {
			if (game.getName().equalsIgnoreCase(args[1])) {
				//check if game is running
				if(game.getIsRunning() == true){
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " is already running!");
					return false;
				}
				//do the stuff
				Location loc = ((Player) sender).getLocation();
				game.setSecondPlace(loc);
				
				sender.sendMessage(greenChat + "Successfully registered second place position!" + resetChat);
				return true;
			}
		}
		
		sender.sendMessage(redChat + "Unable to locate instance: " + goldChat + args[1] + resetChat);		
		return false;
		
	}
	
	/**
	 * This method sets a First Place Winner location.
	 * @param sender The command sender.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean setThirdPlace(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("Please supply the name of the instance to add this exit point to!");
			return false;
		}
		

		for (RegicideGame game : RegicidePlugin.regicidePlugin.getGames()) {
			if (game.getName().equalsIgnoreCase(args[1])) {
				//check if game is running
				if(game.getIsRunning() == true){
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " is already running!");
					return false;
				}
				//do the stuff
				Location loc = ((Player) sender).getLocation();
				game.setThirdPlace(loc);
				
				sender.sendMessage(greenChat + "Successfully registered third place position!" + resetChat);
				return true;
			}
		}
		
		sender.sendMessage(redChat + "Unable to locate instance: " + goldChat + args[1] + resetChat);		
		return false;
		
	}
	
	/**
	 * This method sets a First Place Winner location.
	 * @param sender The command sender.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean setOtherPlace(CommandSender sender, String[] args) {
		if (args.length < 2) {
			sender.sendMessage("Please supply the name of the instance to add this exit point to!");
			return false;
		}
		

		for (RegicideGame game : RegicidePlugin.regicidePlugin.getGames()) {
			if (game.getName().equalsIgnoreCase(args[1])) {
				//check if game is running
				if(game.getIsRunning() == true){
					sender.sendMessage(redChat + boldChat + "ERROR! " + resetChat + "Game: " + game.getName() + " is already running!");
					return false;
				}
				//do the stuff
				Location loc = ((Player) sender).getLocation();
				game.setOtherPlace(loc);
				
				sender.sendMessage(greenChat + "Successfully registered non-winners position!" + resetChat);
				return true;
			}
		}
		
		sender.sendMessage(redChat + "Unable to locate instance: " + goldChat + args[1] + resetChat);		
		return false;
		
	}
	
	/**
	 * This method kicks a player from a game.
	 * @param sender The sender of the argument.
	 * @param args The command arguments.
	 * @return
	 */
	public boolean kickPlayer(CommandSender sender, String[] args) {
		String gameName = args[1];
		String playerName = args[2];
		if (!sender.isOp()) {
			sender.sendMessage("You are not authorized for this command!");
			return true;
		}
		for (RegicideGame game : RegicidePlugin.regicidePlugin.getGames()) {
			//Find Game Specified
			if (game.getName().equalsIgnoreCase(gameName)) {
				//Find Player
				List<RPlayer> players = game.getPlayers();
				for (RPlayer player : players) {
					//Remove player
					if (player.getPlayer().getName().equals(playerName)) {
						sender.sendMessage("Successfully removed: " + goldChat + playerName + 
								resetChat + " from: " + aquaChat + gameName + resetChat);
						game.removePlayer(player);
						player.getPlayer().sendMessage("You have been kicked from: " + goldChat + gameName + resetChat);
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * This method loads a configuration file for a game.
	 * This method will alert the sender if information is missing that must
	 * be set before a game starts.
	 * @param sender The sender of the command.
	 * @param args The arguments of the command.
	 * @return
	 */
	public boolean loadConfig(CommandSender sender, String[] args) {
		String dataPath = RegicidePlugin.regicidePlugin.getDataFolder().getPath();
		File tmp = new File(dataPath, "configs");
		String fileName = args[2];
		String gameName = args[1];
		RegicideGame gameInstance = null;
		File fileConfig = new File(tmp.getPath(), fileName);
		//Check to see if file exists
		if (!fileConfig.exists()) {
			sender.sendMessage(boldChat + redChat + "ERROR! " + resetChat + redChat + "File does not exist!" + resetChat);
			return true;
		}
		//Find specified game instance
		for (RegicideGame game : RegicidePlugin.regicidePlugin.getGames()) {
			if(game.getName().equalsIgnoreCase(gameName)){
				gameInstance = game;
				break;
			}
		}
		//Check to see if the game exists
		if (gameInstance == null) {
			sender.sendMessage(boldChat + redChat + "ERROR!" + resetChat + redChat + 
					"Could not find game instance: " + goldChat + gameName + resetChat);
			return true;
		}
		//Check to see if game is already running
		if (gameInstance.getIsRunning()) {
			sender.sendMessage(boldChat + redChat + "ERROR! " + resetChat + goldChat + 
					gameName + redChat + " is already running!" + resetChat);
			return true;
		}
		sender.sendMessage("Loading configuration file: " + goldChat + fileName + resetChat);
		gameInstance.loadConfig(fileConfig);
		return true;
	}
	
	/**
	 * This method saves a configuration file for a game.
	 * This method will alert the sender if the information being saved is
	 * missing vital information for a game to start.
	 * @param sender The sender of the command.
	 * @param args The arguments of the command.
	 * @return
	 */
	public boolean saveConfig(CommandSender sender, String[] args) {
		String dataPath = RegicidePlugin.regicidePlugin.getDataFolder().getPath();
		File tmp = new File(dataPath, "configs");
		String fileName = args[2];
		String gameName = args[1];
		RegicideGame gameInstance = null;
		File fileConfig = new File(tmp.getPath(), fileName);
		for (RegicideGame game : RegicidePlugin.regicidePlugin.getGames()) {
			if (game.getName().equalsIgnoreCase(gameName)) {
				gameInstance = game;
				break;
			}
		}
		if (gameInstance == null) {
			sender.sendMessage(boldChat + redChat +"ERROR! " + resetChat + redChat +
					"Could not find game instance: " + goldChat + gameName + resetChat);
			return true;
		}
		gameInstance.saveConfig(fileConfig);
		return true;
	}
	
	public boolean printStatus(CommandSender sender, String gameName){
		
		for(RegicideGame game : RegicidePlugin.regicidePlugin.getGames()){
			if (game.getName().equalsIgnoreCase(gameName)) {
				game.printStatus();
				return true;
			}
		}
		sender.sendMessage(redChat+"ERROR: "+resetChat+"Could not find instance of "+ gameName);
		return false;
	}
}
