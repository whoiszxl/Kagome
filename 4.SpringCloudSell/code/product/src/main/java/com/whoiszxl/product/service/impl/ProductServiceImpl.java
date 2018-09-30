package com.whoiszxl.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whoiszxl.product.dataobject.ProductInfo;
import com.whoiszxl.product.dto.CartDTO;
import com.whoiszxl.product.enums.ProductStatusEnum;
import com.whoiszxl.product.enums.ResultEnum;
import com.whoiszxl.product.exception.ProductException;
import com.whoiszxl.product.repository.ProductInfoRepository;
import com.whoiszxl.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductInfoRepository productInfoRepository;
	
	@Override
	public List<ProductInfo> findUpAll() {
		return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public List<ProductInfo> findListByProductIdIn(List<String> productIdList) {
		return productInfoRepository.findByProductIdIn(productIdList);
	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDTO> cartDTOList) {
		for (CartDTO cartDTO : cartDTOList) {
			
			//判断商品是否存在
			Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(cartDTO.getProductId());
			if(!productInfoOptional.isPresent()) {
				throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			
			//判断商品库存是否足够
			ProductInfo productInfo = productInfoOptional.get();
			Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
			if(result < 0) {
				throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
			}
			
			//更新扣减后的库存
			productInfo.setProductStock(result);
			productInfoRepository.save(productInfo);
		}
	}

}
