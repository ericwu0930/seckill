package io.github.ericwu0930.service;

import io.github.ericwu0930.service.model.PromoModel;

/**
 * @author erichwu
 * @date 2020/6/8
 */
public interface PromoService {
    PromoModel getPromoByItemId(Integer itemId);
}
