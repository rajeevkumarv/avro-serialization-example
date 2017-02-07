package com.rkdev.avro.test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.JsonDecoder;

public class GenericAvroDataDeserializer {
	
	public static  IGenericAvroDeserializer getJsonDeserializer(Schema schema) throws IOException{
		return new JsonDeserializer(schema);
	}

	public static  IGenericAvroDeserializer getBinaryDeserializer(Schema schema) throws IOException{
		return new BinaryDeserializer(schema);
	}
	
	static class JsonDeserializer implements IGenericAvroDeserializer{

		private Schema schema = null;
		private DatumReader<GenericRecord> reader = null;
		private JsonDecoder decoder = null;

		public JsonDeserializer(Schema schema) throws IOException{
			this.schema=schema;
			reader = new GenericDatumReader<>(schema);
		}

		public GenericRecord read(InputStream inputStream) throws IOException{
			decoder = DecoderFactory.get().jsonDecoder(schema,inputStream);
			return reader.read(null,decoder) ;
		}

		public List<GenericRecord> readMultiple(ByteArrayInputStream inputStream) throws IOException{
			decoder = DecoderFactory.get().jsonDecoder(schema,inputStream);
			List<GenericRecord> records = new ArrayList<GenericRecord>();
			try{
				records.add( reader.read(null,decoder) );
			}catch(EOFException e){			
			}
			return records;
		}
	}

	static class BinaryDeserializer implements IGenericAvroDeserializer{

		private Schema schema = null;
		private DatumReader<GenericRecord> reader = null;
		private BinaryDecoder decoder = null;

		public BinaryDeserializer(Schema schema) throws IOException{
			this.schema=schema;
			reader = new GenericDatumReader<>(schema);
		}

		public GenericRecord read(InputStream inputStream) throws IOException{
			decoder = DecoderFactory.get().binaryDecoder(inputStream,null);
			return reader.read(null,decoder) ;
		}

		public List<GenericRecord> readMultiple(ByteArrayInputStream inputStream) throws IOException{
			decoder = DecoderFactory.get().binaryDecoder(inputStream,null);
			List<GenericRecord> records = new ArrayList<GenericRecord>();
			try{
				records.add( reader.read(null,decoder) );
			}catch(EOFException e){			
			}
			return records;
		}

	}
}

