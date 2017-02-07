package com.rkdev.avro.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import com.rkdev.avro.User;


public class TestFileSerialization_Main {

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
		File file = new File("test_generic_serialization.avro");
		try(FileOutputStream outstream = new FileOutputStream(file)){
			IGenericAvroSerializer serializer = GenericAvroDataSerializer.getBinarySerializer(schema,outstream);
			//Create Record 1
			serializer.append(createGenericUser(schema));
			serializer.flush();
		}

		try(FileInputStream fileInputStream = new FileInputStream(file)){
			IGenericAvroDeserializer deserializer = GenericAvroDataDeserializer.getBinaryDeserializer(schema);
			GenericRecord record = deserializer.read(fileInputStream);
			System.out.println("Deserialize Generic - "+record.get("name"));
		}
	}

	public static void TrySpecific(Schema schema) throws IOException{

		File file = new File("test_specific_serialization.avro");
		try(FileOutputStream outstream = new FileOutputStream(file)){
		ISpecificAvroSerializer<User> serializer = SpecificAvroDataSerializer.getBinarySerializer(schema,outstream);
		//Create Record 1
		serializer.append(createUser(schema));
		serializer.flush();
		}

		try(FileInputStream fileInputStream = new FileInputStream(file)){
		ISpecificAvroDeserializer<User> deserializer = SpecificAvroDataDeserializer.getBinaryDeserializer(schema);
		User record = deserializer.read(fileInputStream);
		System.out.println("Deserialize Specific - "+record.getName());	
		}
	}

	public static void main(String[] args) throws IOException {

		File schemaFile=  new File("src/main/avro/user.avsc");
		Schema schema = new Schema.Parser().parse(schemaFile);

		try{
			TrySpecific(schema);
			TryGeneric(schema);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}
	}
}
