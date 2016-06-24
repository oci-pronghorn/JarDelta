package com.ociweb.delta.schema;

import com.ociweb.pronghorn.pipe.FieldReferenceOffsetManager;
import com.ociweb.pronghorn.pipe.MessageSchema;
public class DeltaSchema extends MessageSchema {

    public final static FieldReferenceOffsetManager FROM = new FieldReferenceOffsetManager(
            new int[]{0xc0400002,0xb8000000,0xc0200002,0xc0400002,0x88000000,0xc0200002,0xc0400003,0x88000001,0x88000002,0xc0200003,0xc0400002,0xa0000001,0xc0200002,0xc0400002,0xa0000002,0xc0200002},
            (short)0,
            new String[]{"AppendBytes","ByteArray",null,"ReplicateBytes","count",null,"CopyRangeBytes","position","length",null,"NewEntry","name",null,"CloseEntry","name",null},
            new long[]{10, 12, 0, 20, 21, 0, 30, 31, 32, 0, 40, 41, 0, 50, 51, 0},
            new String[]{"global",null,null,"global",null,null,"global",null,null,null,"global",null,null,"global",null,null},
            "DeltaSchema.xml",
            new long[]{2, 2, 0},
            new int[]{2, 2, 0});
    
    public static final int MSG_APPENDBYTES_10 = 0x00000000;
    public static final int MSG_APPENDBYTES_10_FIELD_BYTEARRAY_12 = 0x01C00001;
    public static final int MSG_REPLICATEBYTES_20 = 0x00000003;
    public static final int MSG_REPLICATEBYTES_20_FIELD_COUNT_21 = 0x00400001;
    public static final int MSG_COPYRANGEBYTES_30 = 0x00000006;
    public static final int MSG_COPYRANGEBYTES_30_FIELD_POSITION_31 = 0x00400001;
    public static final int MSG_COPYRANGEBYTES_30_FIELD_LENGTH_32 = 0x00400002;
    public static final int MSG_NEWENTRY_40 = 0x0000000A;
    public static final int MSG_NEWENTRY_40_FIELD_NAME_41 = 0x01000001;
    public static final int MSG_CLOSEENTRY_50 = 0x0000000D;
    public static final int MSG_CLOSEENTRY_50_FIELD_NAME_51 = 0x01000001;
    
    public static final DeltaSchema instance = new DeltaSchema();
    
    private DeltaSchema() {
        super(FROM);
    }
        
}
