# avro-serialization-example
Avro sample code for JSON and Binary Serialization to file. This example also performs a comparison of serialization with schema generated code and generic serialization.

# Sample Code for
- Json Serialization - Test is implemented in TestFileJsonSerialization_Main
- Binary Serialization - Test is implemented in TestFileSerialization_Main
- Serialization with Schema Generated code - This is implemented in SpecificAvroDataSerializer and SpecificAvroDataDeserializer. pom.xml uses avro plugin to automatically compile schema and generate code
- Serialization with Generic  - This is implemented in GenericAvroSerializer and GenericAvroDeserializer
- Code to test speed comparison - This is implemeted in TestSpeed_Main

# Quick Start 
#### Binary Serialization to file
Following command will compile and run the class 'TestFileSerialization_Main' to perform binary serialization
###### mvn clean compile exec:java -Dexec.mainClass="com.rkdev.avro.test.TestFileSerialization_Main"

#### Json Serialization to file
Following command will compile and run the class 'TestFileJsonSerialization_Main' to perform Json serialization
###### mvn clean compile exec:java -Dexec.mainClass="com.rkdev.avro.test.TestFileJsonSerialization_Main"

#### Compare speed of schema generated code serialization with generic serialization
Following command will compile and run the class 'TestSpeed_Main' to perform Json serialization
###### mvn clean compile exec:java -Dexec.mainClass="com.rkdev.avro.test.TestSpeed_Main"

# Prerequisite
JDK 1.8
Maven 3.3.9





