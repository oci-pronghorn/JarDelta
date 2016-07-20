package com.ociweb.delta;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import com.ociweb.delta.schema.ZipFileSchema;
import com.ociweb.pronghorn.pipe.Pipe;
import com.ociweb.pronghorn.pipe.PipeWriter;
import com.ociweb.pronghorn.stage.PronghornStage;
import com.ociweb.pronghorn.stage.scheduling.GraphManager;

public class LoadZipContentStage extends PronghornStage {

    private final String sourceFilePath;
    private final int maxChunkSize;
    private final Pipe<ZipFileSchema> zipData;
    private final int maxSizeofMSG = 4096;
    
    private static boolean isFirstRun;
    private static int activeFile, activePosition;
    // TODO: use member variables: counter activeFile, activePosition

    private File srcFile;
    private byte[]bufferArray;
    private ZipFile loadFile;
    private Enumeration ZipFileEnum;
    private ByteArrayOutputStream boS;
    
    
    
    
    public LoadZipContentStage(	GraphManager graphManager, String sourceFilePath, Pipe<ZipFileSchema> zipData) {
        super(graphManager, NONE, zipData);
        this.sourceFilePath = sourceFilePath;
        //this.maxChunkSize = zipData.maxAvgVarLen;
        this.maxChunkSize = 4096;
        this.zipData = zipData;
    }

    
    @Override
    public void startup() {
        
        //TODO: create file object from sourceFilePath
    	srcFile = new File(sourceFilePath);
    	try {
			loadFile = new ZipFile(srcFile);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		//TODO: create buffer array of length maxChunkSize for copy of data
        bufferArray = new byte[maxChunkSize];
        isFirstRun = true;
        activePosition=0;
        ByteArrayOutputStream boS = new ByteArrayOutputStream();
    }
    
    @Override
    public void run(){
        // TODO load file from zip and push to content pipe
    	
    	// check isFirstRun or tryWriteFragment first? or check file. file is already checked in startup()
    	
    	// or should it be here?
        /*
        if (PipeWriter.tryWriteFragment(zipData, ZipFileSchema.MSG_CONTAINEROPEN_8))
        {
        	PipeWriter.writeUTF8(zipData, ZipFileSchema.MSG_CONTAINEROPEN_8_FIELD_NAME_21, "jarName");
        	PipeWriter.publishWrites(zipData);
        }
        */
    	
        //if this is is the first call then open the zip file and send the openContainer message
        if(isFirstRun)
        {
        	try {
    			loadFile = new ZipFile(srcFile);
    		} catch (ZipException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

        	
        	// tryWriteFragment checks if there is room on the pipe
        	if (PipeWriter.tryWriteFragment(zipData, ZipFileSchema.MSG_CONTAINEROPEN_8))
            {
//            	PipeWriter.writeUTF8(zipData, ZipFileSchema.MSG_CONTAINEROPEN_8_FIELD_NAME_21, "jarName");
            	PipeWriter.writeUTF8(zipData, ZipFileSchema.MSG_CONTAINEROPEN_8_FIELD_NAME_21, sourceFilePath);
            	PipeWriter.publishWrites(zipData);
            	isFirstRun = false;
            	// load
            	try {
            		for (Enumeration<? extends ZipEntry> e = loadFile.entries();
            				e.hasMoreElements();) {
            			ZipEntry ze = e.nextElement();
            			InputStream in = loadFile.getInputStream(ze);
            			
            			// alternative at http://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
            			in.read(bufferArray);
            			//boS.write(bufferArray, activePosition, bufferArray.length);
            			//bufferArray = boS.toByteArray();
            			
            			// get entire file as array
            			// 2 byte array once per file
            			
            			//mask value max int FFFF
            			//size of message 4096, determines how many bytes can be sent with each publish on each call of run
        				PipeWriter.writeBytes(zipData, activePosition, bufferArray);
        				activePosition += maxChunkSize;
            			
//            			String name = ze.getName();
//            			if (name.endsWith(".txt")) {
//            				try {
//            					InputStream in = loadFile.getInputStream(ze);
//            					} catch (IOException e1) {
            						// TODO Auto-generated catch block\
//            						e1.printStackTrace();
//            						}
            				// read from 'in'
            				//PipeWriter.writeBytes(zipData, activePosition, source);
//            				PipeWriter.writeBytes(zipData, activePosition, bufferArray);
            				}
            			} catch (IOException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
            	finally {
            		PipeWriter.publishWrites(zipData);
            		}
            	} // end of if (tryWriteFragment) block	
        	} // end of if (isFirstRun) block
        else if(!isFirstRun)
        {
        	//load(loadFile, boS, bufferArray);
        	activePosition += maxChunkSize;
        	
        	//PipeWriter.writeUTF8(zipData, ZipFileSchema.MSG_CONTAINEROPEN_8_FIELD_NAME_21, "jarName");
        	PipeWriter.publishWrites(zipData);
        }
        else // can't write to pipe
        	{
        	System.out.println("Can't write to pipe");
        	}
        
        
        //read every entry from the zip file.
        //on all other calls continue reading entries from the zip file.
        
        	/* using all these try/catch blocks because other option was to include a "throws" statement
            and then we get
            "Exception IOException is not compatible with throws clause in PronghornStage.run()"
            */
        
        
        //each entry is sent to the pipe as one or more messages each no longer than maxChunkSize.
        //Must start with begin and end with end message id.
		
        
        //when the end of the zip file is reached:  close the file, send EOFPublish, and call requestShutdown();
        //if(EOF)
        /*
        {
        	try {
    			loadFile.close();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            PipeWriter.publishEOF(zipData);
            zipData.closeBlobFieldWrite();
            requestShutdown();
          
            

        } */
        isFirstRun = false;
        
        } // end of run()
}
    
    /*
    static void load(ZipFile loadFile, ByteArrayOutputStream boS,byte[]bufferArray){
    	try {
    		for (Enumeration<? extends ZipEntry> e = loadFile.entries();
    				e.hasMoreElements();) {
    			ZipEntry ze = e.nextElement();
    			InputStream in = loadFile.getInputStream(ze);
    			boS.write(bufferArray, activePosition, bufferArray.length);
    			bufferArray = boS.toByteArray();
				PipeWriter.writeBytes(zipData, activePosition, bufferArray);

    			
    			
//    			String name = ze.getName();
//    			if (name.endsWith(".txt")) {
//    				try {
//    					InputStream in = loadFile.getInputStream(ze);
//    					} catch (IOException e1) {
    						// TODO Auto-generated catch block\
//    						e1.printStackTrace();
//    						}
    				// read from 'in'
    				//PipeWriter.writeBytes(zipData, activePosition, source);
//    				PipeWriter.writeBytes(zipData, activePosition, bufferArray);
    				}
    			} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	finally {
    			try {
    				loadFile.close();
    				requestShutdown(); // *** 
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    					}
    			}
   
    }
    
    */