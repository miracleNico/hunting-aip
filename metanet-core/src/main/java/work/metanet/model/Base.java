/**
 * @Description 文件：Base.java
 * @author EdisonFeng
 * @DateTime 2021年5月14日
 * Copyright(c) 2021. All Rights Reserved
 */
package work.metanet.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Edison F.
 * @Description 基类，提供三个基础字段
 * @DateTime 2021/05/14
 */
@Accessors(chain = true)
@Data
public class Base {
    /**
     * 创建时间（默认系统时间,修改新增都不需要手动插入）
     */	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间（默认系统时间,修改新增都不需要手动插入）", example = "2021-05-01 12:30:59")
	private Date createTime;
	
    /**
     * 修改时间（默认系统时间,不需要手动插入）
     */	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "修改时间（默认系统时间,修改新增都不需要手动插入）", example = "2021-05-01 12:30:59")
	private Date updateTime;
    /**
     * 备注
     */	
	@ApiModelProperty(value = "备注", example = "这是一个备注")
	private String remark;
}
