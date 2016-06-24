package com.ociweb.delta;

import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class SaveZipContentStage extends PronghornStage {

    private final String targetFile;
    
    public SaveZipContentStage(GraphManager graphManager, String targetFile, Pipe<ZipFileSchema> zipData) {
        super(graphManager, zipData, NONE);
        this.targetFile = targetFile;
    }
    @Override
    public void run() {
        // TODO write data out to zip file
        
    }

}
