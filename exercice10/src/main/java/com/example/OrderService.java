package com.example;

import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void addProduct(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Commande introuvable"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        Optional<OrderItem> existing = order.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + 1);
        } else {
            order.getItems().add(new OrderItem(product, 1));
        }
        orderRepository.save(order);
    }

    public void removeProduct(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Commande introuvable"));

        OrderItem item = order.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotInOrderException("Produit absent de la commande"));

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        } else {
            order.getItems().remove(item);
        }
        orderRepository.save(order);
    }

    public ConfirmationOrder validate(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Commande introuvable"));
        order.setValidated(true);
        orderRepository.save(order);
        return new ConfirmationOrder("Commande validée avec succès");
    }
}
