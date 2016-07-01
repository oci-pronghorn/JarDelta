package com.ociweb.delta;

import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class SaveZipContentStage extends PronghornStage {

    private final String targetFile;
    private final Pipe<ZipFileSchema> zipData;
    
    public SaveZipContentStage(GraphManager graphManager, String targetFile, Pipe<ZipFileSchema> zipData) {
        super(graphManager, zipData, NONE);
        this.targetFile = targetFile;
        this.zipData = zipData;
    }
    
    
    @Override
    public void startup() {
        
        //TODO: create file for targetFile;
        
    }
    
    @Override
    public void run() {
        //TODO: on recipt of openContainer message open file for write.
        
        //on recipt of entry messages write each chunk as we get them to the outgoing file entries.
        
        
    }

}
