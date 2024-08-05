package com.cloth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderStatusDTO {
	
	private Integer orderId;
    private Integer status;

    public OrderStatusDTO(Integer orderId, Integer status) {
        this.orderId = orderId;
        this.status = status;
    }
    
    
}
