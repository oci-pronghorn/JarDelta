package com.ociweb.delta.schema;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ociweb.pronghorn.iot.schema.GoSchema;
import com.ociweb.pronghorn.pipe.util.build.FROMValidation;

public class SchemaValidation {

    @Test
    public void zipFileSchemaFROMTest() {
        assertTrue(FROMValidation.testForMatchingFROMs("/ZipFileSchema.xml", ZipFileSchema.instance));
        assertTrue(FROMValidation.testForMatchingLocators(ZipFileSchema.instance));
    }
   
    @Test
    public void deltaSchemaFROMTest() {
        assertTrue(FROMValidation.testForMatchingFROMs("/DeltaSchema.xml", DeltaSchema.instance));
        assertTrue(FROMValidation.testForMatchingLocators(DeltaSchema.instance));
    }
    
}
