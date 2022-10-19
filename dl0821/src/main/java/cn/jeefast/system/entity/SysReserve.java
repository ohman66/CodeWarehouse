package cn.jeefast.system.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 预定餐位
 * </p>
 *
 * @author theodo
 * @since 2021-12-06
 */
@TableName("sys_reserve")
public class SysReserve extends Model<SysReserve> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(type = IdType.UUID)
	private String id;
    /**
     * 餐位是否单间：0为否、1为是
     */
	private String aroom;
    /**
     * 餐位是否靠窗：0为否、1为是
     */
	private String havewindow;
    /**
     * 餐位是否露天：0为否、1为是
     */
	private String outdoor;
    /**
     * 餐位可容纳的人数
     */
	private String numberperpor;
    /**
     * 预定时间
     */
	private Date useinftime;
    /**
     * 更新记录时间
     */
	private Date updatetime;

	private String bjh;

	private Double yudfy;

	public Date getUseinftime() {
		return useinftime;
	}

	public void setUseinftime(Date useinftime) {
		this.useinftime = useinftime;
	}

	public Double getYudfy() {
		return yudfy;
	}

	public void setYudfy(Double yudfy) {
		this.yudfy = yudfy;
	}

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAroom() {
		return aroom;
	}

	public void setAroom(String aroom) {
		this.aroom = aroom;
	}

	public String getHavewindow() {
		return havewindow;
	}

	public void setHavewindow(String havewindow) {
		this.havewindow = havewindow;
	}

	public String getOutdoor() {
		return outdoor;
	}

	public void setOutdoor(String outdoor) {
		this.outdoor = outdoor;
	}

	public String getNumberperpor() {
		return numberperpor;
	}

	public void setNumberperpor(String numberperpor) {
		this.numberperpor = numberperpor;
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
		return "SysReserve{" +
				"id='" + id + '\'' +
				", aroom='" + aroom + '\'' +
				", havewindow='" + havewindow + '\'' +
				", outdoor='" + outdoor + '\'' +
				", numberperpor='" + numberperpor + '\'' +
				", useinftime=" + useinftime +
				", updatetime=" + updatetime +
				", bjh='" + bjh + '\'' +
				", yudfy=" + yudfy +
				'}';
	}
}
