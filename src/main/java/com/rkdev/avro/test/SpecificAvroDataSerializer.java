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
import org.apache.avro.specific.SpecificDatumWriter;

public class SpecificAvroDataSerializer {

	static class JsonSerializer<T> implements ISpecificAvroSerializer<T>{
		
		private Schema schema = null;
		private DatumWriter<T> writer = null;
		private JsonEncoder encoder = null;
		
		public JsonSerializer(Schema schema,OutputStream outstream) throws IOException{
			this.schema=schema;
			writer = new SpecificDatumWriter<T>(schema);
			encoder = EncoderFactory.get().jsonEncoder(schema,outstream);
		}
		
		public void append(T record) throws IOException{
			writer.write(record, encoder);
		}
		
		public void flush() throws IOException{
			encoder.flush();
		}		
	}

	static class BinarySerializer<T> implements ISpecificAvroSerializer<T>{
		
		private Schema schema = null;
		private DatumWriter<T> writer = null;
		private BinaryEncoder encoder = null;
		
		public BinarySerializer(Schema schema,OutputStream outstream) throws IOException{
			this.schema=schema;
			writer = new SpecificDatumWriter<T>(schema);
			encoder = EncoderFactory.get().binaryEncoder(outstream,null);
		}
		
		public void append(T record) throws IOException{
			writer.write(record, encoder);
		}
		
		public void flush() throws IOException{
			encoder.flush();
		}		
	}
	
	public static <T> ISpecificAvroSerializer<T> getJsonSerializer(Schema schema,OutputStream outstream) throws IOException{
		return new JsonSerializer<T>(schema,outstream);
	}
	
	public static <T> ISpecificAvroSerializer<T> getBinarySerializer(Schema schema,OutputStream outstream) throws IOException{
		return new BinarySerializer<T>(schema,outstream);
	}
}


