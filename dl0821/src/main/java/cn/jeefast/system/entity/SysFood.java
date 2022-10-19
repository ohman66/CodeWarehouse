package cn.jeefast.system.entity;

import java.io.Serializable;

import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 物品信息表
 * </p>
 *
 * @author theodo
 * @since 2021-12-06
 */
@TableName("sys_food")
public class SysFood extends Model<SysFood> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(type = IdType.UUID)
	private String id;
    /**
     * 物品的名称
     */
	private String foodname;
    /**
     * 厨师的ID
     */
	private String coolid;
    /**
     * 物品图片的位置
     */
	private String picturelocation;
    /**
     * 物品的发源地分类
     */
	private String localcolor;

	@TableField(exist = false)
	private String localcolorname;
    /**
     * 物品的食品特色分类
     */
	private String foodtype;

	@TableField(exist = false)
	private String foodtypename;
    /**
     * 物品的口味分类
     */
	private String taste;

	@TableField(exist = false)
	private String tastename;
    /**
     * 物品的描述
     */
	private String describe;
    /**
     * 物品是否新近出品：0为否、1为是
     */
	private String newfood;
    /**
     * 更新时间
     */
	private Date updatetime;

	private Double jiage;

	/**
	 * 附件信息
	 */
	@TableField(exist = false)
	private JSONArray files;

	@TableField(exist = false)
	private String tp;

	private String isrxsp;//是否热销物品1：是2：否

	@TableField(exist = false)
	private String isrxspname;

	@TableField(exist = false)
	private Integer ddsl;//统计租赁天数

	private String issj;//是否上架
	private Double bzj;//租赁保证金

	public String getIssj() {
		return issj;
	}

	public void setIssj(String issj) {
		this.issj = issj;
	}

	public Double getBzj() {
		return bzj;
	}

	public void setBzj(Double bzj) {
		this.bzj = bzj;
	}

	public Integer getDdsl() {
		return ddsl;
	}

	public void setDdsl(Integer ddsl) {
		this.ddsl = ddsl;
	}

	public String getIsrxspname() {
		return isrxspname;
	}

	public void setIsrxspname(String isrxspname) {
		this.isrxspname = isrxspname;
	}

	public String getIsrxsp() {
		return isrxsp;
	}

	public void setIsrxsp(String isrxsp) {
		this.isrxsp = isrxsp;
	}

	public String getLocalcolorname() {
		return localcolorname;
	}

	public void setLocalcolorname(String localcolorname) {
		this.localcolorname = localcolorname;
	}

	public String getFoodtypename() {
		return foodtypename;
	}

	public void setFoodtypename(String foodtypename) {
		this.foodtypename = foodtypename;
	}

	public String getTastename() {
		return tastename;
	}

	public void setTastename(String tastename) {
		this.tastename = tastename;
	}

	public Double getJiage() {
		return jiage;
	}

	public void setJiage(Double jiage) {
		this.jiage = jiage;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public JSONArray getFiles() {
		return files;
	}

	public void setFiles(JSONArray files) {
		this.files = files;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public String getCoolid() {
		return coolid;
	}

	public void setCoolid(String coolid) {
		this.coolid = coolid;
	}

	public String getPicturelocation() {
		return picturelocation;
	}

	public void setPicturelocation(String picturelocation) {
		this.picturelocation = picturelocation;
	}

	public String getLocalcolor() {
		return localcolor;
	}

	public void setLocalcolor(String localcolor) {
		this.localcolor = localcolor;
	}

	public String getFoodtype() {
		return foodtype;
	}

	public void setFoodtype(String foodtype) {
		this.foodtype = foodtype;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getNewfood() {
		return newfood;
	}

	public void setNewfood(String newfood) {
		this.newfood = newfood;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysFood{" +
				"id='" + id + '\'' +
				", foodname='" + foodname + '\'' +
				", coolid='" + coolid + '\'' +
				", picturelocation='" + picturelocation + '\'' +
				", localcolor='" + localcolor + '\'' +
				", localcolorname='" + localcolorname + '\'' +
				", foodtype='" + foodtype + '\'' +
				", foodtypename='" + foodtypename + '\'' +
				", taste='" + taste + '\'' +
				", tastename='" + tastename + '\'' +
				", describe='" + describe + '\'' +
				", newfood='" + newfood + '\'' +
				", updatetime=" + updatetime +
				", jiage=" + jiage +
				", files=" + files +
				", tp='" + tp + '\'' +
				", isrxsp='" + isrxsp + '\'' +
				", isrxspname='" + isrxspname + '\'' +
				", ddsl=" + ddsl +
				", issj='" + issj + '\'' +
				", bzj=" + bzj +
				'}';
	}
}
