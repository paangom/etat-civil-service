package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activation")
public class Activation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Integer activationId;
	
	@Column(name = "Temps_Ecoule", columnDefinition = "bigint ", nullable = false)
    private long tps_Ecoule;
	
	@Column(name = "Temps_Expiration", columnDefinition = "bigint", nullable = false)
    private long tps_Expiration;

	/**
	 * 
	 */
	public Activation() {
	}

	/**
	 * @param tps_Ecoule
	 * @param tps_Expiration
	 */
	public Activation(long tps_Ecoule, long tps_Expiration) {
		this.tps_Ecoule = tps_Ecoule;
		this.tps_Expiration = tps_Expiration;
	}

	public Integer getActivationId() {
		return activationId;
	}

	public void setActivationId(Integer activationId) {
		this.activationId = activationId;
	}

	public long getTps_Ecoule() {
		return tps_Ecoule;
	}

	public void setTps_Ecoule(long tps_Ecoule) {
		this.tps_Ecoule = tps_Ecoule;
	}

	public long getTps_Expiration() {
		return tps_Expiration;
	}

	public void setTps_Expiration(long tps_Expiration) {
		this.tps_Expiration = tps_Expiration;
	}
	
	
	
	
}
