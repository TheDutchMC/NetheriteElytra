package nl.thedutchmc.NetheriteElytra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nl.thedutchmc.NetheriteElytra.elytra.ElytraObject;

public class StorageHandler {

	private NetheriteElytra plugin;
	 
	public StorageHandler(NetheriteElytra plugin) {
		this.plugin = plugin;
	}
	
	public List<ElytraObject> read() {
		File path = new File(plugin.getDataFolder() + File.separator + "elytra");
		
		//Check if the storage folder exists, if not, create it.
		if(!path.exists()) {
			path.mkdirs();
		}
		
		//Get a list of files ending in .elytra in the storage folder
		List<String> elytraObjects = null;
		try {
			elytraObjects = discoverConfigs(path);
		} catch (IOException e) {
			NetheriteElytra.logWarn("Something went wrong trying to discover the Elytra Config files: " + e.getMessage());
		}
		
		//Iterate over the discovered files
		List<ElytraObject> result = new ArrayList<>();
		for(String file : elytraObjects) {
			File objectFile = new File(file);
			
			//Try to read the file into an ElytraObject and add it to the List elytras
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objectFile));
				Object o = ois.readObject();
				ElytraObject eo = (ElytraObject) o;
				ois.close();
				
				result.add(eo);
			} catch (IOException e) {
				NetheriteElytra.logWarn("Something went wrong reading " + file + ": " + e.getMessage());
			} catch (ClassNotFoundException e) {
				NetheriteElytra.logWarn("An invalid elytra file was encountered. You should delete it: " + file + " Exception message: " + e.getMessage());
			}
		}
		
		return result;
	}
	
	public void write(ElytraObject eo) {
		File eoFile = new File(plugin.getDataFolder() + File.separator + "elytra", eo.getId() + ".elytra");
		
		//Try to create a file and write the object to it
		try {
			eoFile.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(eoFile));
			oos.writeObject(eo);
			oos.close();
		} catch (IOException e) {
			NetheriteElytra.logWarn("Something went wrong writing " + eoFile.getAbsolutePath() + ": " + e.getMessage());
		}
	}
	
	private List<String> discoverConfigs(File path) throws IOException {
		Stream<Path> walk = Files.walk(Paths.get(path.getAbsolutePath()));
		List<String> result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".elytra")).collect(Collectors.toList());
		walk.close();
		
		return result;
	}
	
}
