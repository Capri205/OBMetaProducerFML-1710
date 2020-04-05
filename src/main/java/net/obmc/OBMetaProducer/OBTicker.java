package net.obmc.OBMetaProducer;


import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import net.obmc.OBMetaProducer.OBMetaTask;


public class OBTicker {

	Integer tickcount = 0;
	
	String metafile;
	Boolean obsfucate;
	Integer range;

	OBMetaTask task = new OBMetaTask();
	
	 public OBTicker(String mfile, Boolean obsf, Integer rng) {
		metafile = mfile;
		obsfucate = obsf;
		range = rng;
	}

	//Called when the server ticks. Usually 20 ticks a second. 
	 @SubscribeEvent
	 public void onServerTick(TickEvent.ServerTickEvent event) {
		 
		 if (event.phase == TickEvent.Phase.END) {
			tickcount++;
		 	if (tickcount == 100 ) {
		 		//FMLLog.log(Level.INFO,"[OBMetaProducer] Writing meta data");
		    
		 		task.run(metafile, obsfucate, range);
		    
		 		tickcount = 0;
		 	}
	 	}
	 }

}