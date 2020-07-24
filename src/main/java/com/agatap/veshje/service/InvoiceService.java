package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.OrderAddressDataDTO;
import com.agatap.veshje.controller.DTO.OrderItemDTO;
import com.agatap.veshje.controller.DTO.OrdersDTO;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class InvoiceService {

    private OrdersService ordersService;
    private OrderItemService orderItemService;
    private OrderAddressDataService orderAddressDataService;
    private UserService userService;
    private DeliveryService deliveryService;

    public void openInvoice(@PathVariable Integer id, HttpServletResponse response)
            throws IOException, JRException, OrderItemNotFoundException, UserNotFoundException,
            OrdersNotFoundException, AddressNotFoundException, DeliveryNotFoundException {
        JasperPrint jasperPrint = generateInvoice(id);
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        String nameInvoice = "veshje-invoice-no-" + id + ".pdf";
        response.setHeader("Content-disposition", "inline; filename=" + nameInvoice);

        final OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    }

    public void saveInvoiceInDownloadsPath(@PathVariable Integer id) throws FileNotFoundException, JRException, OrderItemNotFoundException, AddressNotFoundException, ProductNotFoundException, OrdersNotFoundException, UserNotFoundException, DeliveryNotFoundException {
        String separator = System.getProperty("file.separator");
        String path = System.getProperty("user.home").concat(separator).concat("Downloads").concat(separator);
        JasperPrint jasperPrint = generateInvoice(id);
        String downloadPath = path.concat(separator).concat("veshje-invoice-no-").concat(String.valueOf(id)).concat(".pdf");
        JasperExportManager.exportReportToPdfFile(jasperPrint, downloadPath);
    }

    private JasperPrint generateInvoice(@PathVariable Integer id) throws OrderItemNotFoundException, FileNotFoundException, JRException, OrdersNotFoundException, AddressNotFoundException, UserNotFoundException, DeliveryNotFoundException {
        List<OrderItemDTO> orderItem = orderItemService.findOrderItemByOrderId(id);
        File file = ResourceUtils.getFile("classpath:veshje.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderItem);
        Map<String, Object> parameters = getParameterToReport(id);
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    @NotNull
    private Map<String, Object> getParameterToReport(@PathVariable Integer id) throws OrdersNotFoundException, AddressNotFoundException, UserNotFoundException, DeliveryNotFoundException {
        Map<String, Object> parameters = new HashMap<>();
        OrdersDTO order = ordersService.findOrdersDTOById(id);
        OrderAddressDataDTO address = orderAddressDataService.findOrderAddressDataByOrderId(id);
        parameters.put("orderId", id);
        parameters.put("orderIdBarcode", "0000000000000".substring(String.valueOf(id).length()).concat(String.valueOf(id)));
        parameters.put("createDate", order.getCreateDate().getYear() + "/" + order.getCreateDate().getMonthValue() + "/" + order.getCreateDate().getDayOfMonth());
        parameters.put("customerName", address.getFirstName() + " " + address.getLastName());
        parameters.put("customerAddress", address.getStreet() + " " + address.getNo());
        parameters.put("customerCity", address.getPostalCode() + " " + address.getCity());
        parameters.put("customerPhone", address.getPhoneNumber());
        parameters.put("customerEmail", userService.findUserById(order.getUserId()).getEmail());
        parameters.put("deliveryId", order.getDeliveryId());
        parameters.put("deliveryName", deliveryService.findDeliveryById(order.getDeliveryId()).getName());
        parameters.put("deliveryPrice", BigDecimal.valueOf(order.getDeliveryPrice()).round(new MathContext(4, RoundingMode.HALF_EVEN)));
        parameters.put("deliveryPriceVat", BigDecimal.valueOf(order.getDeliveryPrice() * 0.18699187).round(new MathContext(4, RoundingMode.HALF_EVEN)));
        parameters.put("deliveryPriceNet", BigDecimal.valueOf(order.getDeliveryPrice() * 0.81300813).round(new MathContext(4, RoundingMode.HALF_EVEN)));
        parameters.put("total", BigDecimal.valueOf(order.getTotalAmount()).round(new MathContext(4, RoundingMode.HALF_EVEN)));
        parameters.put("discount", BigDecimal.valueOf(order.getDiscount()).round(new MathContext(4, RoundingMode.HALF_EVEN)));
        parameters.put("net", BigDecimal.valueOf(order.getTotalAmount() * 0.81300813).round(new MathContext(5, RoundingMode.HALF_EVEN)));
        parameters.put("vat", BigDecimal.valueOf(order.getTotalAmount() * 0.18699187).round(new MathContext(5, RoundingMode.HALF_EVEN)));
        return parameters;
    }
}
