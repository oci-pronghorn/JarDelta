package com.ociweb.delta;

import java.util.concurrent.TimeUnit;

import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.PipeConfig;
import com.ociweb.pronghorn.pipe.util.hash.MurmurHash;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;
import com.ociweb.pronghorn.stage.scheduling.ThreadPerStageScheduler;
import com.ociweb.pronghorn.stage.test.ConsoleJSONDumpStage;
import com.ociweb.pronghorn.util.BloomFilter;
import com.ociweb.pronghorn.util.TrieParser;

/*
 *   Tasks to complete:
 *   
 *   1. finish LoadZipContentStage
 *   2. finish SaveZipContentStage
 *   3. confirm reconstructed file can still be opened by zip.
 *   4. confirm contents of reconstructed file are the same as the original file. (use command line dif tool)
 * 
 * 
 * 
 */


public class CopyApp {

    ///////////////////////////
    //ALL THE PIPE LENGTHS AND TYPES ARE DEFINED HERE
    ////////////////////////////
    private static final int MAX_UNZIPED_FILE_ENTRY=4096;//largest file slice
    private static final int MAX_FILES_IN_FLIGHT = 4;
    private static final PipeConfig<ZipFileSchema> zipDataPipeConfig = new PipeConfig<ZipFileSchema>(ZipFileSchema.instance,MAX_FILES_IN_FLIGHT,MAX_UNZIPED_FILE_ENTRY);
    
    
    public static void main(String[] args) {
        //final String fileA = getOptArg("jarA", "a", args, "unknown file A");
        //final String fileB = getOptArg("jarB", "b", args, "unknown file B"); 
        final String fileA = args[0];
    	
        ////////////////////////////////////////
        ///BUILD FILTER TO "RECOGNIZE" ALL THE EXISTING FILE NAMES.
        //this is needed later but we will develop it now
        /////////////////////////////////////////
        TrieParser trieNames = collectEntryNames(fileA);
                
        
        /////////////////////////////////////////
        //ALL THE PIPE INSTANCES ARE DEFINED HERE
        /////////////////////////////////////////
        Pipe<ZipFileSchema> dataPipe       = new Pipe<ZipFileSchema>(zipDataPipeConfig);
        
        //////////////////////////////////////////////
        //ALL THE STAGES ARE CONSTRUCTED HERE
        /////////////////////////////////////////////    
        
        GraphManager graphManager = new GraphManager();
                
        PronghornStage watchMe = new LoadZipContentStage(graphManager, fileA, dataPipe);        
        
        new ConsoleJSONDumpStage<>(graphManager, dataPipe); // dumps file name, fields/data, separated by colons
       // new SaveZipContentStage(graphManager, fileB, dataPipe);
                
        run(graphManager, watchMe);

    }
    
    private static TrieParser collectEntryNames(String fileA) {
        
        int size = 100000;//100K may not be enough room it it is not we will get an array out of bounds exception.
        int seed = 101;//just a number;
        
        TrieParser trie = new TrieParser(size, 2, true, false); //takes int as value, uses fast match and need not support extraction
        
        
        //TODO: walk over all the zip entries and take the "full name of each file"
        //call this method to add the names to the filter
        //           trie.setUTF8Value(<full name of entry here>, MurmurHash.hash32(<Body of entry here in bytes>, seed));
        //          NOTE: if setUTF8Value throws then make size 2x bigger and do it again.
        
        
        return trie;
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
