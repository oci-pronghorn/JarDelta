package com.ociweb.delta;

import com.ociweb.delta.schema.DeltaSchema;
import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class ApplyPatchStage extends PronghornStage{

    public ApplyPatchStage(GraphManager graphManager, Pipe<ZipFileSchema> originalData, Pipe<DeltaSchema> deltaToApply, Pipe<ZipFileSchema> newUpdatedData) {
       super(graphManager,new Pipe[]{originalData,deltaToApply},newUpdatedData);
    }

    @Override
    public void run() {
        
        // TODO Must read all the data and apply the delta changes.
        
    }

}
