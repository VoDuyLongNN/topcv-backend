package com.vn.topcv.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseObject {
	private String status;
	private String message;
	private Object data;
	private Integer currentPage;
	private Integer totalPages;
	private Long totalItems;
}
