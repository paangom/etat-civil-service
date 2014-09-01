/**
 * 
 */
package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Papangom
 *
 */

@Entity
@Table(name="licences")
public class Licences  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer licenceId;
	
	@Column(name = "code", columnDefinition = "varchar(150) default '' ", nullable = false, unique = true)
    private String code;
	
	@Column(name = "Date_Activation", columnDefinition = "bigint ", nullable = false)
    private long date_Activation;
	
	@Column(name = "Duree", columnDefinition = "bigint", nullable = false)
    private long duree;
	
	@Column(name = "Date_expiration", columnDefinition = "bigint", nullable = false)
    private long date_Expiration;

	/**
	 * 
	 */
	public Licences() {
	}

	/**
	 * @param code
	 * @param date_Activation
	 * @param duree
	 * @param centerCode
	 */
	public Licences(String code, long date_Activation, long duree,
			long date_Expiration) {
		this.code = code;
		this.date_Activation = date_Activation;
		this.duree = duree;
		this.date_Expiration = date_Expiration;
	}

	public Integer getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(Integer licenceId) {
		this.licenceId = licenceId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getDate_Activation() {
		return date_Activation;
	}

	public void setDate_Activation(long date_Activation) {
		this.date_Activation = date_Activation;
	}

	public long getDuree() {
		return duree;
	}

	public void setDuree(long duree) {
		this.duree = duree;
	}

	public long getDate_Expiration() {
		return date_Expiration;
	}

	public void setDate_Expiration(long date_Expiration) {
		this.date_Expiration = date_Expiration;
	}

	
}
