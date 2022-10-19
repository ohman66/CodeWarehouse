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
 * 
 * </p>
 *
 */
@TableName("sys_dingdan")
public class SysDingdan extends Model<SysDingdan> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(type = IdType.UUID)
	private String id;
    /**
     * 租赁类型
     */
	private String ddtype;
    /**
     * 租赁金额
     */
	private String ddje;
    /**
     * 预定开始时间
     */
	private Date kssj;
    /**
     * 所属父id
     */
	private String parintid;
	private Date updatetime;
    /**
     * 预定人员
     */
	private String ydry;
    /**
     * 预定结束时间
     */
	private Date jssj;

	@TableField(exist = false)
	private String name;

	private String shdz;//物品送达地址

	private String hjxdh;//合计下单标识号

	private String wlzt;//使用状态

	private String sfqrsh;//是否确认还租1：确认还租2：未收货3：申请退货
	private String sqthzt;//申请退货状态1：允许申请退货2：拒绝申请
	private String thyy;//退货原因

	@TableField(exist = false)
	private SysFood sysFood;

	private Double zongjiage;

	private Integer sl;

	private String bzjthsq;//保证金退还申请0:申请中1：退还2：拒绝退换保证金

	public String getBzjthsq() {
		return bzjthsq;
	}

	public void setBzjthsq(String bzjthsq) {
		this.bzjthsq = bzjthsq;
	}

	public Double getZongjiage() {
		return zongjiage;
	}

	public void setZongjiage(Double zongjiage) {
		this.zongjiage = zongjiage;
	}

	public Integer getSl() {
		return sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}

	public String getSfqrsh() {
		return sfqrsh;
	}

	public void setSfqrsh(String sfqrsh) {
		this.sfqrsh = sfqrsh;
	}

	public String getSqthzt() {
		return sqthzt;
	}

	public void setSqthzt(String sqthzt) {
		this.sqthzt = sqthzt;
	}

	public String getThyy() {
		return thyy;
	}

	public void setThyy(String thyy) {
		this.thyy = thyy;
	}

	public SysFood getSysFood() {
		return sysFood;
	}

	public void setSysFood(SysFood sysFood) {
		this.sysFood = sysFood;
	}

	public String getWlzt() {
		return wlzt;
	}

	public void setWlzt(String wlzt) {
		this.wlzt = wlzt;
	}

	public String getHjxdh() {
		return hjxdh;
	}

	public void setHjxdh(String hjxdh) {
		this.hjxdh = hjxdh;
	}

	public String getShdz() {
		return shdz;
	}

	public void setShdz(String shdz) {
		this.shdz = shdz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDdtype() {
		return ddtype;
	}

	public void setDdtype(String ddtype) {
		this.ddtype = ddtype;
	}

	public String getDdje() {
		return ddje;
	}

	public void setDdje(String ddje) {
		this.ddje = ddje;
	}

	public Date getKssj() {
		return kssj;
	}

	public void setKssj(Date kssj) {
		this.kssj = kssj;
	}

	public String getParintid() {
		return parintid;
	}

	public void setParintid(String parintid) {
		this.parintid = parintid;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getYdry() {
		return ydry;
	}

	public void setYdry(String ydry) {
		this.ydry = ydry;
	}

	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysDingdan{" +
				"id='" + id + '\'' +
				", ddtype='" + ddtype + '\'' +
				", ddje='" + ddje + '\'' +
				", kssj=" + kssj +
				", parintid='" + parintid + '\'' +
				", updatetime=" + updatetime +
				", ydry='" + ydry + '\'' +
				", jssj=" + jssj +
				", name='" + name + '\'' +
				", shdz='" + shdz + '\'' +
				", hjxdh='" + hjxdh + '\'' +
				", wlzt='" + wlzt + '\'' +
				", sfqrsh='" + sfqrsh + '\'' +
				", sqthzt='" + sqthzt + '\'' +
				", thyy='" + thyy + '\'' +
				", sysFood=" + sysFood +
				", zongjiage=" + zongjiage +
				", sl=" + sl +
				", bzjthsq='" + bzjthsq + '\'' +
				'}';
	}
}
