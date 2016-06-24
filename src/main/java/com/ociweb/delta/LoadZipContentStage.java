package com.ociweb.delta;

import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class LoadZipContentStage extends PronghornStage {

    private  String fileA;
    
    public LoadZipContentStage(GraphManager graphManager, String fileA, Pipe<ZipFileSchema> contentPipeA) {
        super(graphManager, NONE, contentPipeA);
        this.fileA = fileA;
    }

    @Override
    public void run() {
        // TODO load file from zip and push to content pipe
        
    }

}
