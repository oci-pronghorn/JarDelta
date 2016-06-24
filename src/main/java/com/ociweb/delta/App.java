package com.ociweb.delta;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import com.ociweb.delta.schema.DeltaSchema;
import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.PipeConfig;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.file.TapeWriteStage;
import com.ociweb.pronghorn.stage.route.SplitterStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;
import com.ociweb.pronghorn.stage.scheduling.ThreadPerStageScheduler;
import com.ociweb.pronghorn.stage.test.PipeCleanerStage;
import com.ociweb.pronghorn.stage.test.TestValidator;

public class App 
{    
    
    ///////////////////////////
    //ALL THE PIPE LENGTHS AND TYPES ARE DEFINED HERE
    ////////////////////////////
    private static final int MAX_UNZIPED_FILE_ENTRY=1<<20;//1Mb
    private static final int MAX_FILES_IN_FLIGHT = 4;
    private static final PipeConfig<ZipFileSchema> zipDataPipeConfig = new PipeConfig<ZipFileSchema>(ZipFileSchema.instance,MAX_FILES_IN_FLIGHT,MAX_UNZIPED_FILE_ENTRY);
          
    private static final int MAX_INJECT_BLOCK=1<<16;//64k
    private static final int MAX_DELTA_COMMANDS = 8;
    private static final PipeConfig<DeltaSchema> deltaDataPipeConfig = new PipeConfig<DeltaSchema>(DeltaSchema.instance,MAX_DELTA_COMMANDS,MAX_INJECT_BLOCK);
    ////////////////////////
    ////////////////////////
    
    public static void main( String[] args )
    {
        final String fileA = getOptArg("jarA", "a", args, "unknown file A");
        final String fileB = getOptArg("jarB", "b", args, "unknown file B");        
        final String deltaFile = getOptArg("deltaFile", "d", args, "deltaData.dat");
        final String newZipFile = getOptArg("reconstructedB", "newB", args, "newB.dat");
        
        RandomAccessFile deltaOutputFile;
        try {
            deltaOutputFile = new RandomAccessFile(new File(deltaFile), "rw");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        
        App instance = new App();
        
        GraphManager graphManager = new GraphManager();
        
        /////////////////////////////////////////
        //ALL THE PIPE INSTANCES ARE DEFINED HERE
        /////////////////////////////////////////
        Pipe<ZipFileSchema> contentPipeA       = new Pipe<ZipFileSchema>(zipDataPipeConfig);
        Pipe<ZipFileSchema> contentPipeB       = new Pipe<ZipFileSchema>(zipDataPipeConfig);
        Pipe<ZipFileSchema> reconstructedPipeB = new Pipe<ZipFileSchema>(zipDataPipeConfig);
        
        Pipe<ZipFileSchema> contentForDeltaPipeA = new Pipe<ZipFileSchema>(zipDataPipeConfig.grow2x());
        Pipe<ZipFileSchema> contentForTestPipeA = new Pipe<ZipFileSchema>(zipDataPipeConfig.grow2x());
        
        Pipe<ZipFileSchema> contentForDeltaPipeB = new Pipe<ZipFileSchema>(zipDataPipeConfig.grow2x());
        Pipe<ZipFileSchema> contentForTestPipeB = new Pipe<ZipFileSchema>(zipDataPipeConfig.grow2x());
        
        Pipe<ZipFileSchema> reconstructedOutputPipeB = new Pipe<ZipFileSchema>(zipDataPipeConfig.grow2x());
        Pipe<ZipFileSchema> reconstructedTestPipeB = new Pipe<ZipFileSchema>(zipDataPipeConfig.grow2x());
        
        Pipe<DeltaSchema> deltaPipe       = new Pipe<DeltaSchema>(deltaDataPipeConfig);        
        Pipe<DeltaSchema> deltaTestPipe   = new Pipe<DeltaSchema>(deltaDataPipeConfig.grow2x());
        Pipe<DeltaSchema> deltaOutputPipe = new Pipe<DeltaSchema>(deltaDataPipeConfig.grow2x());
        //////////////////////////////////////////        
        
 
        //////////////////////////////////////////////
        //ALL THE STAGES ARE CONSTRUCTED HERE
        /////////////////////////////////////////////        
        
        //TODO: build static constuctors for these
        
        new LoadZipContentStage(graphManager, fileA, contentPipeA);        
        new SplitterStage<>(graphManager, contentPipeA, contentForDeltaPipeA, contentForTestPipeA);
        
        new LoadZipContentStage(graphManager, fileB, contentPipeB);        
        new SplitterStage<>(graphManager, contentPipeB, contentForDeltaPipeB, contentForTestPipeB);
                
        new DeltaProductionStage(graphManager, contentForDeltaPipeA, contentForDeltaPipeB, deltaPipe);
        new SplitterStage<>(graphManager, deltaPipe, deltaTestPipe, deltaOutputPipe);
        
        new ApplyPatchStage(graphManager, contentForTestPipeA, deltaTestPipe, reconstructedPipeB);
        new SplitterStage<>(graphManager, reconstructedPipeB, reconstructedOutputPipeB, reconstructedTestPipeB);
        
        new TapeWriteStage<>(graphManager, deltaOutputPipe, deltaOutputFile);
        new SaveZipContentStage(graphManager, newZipFile, reconstructedOutputPipeB);
        
        /////////////////////////////////////////////////
        
        //confirm pipes contain equal values
        PronghornStage test = new TestValidator(graphManager, contentForTestPipeB, reconstructedTestPipeB);
        
        run(graphManager, test); 
        
        
    }

    private static void run(GraphManager graphManager, PronghornStage watchMe) {
        if (null!=watchMe) {
            ThreadPerStageScheduler scheduler = new ThreadPerStageScheduler(graphManager);
            
            scheduler.startup();
        
            GraphManager.blockUntilStageBeginsShutdown(graphManager, watchMe);                
        
            scheduler.shutdown();
            scheduler.awaitTermination(30, TimeUnit.SECONDS);
        }
    }

   private static String getOptArg(String longName, String shortName, String[] args, String defaultValue) {
        
        String prev = null;
        for (String token : args) {
            if (longName.equals(prev) || shortName.equals(prev)) {
                if (token == null || token.trim().length() == 0 || token.startsWith("-")) {
                    return defaultValue;
                }
                return reportChoice(longName, shortName, token.trim());
            }
            prev = token;
        }
        return reportChoice(longName, shortName, defaultValue);
    }
    
    private static String reportChoice(final String longName, final String shortName, final String value) {
        System.out.print(longName);
        System.out.print(" ");
        System.out.print(shortName);
        System.out.print(" ");
        System.out.println(value);
        return value;
    }
  
}
