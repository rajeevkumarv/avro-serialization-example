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
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class SpecificAvroDataDeserializer<T> {
	
	public static <T> ISpecificAvroDeserializer<T> getJsonDeserializer(Schema schema) throws IOException{
		return new JsonDeserializer<T>(schema);
	}

	public static <T>  ISpecificAvroDeserializer<T> getBinaryDeserializer(Schema schema) throws IOException{
		return new BinaryDeserializer<T>(schema);
	}
	
	static class JsonDeserializer<T> implements ISpecificAvroDeserializer<T>{

		private Schema schema = null;
		private DatumReader<T> reader = null;
		private JsonDecoder decoder = null;

		public JsonDeserializer(Schema schema) throws IOException{
			this.schema=schema;
			reader = new SpecificDatumReader<>(schema);
		}

		public T read(InputStream inputStream) throws IOException{
			decoder = DecoderFactory.get().jsonDecoder(schema,inputStream);
			return reader.read(null,decoder) ;
		}

		public List<T> readMultiple(ByteArrayInputStream inputStream) throws IOException{
			decoder = DecoderFactory.get().jsonDecoder(schema,inputStream);
			List<T> records = new ArrayList<T>();
			try{
				records.add( reader.read(null,decoder) );
			}catch(EOFException e){			
			}
			return records;
		}
	}

	static class BinaryDeserializer<T> implements ISpecificAvroDeserializer<T>{

		private Schema schema = null;
		private SpecificDatumReader<T> reader = null;
		private BinaryDecoder decoder = null;

		public BinaryDeserializer(Schema schema) throws IOException{
			this.schema=schema;
			reader = new SpecificDatumReader<T>(schema);
		}

		public T read(InputStream inputStream) throws IOException{
			decoder = DecoderFactory.get().binaryDecoder(inputStream,null);
			return reader.read(null,decoder) ;
		}

		public List<T> readMultiple(ByteArrayInputStream inputStream) throws IOException{
			decoder = DecoderFactory.get().binaryDecoder(inputStream,null);
			List<T> records = new ArrayList<T>();
			try{
				records.add( reader.read(null,decoder) );
			}catch(EOFException e){			
			}
			return records;
		}

	}
}

