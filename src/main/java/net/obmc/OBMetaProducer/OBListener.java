package net.obmc.OBMetaProducer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.SimpleDateFormat;
import java.util.Date;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class OBListener {
	
	String trackerfile;
	String servername;

    public OBListener( String trackerfile, String servername ) {
    	this.trackerfile = trackerfile;
    	this.servername = servername;
    }

    @SubscribeEvent
    public void onPlayerJoin( PlayerLoggedInEvent event ) {
    	
    	String switchMsg = "ServerSwitchEvent#" + event.player.getDisplayName() + "#" + this.servername + "#" + getTimestamp();
    	logTrackerMsg( switchMsg );
    }

    @SubscribeEvent
    public void onPlayerJoin( PlayerLoggedOutEvent event ) {
    	
    	String switchMsg = "PlayerDisconnectEvent#" + event.player.getDisplayName() + "#" + this.servername + "#" + getTimestamp();
    	logTrackerMsg( switchMsg );
    }

    private void logTrackerMsg( String msg ) {
    	
    	try (RandomAccessFile stream = new RandomAccessFile( this.trackerfile, "rw" );
    		     FileChannel channel = stream.getChannel()) {

    		    // Use tryLock() or lock() to block until the lock is acquired
    		    FileLock lock = channel.lock();
    		    try {
    		        // Move to the end of the file to append data
    		        channel.position(channel.size());
    		        // Write data
    		        channel.write( ByteBuffer.wrap( ( msg + "\n" ).getBytes() ) );
    		    } finally {
    		        lock.release();
    		    }
    		} catch ( IOException e ) {
    		    e.printStackTrace();
    		}		
	}

    private String getTimestamp() {
    	return new SimpleDateFormat( "MM/dd HH:mm:ss.ms" ).format( new Date() );

    }
}
