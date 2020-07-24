package com.agatap.veshje.controller;

import com.agatap.veshje.controller.DTO.OrderAddressDataDTO;
import com.agatap.veshje.controller.DTO.OrderItemDTO;
import com.agatap.veshje.controller.DTO.OrdersDTO;
import com.agatap.veshje.service.*;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.components.barcode4j.AbstractBarcodeEvaluator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    private InvoiceService invoiceService;

    @GetMapping("/open-report/{id}")
    public void openInvoice(@PathVariable Integer id, HttpServletResponse response) throws IOException, JRException,
            OrderItemNotFoundException, AddressNotFoundException, OrdersNotFoundException, UserNotFoundException,
            DeliveryNotFoundException {
        invoiceService.openInvoice(id, response);
    }

    @GetMapping("/save-report/{id}")
    public void saveInvoiceInDownloadsPath(@PathVariable Integer id)
            throws FileNotFoundException, JRException, OrderItemNotFoundException, AddressNotFoundException,
            ProductNotFoundException, OrdersNotFoundException, UserNotFoundException, DeliveryNotFoundException {
        invoiceService.saveInvoiceInDownloadsPath(id);
    }

}
