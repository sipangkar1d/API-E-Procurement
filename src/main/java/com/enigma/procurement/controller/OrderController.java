package com.enigma.procurement.controller;

import com.enigma.procurement.entity.Order;
import com.enigma.procurement.entity.OrderDetail;
import com.enigma.procurement.model.request.OrderRequest;
import com.enigma.procurement.model.response.CommonResponse;
import com.enigma.procurement.model.response.DownloadResponse;
import com.enigma.procurement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Order Success")
                .data(orderService.create(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping()
    public ResponseEntity<?> downloadReportThisDay() {
        List<DownloadResponse> responses = orderService.getByDay();
        String fileName = "src/main/resources/report-Day-" + LocalDate.now() + ".csv";
        return getDownloadReport(responses, fileName);
    }

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/{month}")
    public ResponseEntity<?> downloadReportByMonth(@PathVariable(name = "month") Integer month) {
        List<DownloadResponse> responses = orderService.getByMonth(month);
        String fileName = "src/main/resources/report-Month-" + LocalDate.now() + ".csv";
        return getDownloadReport(responses, fileName);

    }

    private ResponseEntity<?> getDownloadReport(List<DownloadResponse> responses, String fileName) {
        try (FileWriter writer = new FileWriter(fileName);
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT
                             .withHeader("Kode Barang", "Tanggal", "Nama Vendor", "Nama Barang", "Kategory", "Harga Barang", "Qty", "Jumlah"))) {

            for (DownloadResponse response : responses) {
                csvPrinter.printRecord(
                        response.getProductCode(),
                        response.getOrderDate(),
                        response.getVendor(),
                        response.getProductName(),
                        response.getCategory(),
                        response.getPrice(),
                        response.getQuantity(),
                        response.getSubTotal()
                );
            }

            Path filePath = Paths.get(fileName);
            Resource resource;
            try {
                resource = new UrlResource(filePath.toUri());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException("Failed: ");
        }
    }
}
