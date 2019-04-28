package com.mt.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="mt_order")
@Data
@ToString(exclude= {"userId","roomId"})
@NoArgsConstructor
@JsonInclude(value=Include.NON_NULL)
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name="order_id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer orderId;
	
	@Column(name="order_no")
	private Long orderNo;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User userId;
	
	@ManyToOne
	@JoinColumn(name="room_id")
	private Room roomId;
	
	@Column(name="payment")
	private BigDecimal payment;
	
	@Column(name="payment_type")
	private Integer paymentType;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="order_desc")
	private String orderDesc;
	
	@Column(name="payment_time")
	private Date paymentTime;
	
	@Column(name="send_time")
	private Date sendTime;
	
	@Column(name="end_time")
	private Date endTime;
	
	@Column(name="close_time")
	private Date closeTime;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="update_time")
	private Date updateTime;

	

	public Order(Integer orderId, Long orderNo, User userId,BigDecimal payment,
			Integer status) {
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.userId = userId;
		this.payment = payment;
		this.status = status;
	}



	public Order(Integer orderId, Long orderNo, Room roomId, BigDecimal payment, Integer paymentType, Integer status,
			String orderDesc, Date paymentTime, Date sendTime, Date endTime, Date closeTime, Date createTime) {
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.roomId = roomId;
		this.payment = payment;
		this.paymentType = paymentType;
		this.status = status;
		this.orderDesc = orderDesc;
		this.paymentTime = paymentTime;
		this.sendTime = sendTime;
		this.endTime = endTime;
		this.closeTime = closeTime;
		this.createTime = createTime;
	}
	
	
	
	
}
