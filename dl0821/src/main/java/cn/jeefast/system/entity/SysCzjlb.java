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
 * 充值记录管理
 * </p>
 *
 */
@TableName("sys_czjlb")
public class SysCzjlb extends Model<SysCzjlb> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(type = IdType.UUID)
	private String id;
    /**
     * 充值人员
     */
	private String username;
    /**
     * 充值金额
     */
	private Double jine;
    /**
     * 更新时间
     */
	private Date updatetime;
    /**
     * 创建时间
     */
	private Date createtime;
    /**
     * 更新人员
     */
	private String updateuser;
    /**
     * 创建人员
     */
	private String createuser;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getJine() {
		return jine;
	}

	public void setJine(Double jine) {
		this.jine = jine;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysCzjlb{" +
			", id=" + id +
			", username=" + username +
			", jine=" + jine +
			", updatetime=" + updatetime +
			", createtime=" + createtime +
			", updateuser=" + updateuser +
			", createuser=" + createuser +
			"}";
	}
}
