package com.ociweb.delta.schema;

import com.ociweb.pronghorn.pipe.FieldReferenceOffsetManager;
import com.ociweb.pronghorn.pipe.MessageSchema;
public class ZipFileSchema extends MessageSchema {

    public final static FieldReferenceOffsetManager FROM = new FieldReferenceOffsetManager(
            new int[]{0xc0400003,0xa0000000,0xb8000001,0xc0200003,0xc0400002,0xa0000002,0xc0200002},
            (short)0,
            new String[]{"Entry","name","ByteArray",null,"ContainerOpen","name",null},
            new long[]{10, 11, 12, 0, 20, 21, 0},
            new String[]{"global",null,null,null,"global",null,null},
            "ZipFileSchema.xml",
            new long[]{2, 2, 0},
            new int[]{2, 2, 0});
    
    public static final int MSG_ENTRY_10 = 0x00000000;
    public static final int MSG_ENTRY_10_FIELD_NAME_11 = 0x01000001;
    public static final int MSG_ENTRY_10_FIELD_BYTEARRAY_12 = 0x01C00003;
    public static final int MSG_CONTAINEROPEN_20 = 0x00000004;
    public static final int MSG_CONTAINEROPEN_20_FIELD_NAME_21 = 0x01000001;
    
    public static final ZipFileSchema instance = new ZipFileSchema();
    
    private ZipFileSchema() {
        super(FROM);
    }
        
}
