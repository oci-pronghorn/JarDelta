package com.ociweb.delta;

import com.ociweb.delta.schema.DeltaSchema;
import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class DeltaProductionStage extends PronghornStage{

    public DeltaProductionStage(GraphManager graphManager, Pipe<ZipFileSchema> originalData, Pipe<ZipFileSchema> updatedData, Pipe<DeltaSchema> newDeltaPipe) {
       super(graphManager, new Pipe[]{originalData, updatedData}, newDeltaPipe);
    }

    @Override
    public void run() {
        // TODO read both old and new and produce the delte between them.
        
    }

}
