package cn.jeefast.system.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 收藏管理
 * </p>
 *
 */
@TableName("sys_gwc")
public class SysGwc extends Model<SysGwc> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(type = IdType.UUID)
	private String id;
    /**
     * 物品id
     */
	private String foodid;
    /**
     * 所属用户
     */
	private String createuser;
    /**
     * 创建时间
     */
	private Date cratetime;
    /**
     * 更新用户
     */
	private String updateuser;
    /**
     * 更新时间
     */
	private Date updatetime;
    /**
     * 所属商家
     */
	private String sssj;
	private String shdz;//物品送达地址

	@TableField(exist = false)
	private SysFood sysFood;

	@TableField(exist = false)
	private String  realname;

	@TableField(exist = false)
	private String foodname;

	private Integer sl;

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public SysFood getSysFood() {
		return sysFood;
	}

	public void setSysFood(SysFood sysFood) {
		this.sysFood = sysFood;
	}

	public String getShdz() {
		return shdz;
	}

	public void setShdz(String shdz) {
		this.shdz = shdz;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFoodid() {
		return foodid;
	}

	public void setFoodid(String foodid) {
		this.foodid = foodid;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public Date getCratetime() {
		return cratetime;
	}

	public void setCratetime(Date cratetime) {
		this.cratetime = cratetime;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getSssj() {
		return sssj;
	}

	public void setSssj(String sssj) {
		this.sssj = sssj;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysGwc{" +
				"id='" + id + '\'' +
				", foodid='" + foodid + '\'' +
				", createuser='" + createuser + '\'' +
				", cratetime=" + cratetime +
				", updateuser='" + updateuser + '\'' +
				", updatetime=" + updatetime +
				", sssj='" + sssj + '\'' +
				", shdz='" + shdz + '\'' +
				", sysFood=" + sysFood +
				", realname='" + realname + '\'' +
				", foodname='" + foodname + '\'' +
				", sl=" + sl +
				'}';
	}
}
