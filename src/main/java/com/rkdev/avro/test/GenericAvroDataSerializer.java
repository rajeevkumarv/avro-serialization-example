package com.rkdev.avro.test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.io.JsonEncoder;

public class GenericAvroDataSerializer {
	
	public static IGenericAvroSerializer getJsonSerializer(Schema schema,OutputStream outstream) throws IOException{
		return new JsonSerializer(schema,outstream);
	}
	
	public static IGenericAvroSerializer getBinarySerializer(Schema schema,OutputStream outstream) throws IOException{
		return new BinarySerializer(schema,outstream);
	}
}

class JsonSerializer implements IGenericAvroSerializer{
	
	private Schema schema = null;
	private DatumWriter<GenericRecord> writer = null;
	private JsonEncoder encoder = null;
	
	public JsonSerializer(Schema schema,OutputStream outstream) throws IOException{
		this.schema=schema;
		writer = new GenericDatumWriter<>(schema);
		encoder = EncoderFactory.get().jsonEncoder(schema,outstream);
	}
	
	public void append(GenericRecord record) throws IOException{
		writer.write(record, encoder);
	}
	
	public void flush() throws IOException{
		encoder.flush();
	}
	
	public GenericRecord Record(){
		return new GenericData.Record(schema);
	}
}

class BinarySerializer implements IGenericAvroSerializer{
	
	private Schema schema = null;
	private DatumWriter<GenericRecord> writer = null;
	private BinaryEncoder encoder = null;
	
	public BinarySerializer(Schema schema,OutputStream outstream) throws IOException{
		this.schema=schema;
		writer = new GenericDatumWriter<>(schema);
		encoder = EncoderFactory.get().binaryEncoder(outstream,null);
	}
	
	public void append(GenericRecord record) throws IOException{
		writer.write(record, encoder);
	}
	
	public void flush() throws IOException{
		encoder.flush();
	}
	
	public GenericRecord Record(){
		return new GenericData.Record(schema);
	}
}
