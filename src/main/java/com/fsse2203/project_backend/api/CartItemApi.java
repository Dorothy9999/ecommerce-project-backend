package com.fsse2203.project_backend.api;

import com.fsse2203.project_backend.config.EnvConfig;
import com.fsse2203.project_backend.data.cart.CartItemRequestData;
import com.fsse2203.project_backend.data.cart.CartItemsResponseData;
import com.fsse2203.project_backend.data.cart.dto.CartItemSuccessMsgResponseDto;
import com.fsse2203.project_backend.data.cart.dto.CartItemDetailsResponseDto;
import com.fsse2203.project_backend.exception.*;
import com.fsse2203.project_backend.service.CartItemService;
import com.fsse2203.project_backend.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {EnvConfig.devBaseUrl,
        EnvConfig.prodBaseUrl},
        maxAge = 3600)
@RestController
@RequestMapping("/cart")
public class CartItemApi {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemApi (CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PutMapping("/add-item/{pid}/{quantity}")
    public CartItemSuccessMsgResponseDto addItemToCart (@PathVariable("pid") Integer pid, @PathVariable("quantity") Integer qty,
                                                        Authentication authentication)
            throws ProductNotFoundException, QuantityNumberNotValidException, NotEnoughStockException, UserNotFoundExceptionException {

        String firebaseUid = SecurityUtil.getFirebaseUid(authentication);
        CartItemRequestData cartItemRequestData = new CartItemRequestData(firebaseUid, qty, pid);

        return new CartItemSuccessMsgResponseDto(cartItemService.addItemToCart(cartItemRequestData));
    }

    @GetMapping("")
    public List<CartItemDetailsResponseDto> getCartItems (Authentication authentication) {
        String firebaseUid = SecurityUtil.getFirebaseUid(authentication);
        List<CartItemsResponseData> cartItemsResponseDataList = cartItemService.getCartItems(firebaseUid);

        List <CartItemDetailsResponseDto> cartItemsDto = new ArrayList<>();
        for (CartItemsResponseData data : cartItemsResponseDataList) {
            cartItemsDto.add(new CartItemDetailsResponseDto(data));
        }
        return cartItemsDto;
    }

    @PatchMapping("{pid}/{quantity}")
    public CartItemDetailsResponseDto updateCartItemQty (@PathVariable Integer pid, @PathVariable Integer quantity, Authentication authentication)
            throws QuantityNumberNotValidException, ProductNotFoundException, ProductNotExistsInCartException, NotEnoughStockException {

        String firebaseUid = SecurityUtil.getFirebaseUid(authentication);
        CartItemRequestData cartItemRequestData =
                new CartItemRequestData(firebaseUid, quantity, pid);
        CartItemsResponseData cartItemsResponseData = cartItemService.updateCartItemQty(cartItemRequestData);

        return new CartItemDetailsResponseDto(cartItemsResponseData);
    }

    @DeleteMapping("/{pid}")
    public CartItemSuccessMsgResponseDto deleteCartItem (@PathVariable Integer pid, Authentication authentication)
            throws ProductNotFoundException, ProductNotExistsInCartException{

        String firebaseUid = SecurityUtil.getFirebaseUid(authentication);

        return new CartItemSuccessMsgResponseDto(cartItemService.deleteCartItem(firebaseUid, pid));

    }

}
