package com.agatap.veshje.service;

import com.agatap.veshje.controller.DTO.CreateUpdateOrdersDTO;
import com.agatap.veshje.controller.DTO.OrdersDTO;
import com.agatap.veshje.controller.DTO.ShoppingCartDTO;
import com.agatap.veshje.controller.DTO.SizeDTO;
import com.agatap.veshje.controller.mapper.OrdersDTOMapper;
import com.agatap.veshje.model.*;
import com.agatap.veshje.repository.OrdersRepository;
import com.agatap.veshje.repository.SizeRepository;
import com.agatap.veshje.service.exception.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersService {

    private UserService userService;
    private ProductService productService;
    private OrdersRepository ordersRepository;
    private OrdersDTOMapper mapper;
    private DeliveryService deliveryService;
    private PaymentsService paymentsService;
    private PaymentsTypeService paymentsTypeService;
    private ShoppingCartService shoppingCartService;
    private OrderCheckoutDetailsService orderCheckoutDetailsService;
    private CouponCodeService couponCodeService;
    private OrderAddressDataService orderAddressDataService;
    private OrderItemService orderItemService;
    private MailSenderService mailSenderService;

    public List<OrdersDTO> getAllOrders() {
        return ordersRepository.findAll().stream()
                .map(orders -> mapper.mappingToDTO(orders))
                .collect(Collectors.toList());
    }

    public OrdersDTO findOrdersDTOById(Integer id) throws OrdersNotFoundException {
        return ordersRepository.findById(id)
                .map(orders -> mapper.mappingToDTO(orders))
                .orElseThrow(() -> new OrdersNotFoundException());
    }

    public Orders findOrdersOById(Integer id) throws OrdersNotFoundException {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new OrdersNotFoundException());
    }

    public OrdersDTO createOrdersDTO(Integer userId) throws DeliveryNotFoundException, ProductNotFoundException,
            PaymentsTypeNotFoundException, UserNotFoundException, CouponCodeNotFoundException, AddressNotFoundException,
            PaymentsDataInvalidException, MessagingException {
        Orders orders = new Orders();
        OrderCheckoutDetails orderCheckoutDetails = orderCheckoutDetailsService.getOrderCheckoutDetails();
        Delivery deliveryById = deliveryService.findDeliveryById(orderCheckoutDetails.getDeliveryId());
        orders.setOrderStatus(OrderStatus.BOUGHT);
        orders.setCreateDate(OffsetDateTime.now());
        orders.setDeliveryPrice(shoppingCartService.checkDeliveryPrice(orderCheckoutDetails.getDeliveryId()));
        orders.setDeliveryType(deliveryById.getName());
        orders.setPaymentType(paymentsTypeService.findPaymentsTypeById(orderCheckoutDetails.getPaymentId()).getName());
        for (ShoppingCartDTO shoppingCart : shoppingCartService.getAllProductsInCart()) {
            if (shoppingCart.getCouponCode() != null) {
                orders.setTotalProducts(shoppingCartService.getTotalSalePrice());
                orders.setTotalAmount(shoppingCartService.getTotalSalePrice()
                        + shoppingCartService.checkDeliveryPrice(orderCheckoutDetails.getDeliveryId()));
                CouponCode couponCodeByCode = couponCodeService.findCouponCodeByCode(shoppingCart.getCouponCode());
                orders.setDiscount(shoppingCartService.getTotalPrice() - shoppingCartService.getTotalSalePrice());
                orders.setCouponsCode(couponCodeByCode);
                couponCodeByCode.getOrders().add(orders);
            } else {
                orders.setTotalProducts(shoppingCartService.getTotalPrice());
                orders.setTotalAmount(shoppingCartService.getTotalPrice()
                        + shoppingCartService.checkDeliveryPrice(orderCheckoutDetails.getDeliveryId()));
                orders.setDiscount(0.0);
            }
        }


        Payments payments = paymentsService.createPayments(userId);
        orders.setPayment(payments);
        payments.setOrders(orders);

        OrderAddressData orderAddressData = orderAddressDataService.createOrderAddress();
        orderAddressData.setOrder(orders);
        orders.setOrderAddressData(orderAddressData);

        orders.setDelivery(deliveryById);
        deliveryById.getOrders().add(orders);

        User user = userService.findUserById(userId);
        orders.setUser(userService.findUserById(userId));
        user.getOrders().add(orders);

        for (ShoppingCartDTO shoppingCart : shoppingCartService.getAllProductsInCart()) {
            for (int i = 0; i < shoppingCart.getQuantity(); i++) {
                Product productById = productService.findProductById(shoppingCart.getProductId());
                productById.getOrders().add(orders);
                orders.getProducts().add(productById);
            }
            OrderItem orderItem = orderItemService.createOrderItem(shoppingCart);
            orderItem.setOrder(orders);
            orders.getOrderItem().add(orderItem);
        }

        Orders saveOrder = ordersRepository.save(orders);
        orderCheckoutDetailsService.clearOrderCheckoutDetails();
        shoppingCartService.clearShoppingCart();

//        mailSenderService.sendMail(user.getEmail(), "Veshje shop - order confirmation", null, true,
//                "order-mail-confirmation");
        return mapper.mappingToDTO(saveOrder);
    }


    public OrdersDTO updateOrdersDTO(CreateUpdateOrdersDTO updateOrdersDTO, Integer id) throws OrdersNotFoundException {
        Orders orders = findOrdersOById(id);
        orders.setOrderStatus(updateOrdersDTO.getOrderStatus());
        orders.setUpdateDate(OffsetDateTime.now());
        //todo bind to foreign tables
        Orders updateOrders = ordersRepository.save(orders);
        return mapper.mappingToDTO(updateOrders);
    }

    public OrdersDTO deleteOrdersDTO(Integer id) throws OrdersNotFoundException {
        Orders orders = findOrdersOById(id);
        ordersRepository.delete(orders);
        return mapper.mappingToDTO(orders);
    }

    public List<OrdersDTO> findOrdersByUserId(Integer id) throws UserNotFoundException {
        return ordersRepository.findByUserId(id)
                .stream()
                .map(orders -> mapper.mappingToDTO(orders))
                .collect(Collectors.toList());
    }
}
