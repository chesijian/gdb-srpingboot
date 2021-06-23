package com.jrsoft.business.modules.material.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "T_IMS_IN_WEIGH")
public class Material{
	
	
	@ApiModelProperty(value="磅单",name="poundCode" )
//	@Column(name = "POUND_CODE_")
	private String poundCode;
	
	
	@ApiModelProperty(value="车牌号",name="licensePlateNumber" )
//	@Column(name = "LICENSE_PLATE_NUMBER_")
	private String licensePlateNumber;
	
	
	@ApiModelProperty(value="0:待检, 1:已检",name="status" )
//	@Column(name = "STATUS_")
	private String status;
	
	
	@ApiModelProperty(value="供应商",name="supplierName" )
//	@Column(name = "SUPPLIER_NAME_")
	private String supplierName;
	
	
	@ApiModelProperty(value="品名",name="commodityName" )
//	@Column(name = "COMMODITY_NAME_")
	private String commodityName;
	
	
	@ApiModelProperty(value="型号",name="modelCode" )
//	@Column(name = "MODEL_CODE_")
	private String modelCode;
	
	
	@ApiModelProperty(value="毛重",name="roughWeight" )
//	@Column(name = "ROUGH_WEIGHT_")
	private String roughWeight;
	
	
	@ApiModelProperty(value="皮重",name="tare" )
//	@Column(name = "TARE_")
	private String tare;
	
	
	@ApiModelProperty(value="净重",name="suttle" )
//	@Column(name = "SUTTLE_")
	private String suttle;
	
	
	@ApiModelProperty(value="时间",name="date" )
//	@Column(name = "DATE_")
	private String date;
	
	
	@ApiModelProperty(value="验收人",name="acceptorName" )
//	@Column(name = "ACCEPTOR_NAME_")
	private String acceptorName;
	
	
}
