package com.rkdev.avro.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import com.rkdev.avro.User;


public class TestSpeed_Main {

	//Loop serialization and deserialization these many number of times 
	public static int LOOP_COUNT=100;

	static byte[] image = null;
	static{
		Path path = Paths.get("src/main/resources/sample_img_approx_8_mb.jpg");
		try {
			image =java.nio.file.Files.readAllBytes(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static ByteBuffer binaryData() throws IOException{		
		return ByteBuffer.wrap(image);
	}

	public static GenericRecord createGenericUser(Schema schema) throws IOException{
		GenericRecord user = new GenericData.Record(schema);
		user.put("name","Bond007");	
		user.put("photo",binaryData());		
		return user;
	}

	public static User createUser(Schema schema) throws IOException{
		User user = new User();
		user.setName("Bond007");
		user.setPhoto(binaryData());
		return user;
	}

	public static void TryGeneric(Schema schema) throws IOException{

		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		IGenericAvroSerializer serializer = GenericAvroDataSerializer.getBinarySerializer(schema,outstream);

		//Create Record 1
		serializer.append(createGenericUser(schema));
		serializer.flush();

		IGenericAvroDeserializer deserializer = GenericAvroDataDeserializer.getBinaryDeserializer(schema);
		GenericRecord record = deserializer.read(new ByteArrayInputStream(outstream.toByteArray()));
		System.out.println("Deserialize Generic - "+record.get("name"));	
	}

	public static void TrySpecific(Schema schema) throws IOException{

		ByteArrayOutputStream outstream = new ByteArrayOutputStream();

		ISpecificAvroSerializer<User> serializer = SpecificAvroDataSerializer.getBinarySerializer(schema,outstream);

		//Create Record 1
		serializer.append(createUser(schema));
		serializer.flush();

		ISpecificAvroDeserializer<User> deserializer = SpecificAvroDataDeserializer.getBinaryDeserializer(schema);
		User record = deserializer.read(new ByteArrayInputStream(outstream.toByteArray()));
		System.out.println("Deserialize Specific - "+record.getName());	
	}

	public static void main(String[] args) throws IOException {

		File schemaFile=  new File("src/main/avro/user.avsc");
		Schema schema = new Schema.Parser().parse(schemaFile);

		try{
			long s1 = System.nanoTime();
			for(int i=0;i<LOOP_COUNT;i++){
				TrySpecific(schema);
			}
			long e1 = System.nanoTime();
			
			long s2 = System.nanoTime();
			for(int i=0;i<LOOP_COUNT;i++){
				TryGeneric(schema);
			}
			System.out.println("Time taken with code generated serialization is "+ Long.toString( ((e1 - s1))/(1000*1000) ) + " mili seconds");
			System.out.println("Time taken with  generic way is "+ Long.toString( ((System.nanoTime() - s2))/(1000*1000) ) + " mili seconds");

		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}


	}
}
