package com.vn.topcv.util;

import com.vn.topcv.entity.PostPersonal;
import com.vn.topcv.entity.enums.EPostStatus;
import org.springframework.data.jpa.domain.Specification;

public class PostPersonalSpecification {
  public static Specification<PostPersonal> hasProvince(String province) {
	return (root, query, criteriaBuilder) ->
		province == null ? null : criteriaBuilder.equal(root.get("province").get("name"), province);
  }

  public static Specification<PostPersonal> hasStatus(EPostStatus status) {
	return (root, query, criteriaBuilder) ->
		status == null ? null : criteriaBuilder.equal(root.get("status"), status);
  }

  public static Specification<PostPersonal> hasJobType(String jobType) {
	return (root, query, criteriaBuilder) ->
		jobType == null ? null : criteriaBuilder.equal(root.get("jobCategory").get("type"), jobType);
  }

  public static Specification<PostPersonal> hasSearchQuery(String searchQuery) {
	return (root, query, criteriaBuilder) -> {
	  if (searchQuery == null || searchQuery.isEmpty()) {
		return null;
	  }
	  String likePattern = "%" + searchQuery.toLowerCase() + "%";
	  return criteriaBuilder.or(
		  criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
		  criteriaBuilder.like(criteriaBuilder.lower(root.join("jobCategory").get("type")), likePattern),
		  criteriaBuilder.like(criteriaBuilder.lower(root.join("jobSubCategory").get("name")), likePattern)
	  );
	};
  }
}
