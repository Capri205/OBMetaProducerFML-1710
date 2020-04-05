package net.obmc.OBMetaProducer;


import java.io.File;

import org.apache.logging.log4j.Level;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = "obmetaproducer", version = "0.1", name = "OBMetaProducer", acceptableRemoteVersions = "*")

public class OBMetaProducer
{
	@Instance("OBMetaProducer")
	public static OBMetaProducer instance = new OBMetaProducer();
	
	public static final String OBMFILE = "MetaFile";
	public static final String OBMOPTS = "Options";

    private String metafile;
    private Boolean obsfucate;
    private Integer range;
    private File cfgFile;
	private Configuration cfg;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {

    	// check for config file
    	try {
    		cfgFile = new File("config/OBMetaProducer.cfg");
    	} catch (Exception e) {
    		FMLLog.log(Level.INFO, "[OBMetaProducer] Error openening config file!");
    		FMLLog.log(Level.INFO,e.toString());
    	}
    	
    	event.getSuggestedConfigurationFile();
    	cfg = new Configuration(cfgFile);

    	try
        {
    		cfg.load();
            FMLLog.log(Level.INFO, "[OBMetaProducer] Loaded configuration");
            //Property metafileProperty = cfg.get(Configuration.CATEGORY_GENERAL, "Filename", "OBMetaProducer.dat");
            //String metafile = cfg.get(Configuration.CATEGORY_GENERAL, "Filename", "XxXxXx.dat").getString();
            metafile = cfg.getString("Filename", Configuration.CATEGORY_GENERAL, "config/OBMetaProducer.dat","Player position data file");
            obsfucate = cfg.getBoolean("Obsfucate", Configuration.CATEGORY_GENERAL, false, "Option to obsfucate player location");
            range = cfg.getInt("Range", Configuration.CATEGORY_GENERAL, 1000, 1000, 1000, "Number of blocks to obsfucate");

            //FMLLog.log(Level.INFO, "[OBMetaProducer] "+metafileProperty);
            //metafile = metafileProperty.getString();
            FMLLog.log(Level.INFO, "[OBMetaProducer] Metadata output in "+metafile);
            FMLLog.log(Level.INFO, "[OBMetaProducer] Player positon masking is set to "+obsfucate);
            if (obsfucate) {
            	FMLLog.log(Level.INFO, "[OBMetaProducer] Masking range is "+range+" blocks");
            }
        } catch (Exception e) {
        	FMLLog.log(Level.ERROR, e, "[OBMetaProducer] Failed to load configuration");
        }

    	FMLCommonHandler.instance().bus().register(new OBTicker(metafile, obsfucate, range));    
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
 
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}