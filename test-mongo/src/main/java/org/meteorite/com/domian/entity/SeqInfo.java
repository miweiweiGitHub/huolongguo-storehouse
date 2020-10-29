package org.meteorite.com.domian.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = "sequence")
public class SeqInfo {

	@Id
	private String id;// 主键

	@Field
	private String collName;// 集合名称

	@Field
	private Long seqId;// 序列值

}