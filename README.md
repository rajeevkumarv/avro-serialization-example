# avro-serialization-example
Avro sample code for JSON and Binary Serialization to file. This example also performs a comparison of serialization with schema generated code and generic serialization.

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





