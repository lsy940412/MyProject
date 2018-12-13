package com.itany.orderservice.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.itany.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AlipayClient alipayClient;

    @Override
    public String getOrderCode() {

       String qrCode = null;

        try {
        String orderNo = "订单号"+new Date().getTime();
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setOutTradeNo(orderNo);
        model.setTotalAmount("0.01");
        model.setSubject("orderName");
        request.setNotifyUrl("http://lsyhjr.free.idcfengye.com/order/orderserver/success/lsy");
        request.setBizModel(model);
        AlipayTradePrecreateResponse response = null;

            response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("成功"+response.getBody());
        }else {
            System.out.println("失败"+response.getBody());
        }

        qrCode = response.getQrCode();
        }catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return qrCode;
    }
}
