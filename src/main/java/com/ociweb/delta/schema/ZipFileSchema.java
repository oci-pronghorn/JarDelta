package com.ociweb.delta.schema;

import com.ociweb.pronghorn.pipe.FieldReferenceOffsetManager;
import com.ociweb.pronghorn.pipe.MessageSchema;
public class ZipFileSchema extends MessageSchema {

    public final static FieldReferenceOffsetManager FROM = new FieldReferenceOffsetManager(
            new int[]{0xc0400003,0xa0000000,0xb8000001,0xc0200003,0xc0400003,0xa0000000,0xb8000001,0xc0200003,0xc0400003,0xa0000000,0xb8000001,0xc0200003,0xc0400003,0xa0000000,0xb8000001,0xc0200003,0xc0400002,0xa0000002,0xc0200002},
            (short)0,
            new String[]{"EntryFull","name","ByteArray",null,"EntryBegin","name","ByteArray",null,"EntryContinuation","name","ByteArray",null,"EntryClose","name","ByteArray",null,"ContainerOpen","name",null},
            new long[]{7, 11, 12, 0, 5, 11, 12, 0, 4, 11, 12, 0, 6, 11, 12, 0, 8, 21, 0},
            new String[]{"global",null,null,null,"global",null,null,null,"global",null,null,null,"global",null,null,null,"global",null,null},
            "ZipFileSchema.xml",
            new long[]{2, 2, 0},
            new int[]{2, 2, 0});
    
    public static final int MSG_ENTRYFULL_7 = 0x00000000;
    public static final int MSG_ENTRYFULL_7_FIELD_NAME_11 = 0x01000001;
    public static final int MSG_ENTRYFULL_7_FIELD_BYTEARRAY_12 = 0x01C00003;
    
    public static final int MSG_ENTRYBEGIN_5 = 0x00000004;
    public static final int MSG_ENTRYBEGIN_5_FIELD_NAME_11 = 0x01000001;
    public static final int MSG_ENTRYBEGIN_5_FIELD_BYTEARRAY_12 = 0x01C00003;
    
    public static final int MSG_ENTRYCONTINUATION_4 = 0x00000008;
    public static final int MSG_ENTRYCONTINUATION_4_FIELD_NAME_11 = 0x01000001;
    public static final int MSG_ENTRYCONTINUATION_4_FIELD_BYTEARRAY_12 = 0x01C00003;
    
    public static final int MSG_ENTRYCLOSE_6 = 0x0000000C;
    public static final int MSG_ENTRYCLOSE_6_FIELD_NAME_11 = 0x01000001;
    public static final int MSG_ENTRYCLOSE_6_FIELD_BYTEARRAY_12 = 0x01C00003;
    
    public static final int MSG_CONTAINEROPEN_8 = 0x00000010;
    public static final int MSG_CONTAINEROPEN_8_FIELD_NAME_21 = 0x01000001;
    
    public static final ZipFileSchema instance = new ZipFileSchema();
    
    private ZipFileSchema() {
        super(FROM);
    }
        
}
