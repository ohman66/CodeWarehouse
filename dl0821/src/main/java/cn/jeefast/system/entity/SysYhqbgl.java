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
 * 用户钱包管理
 * </p>
 *
 */
@TableName("sys_yhqbgl")
public class SysYhqbgl extends Model<SysYhqbgl> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(type = IdType.UUID)
	private String id;
    /**
     * 所属人员
     */
	private String username;
    /**
     * 余额
     */
	private Double yue;
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

	public Double getYue() {
		return yue;
	}

	public void setYue(Double yue) {
		this.yue = yue;
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
		return "SysYhqbgl{" +
			", id=" + id +
			", username=" + username +
			", yue=" + yue +
			", updatetime=" + updatetime +
			", createtime=" + createtime +
			", updateuser=" + updateuser +
			", createuser=" + createuser +
			"}";
	}
}
