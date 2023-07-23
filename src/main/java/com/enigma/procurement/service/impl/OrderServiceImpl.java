package com.enigma.procurement.service.impl;

import com.enigma.procurement.entity.*;
import com.enigma.procurement.model.request.OrderRequest;
import com.enigma.procurement.model.request.ProductOnWarehouseRequest;
import com.enigma.procurement.model.response.DownloadResponse;
import com.enigma.procurement.model.response.OrderDetailResponse;
import com.enigma.procurement.model.response.OrderResponse;
import com.enigma.procurement.repository.OrderRepository;
import com.enigma.procurement.service.*;
import com.enigma.procurement.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private final ValidationUtil validationUtil;
    private final ProductOnWarehouseService productOnWarehouseService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public OrderResponse create(OrderRequest request) {
        validationUtil.validate(request);

        Order order = orderRepository.save(new Order());
        String invoice = generateInvoice();
        order.setInvoice(invoice);

        List<OrderDetailResponse> orderDetailResponses = request.getOrderDetails().stream().map(orderDetailRequest -> {
            ProductPrice productPrice = productPriceService.getById(orderDetailRequest.getProductPriceId());
            if (!productPrice.getVendor().getId().equals(request.getVendorId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some product is not from this vendor");

            Product product = productPrice.getProduct();

            OrderDetail orderDetail = orderDetailService.create(OrderDetail.builder()
                    .order(order)
                    .productPrice(productPrice)
                    .quantity(orderDetailRequest.getQuantity())
                    .build());

            productOnWarehouseService.createOrUpdate(
                    ProductOnWarehouseRequest.builder()
                            .productCode(product.getProductCode())
                            .name(product.getName())
                            .quantity(orderDetail.getQuantity())
                            .build()
            );

            return OrderDetailResponse.builder()
                    .vendor(productPrice.getVendor().getName())
                    .name(product.getName())
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
    public List<DownloadResponse> getByDay() {
        Specification<Order> specification = getOrderSpecification();

        return getDownloadResponses(specification);
    }

    private static Specification<Order> getOrderSpecification() {
        Specification<Order> specification = (root, query, criteriaBuilder) -> {
            LocalDate date = LocalDate.now();
            List<Predicate> predicates = List.of(criteriaBuilder.equal(criteriaBuilder
                    .function("DATE", LocalDate.class, root.get("orderDate")), date));

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        return specification;
    }


    @Override
    public List<DownloadResponse> getByMonth(Integer month) {
        Specification<Order> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = List.of(criteriaBuilder.equal(criteriaBuilder
                    .function("MONTH", Integer.class, root.get("orderDate")), month));

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        return getDownloadResponses(specification);
    }

    private List<DownloadResponse> getDownloadResponses(Specification<Order> specification) {
        List<Order> orders = orderRepository.findAll(specification);
        List<DownloadResponse> downloadResponses = new ArrayList<>();

        for (Order order : orders) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                downloadResponses.add(DownloadResponse.builder()
                        .productCode(orderDetail.getProductPrice().getProduct().getProductCode())
                        .orderDate(String.valueOf(order.getOrderDate()))
                        .vendor(orderDetail.getProductPrice().getVendor().getName())
                        .productName(orderDetail.getProductPrice().getProduct().getName())
                        .category(orderDetail.getProductPrice().getProduct().getCategory().getCategory())
                        .price(orderDetail.getProductPrice().getPrice())
                        .quantity(orderDetail.getQuantity())
                        .subTotal(orderDetail.getQuantity() *orderDetail.getProductPrice().getPrice())
                        .build());
            }
        }
        return downloadResponses;
    }

    private String generateInvoice() {
        String prefix = "EP";
        Date currentDate = new Date();
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);

        int counterOrder = orderRepository.findAll(getOrderSpecification()).size();

        return prefix + "-" + formattedDate + "-" + counterOrder + 1;
    }
}
