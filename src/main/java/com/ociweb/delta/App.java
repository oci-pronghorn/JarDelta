package com.ociweb.delta;

import java.util.concurrent.TimeUnit;

import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;
import com.ociweb.pronghorn.stage.scheduling.ThreadPerStageScheduler;

public class App 
{

    public static void main( String[] args )
    {
        
        App instance = new App();
        
        GraphManager graphManager = new GraphManager();;

        
        //new UnZip stage
        //new zip stage
        //new delta stage
        //new assemble stage
        
        //needs pipes
                
        
        
        
        PronghornStage watchMe = null;
        
        
        run(graphManager, watchMe); 
        
        
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

    
  
}
