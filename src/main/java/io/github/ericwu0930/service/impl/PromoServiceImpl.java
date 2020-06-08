package io.github.ericwu0930.service.impl;

import io.github.ericwu0930.dao.PromoMapper;
import io.github.ericwu0930.pojo.Promo;
import io.github.ericwu0930.service.PromoService;
import io.github.ericwu0930.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author erichwu
 * @date 2020/6/8
 */
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoMapper promoMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        // 获取对应商品的秒杀信息
        Promo promo = promoMapper.selectByItemId(itemId);
        PromoModel promoModel = convertFromDataObject(promo);
        if(promoModel==null)
            return null;
        DateTime now =new DateTime();
        if(promoModel.getStartDate().isAfterNow())
            promoModel.setStatus(1);
        else if(promoModel.getEndDate().isBeforeNow())
            promoModel.setStatus(3);
        else
            promoModel.setStatus(2);
        return promoModel;
    }

    private PromoModel convertFromDataObject(Promo promo){
        if(promo==null)
            return null;
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promo,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promo.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promo.getStartTime()));
        return promoModel;
    }
}
