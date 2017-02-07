package com.rkdev.avro.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.avro.generic.GenericRecord;

public interface ISpecificAvroDeserializer<T> {

	T read(InputStream inputStream) throws IOException;

	public List<T> readMultiple(ByteArrayInputStream inputStream) throws IOException;
	
}
