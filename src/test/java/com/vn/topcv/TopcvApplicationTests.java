package com.vn.topcv;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import com.vn.topcv.service.IProvinceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TopcvApplicationTests {

  @Autowired
  private IProvinceService provinceService;

  @Test
  void contextLoads() {
	assertNotNull(provinceService);
  }

}
