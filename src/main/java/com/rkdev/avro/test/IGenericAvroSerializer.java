package com.rkdev.avro.test;

import java.io.IOException;

import org.apache.avro.generic.GenericRecord;

public interface IGenericAvroSerializer {

	void append(GenericRecord record) throws IOException;

	void flush() throws IOException;

	GenericRecord Record();
	
}
