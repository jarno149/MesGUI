/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.gui;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 *
 * @author k1400284
 */
public class FileWatcher implements Runnable
{
    private WatchService watchService;
    private FileListener fileListener;
    private long lastInvoke;

    public FileWatcher(WatchService watchService, FileListener fileListener)
    {
        this.watchService = watchService;
        this.fileListener = fileListener;
        this.lastInvoke = System.currentTimeMillis();
    }    
    
    @Override
    public void run()
    {
        try
        {
            WatchKey key = this.watchService.take();
            while(key != null)
            {
                for(WatchEvent event : key.pollEvents())
                {
                    String filename = event.context().toString();
                    String compareFilename = this.fileListener.getFilename();
                        long compareTimeStamp = System.currentTimeMillis();
                        if(this.lastInvoke + this.fileListener.getMinDelayBetweenInvokes() < compareTimeStamp)
                        {
                            if(this.fileListener.isListenAllFilesInDir())
                            {
                                this.lastInvoke = System.currentTimeMillis();
                                this.fileListener.getActions().onEvent(filename);
                            }
                            else if(compareFilename.equals(filename))
                                {
                                    this.lastInvoke = System.currentTimeMillis();
                                    this.fileListener.getActions().onEvent(filename);
                                }
                        }
                }
            }
            key.reset();
            key = this.watchService.take();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        System.err.println("Filelistener thread stopped!");
    }
    
}
