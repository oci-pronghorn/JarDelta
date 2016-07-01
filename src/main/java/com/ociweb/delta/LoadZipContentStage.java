package com.ociweb.delta;

import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class LoadZipContentStage extends PronghornStage {

    private final String sourceFilePath;
    private final int maxChunkSize;
    private final Pipe<ZipFileSchema> zipData;
    
    public LoadZipContentStage(GraphManager graphManager, String sourceFilePath, Pipe<ZipFileSchema> zipData) {
        super(graphManager, NONE, zipData);
        this.sourceFilePath = sourceFilePath;        
        this.maxChunkSize = zipData.maxAvgVarLen;
        this.zipData = zipData;
    }

    
    @Override
    public void startup() {
        
        //TODO: create file object from sourceFilePath 
        //TODO: create buffer array of length maxChunkSize for copy of data
        
    }
    
    @Override
    public void run() {
        // TODO load file from zip and push to content pipe
        
        //if this is is the first call then open the zip file and send the openContainer message
        
        //read every entry from the zip file.
        //on all other calls continue reading entries from the zip file.
        
        //each entry is sent to the pipe as one or more messages each no longer than maxChunkSize.
        //Must start with begin and end with end message id.
        
        
        //when the end of the zip file is reached:  close the file and call requestShutdown();
        
    }

}
