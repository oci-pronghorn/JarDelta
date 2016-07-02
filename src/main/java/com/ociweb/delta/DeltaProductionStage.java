package com.ociweb.delta;

import com.ociweb.delta.schema.DeltaSchema;
import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;
import com.ociweb.pronghorn.util.BloomFilter;
import com.ociweb.pronghorn.util.TrieParser;
import com.ociweb.pronghorn.util.TrieParserReader;

public class DeltaProductionStage extends PronghornStage{

    private final TrieParser trieNames;
    private TrieParserReader reader = new TrieParserReader();
        
    public DeltaProductionStage(GraphManager graphManager,  TrieParser trieNames, Pipe<ZipFileSchema> originalData, Pipe<ZipFileSchema> updatedData, Pipe<DeltaSchema> newDeltaPipe) {
       super(graphManager, new Pipe[]{originalData, updatedData}, newDeltaPipe);
       this.trieNames = trieNames;
    }

    @Override
    public void startup() {
        reader = new TrieParserReader();
    }
    
    @Override
    public void run() {
        // TODO read both old and new and produce the delte between them.
        
        //call this to find if the entry exists elsewhere.
        //the hash returned can be checked for rough "equivilance"
        //TrieParserReader.query(reader, trieNames, input, unfoundResult)
        
        
    }

}
