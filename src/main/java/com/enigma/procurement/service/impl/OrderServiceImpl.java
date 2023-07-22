package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.Order;
import com.enigma.procurement.entity.OrderDetail;
import com.enigma.procurement.entity.Product;
import com.enigma.procurement.entity.ProductPrice;
import com.enigma.procurement.model.request.OrderRequest;
import com.enigma.procurement.model.response.OrderDetailResponse;
import com.enigma.procurement.model.response.OrderResponse;
import com.enigma.procurement.repository.OrderRepository;
import com.enigma.procurement.service.OrderDetailService;
import com.enigma.procurement.service.OrderService;
import com.enigma.procurement.service.ProductPriceService;
import com.enigma.procurement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final ProductPriceService productPriceService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public OrderResponse create(OrderRequest request) {
        Order order = orderRepository.save(new Order());
        order.setInvoice(generateInvoice());

        List<OrderDetailResponse> orderDetailResponses = request.getOrderDetails().stream().map(orderDetailRequest -> {
            ProductPrice productPrice = productPriceService.getById(orderDetailRequest.getProductPriceId());
            Product product = productPrice.getProduct();

            OrderDetail orderDetail = orderDetailService.create(OrderDetail.builder()
                    .order(order)
                    .productPrice(productPrice)
                    .quantity(orderDetailRequest.getQuantity())
                    .build());

            order.getOrderDetails().add(orderDetail);

            return OrderDetailResponse.builder()
                    .id(orderDetail.getId())
                    .vendor(productPrice.getVendor().getName())
                    .name(product.getName())
                    .category(product.getCategory().getCategory())
                    .quantity(orderDetailRequest.getQuantity())
                    .price(productPrice.getPrice())
                    .subTotal(productPrice.getPrice() * orderDetailRequest.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        Long grandTotal = orderDetailResponses.stream()
                .mapToLong(OrderDetailResponse::getSubTotal).sum();

        return OrderResponse.builder()
                .invoice(order.getInvoice())
                .orderDate(order.getOrderDate())
                .orderDetails(orderDetailResponses)
                .grandTotal(grandTotal)
                .build();
    }

    @Override
    public List<Order> getByDay() {
        return new ArrayList<>();
    }

    @Override
    public List<Order> getByWeek() {
        return new ArrayList<>();
    }

    private String generateInvoice() {
        String prefix = "EP";
        Date currentDate = new Date();
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
        int counterOrder = getByDay().size();

        return prefix + "-" + formattedDate + "-" + counterOrder + 1;
    }
}
