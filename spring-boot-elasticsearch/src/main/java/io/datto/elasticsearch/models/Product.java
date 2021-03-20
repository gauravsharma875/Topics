
package io.datto.elasticsearch.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "deviceindex")
public class Product {
	@Id
    private String id;


	@Field(type = FieldType.Text, name = "name")
	private String name;

	@Field(type = FieldType.Double, name = "price")
	private Double price;

	@Field(type = FieldType.Integer, name = "quantity")
	private Integer quantity;

	@Field(type = FieldType.Keyword, name = "category")
	private String category;

	@Field(type = FieldType.Text, name = "desc")
	private String description;

	@Field(type = FieldType.Keyword, name = "manufacturer")
	private String manufacturer;

	@Field(type = FieldType.Text, name = "uuid")
	private String Profile;

	/*@Field(type = FieldType.Text, name = "uuid")
	private String uuid;

	@Field(type = FieldType.Text, name = "description")
	private String description;*/

	@Field(type = FieldType.Text, name = "ipAddress")
	private String ipAddress;

	@Field(type = FieldType.Text, name = "extIPAddr")
	private String extIPAddr;

	@Field(type = FieldType.Text, name = "lastUser")
	private String lastUser;

	@Field(type = FieldType.Text, name = "agentVersion")
	private String agentVersion;

	@Field(type = FieldType.Text, name = "model")
	private String model;

	@Field(type = FieldType.Text, name = "operatingSystem")
	private String operatingSystem;

	@Field(type = FieldType.Text, name = "serialNumber")
	private String serialNumber;

	@Field(type = FieldType.Text, name = "motherboard")
	private String motherboard;

	@Field(type = FieldType.Text, name = "customField1")
	private String customField1;

	@Field(type = FieldType.Text, name = "customField2")
	private String customField2;

	@Field(type = FieldType.Text, name = "customField3")
	private String customField3;

	@Field(type = FieldType.Text, name = "customField4")
	private String customField4;

	@Field(type = FieldType.Text, name = "customField5")
	private String customField5;

}
