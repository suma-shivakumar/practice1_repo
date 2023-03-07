package com.example.product.services;

import com.example.product.domain.requests.AddAmountToWalletRequestDTO;
import com.example.product.domain.requests.UserBuysProductRequestDTO;
import com.example.product.domain.requests.UserLoginRequestDTO;
import com.example.product.domain.requests.UserRegistrationRequestDTO;
import com.example.product.domain.responses.MessageResponse;
import com.example.product.domain.responses.UserDetailsAfterLoginResponse;
import com.example.product.model.ProductUserMappingEntity;
import com.example.product.model.ProductsEntity;
import com.example.product.model.UserRolesEntity;
import com.example.product.model.UsersEntity;
import com.example.product.repository.ProductUserMappingRepository;
import com.example.product.repository.ProductsRepository;
import com.example.product.repository.UserRolesRepository;
import com.example.product.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Objects;

@Service
public class UsersServices {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductUserMappingRepository productUserMappingRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    public MessageResponse userRegistration(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!Objects.isNull(userRegistrationRequestDTO)) {
            final String firstName = userRegistrationRequestDTO.getFirstName();
            final String lastName = userRegistrationRequestDTO.getLastName();
            final String emailId = userRegistrationRequestDTO.getEmailId();
            final String encodedPassword = passwordEncoder.encode(userRegistrationRequestDTO.getPassword());
            final Integer phoneNumber = userRegistrationRequestDTO.getPhoneNumber();

            if (usersRepository.existsByEmailId(emailId)) {
                return new MessageResponse("With this email already account is present!");
            } else {
                UsersEntity usersEntity = new UsersEntity();
                usersEntity.setEmailId(emailId);
                usersEntity.setPassword(encodedPassword);
                usersEntity.setFirstName(firstName);
                usersEntity.setLastName(lastName);
                usersEntity.setPhoneNumber(phoneNumber);

                final UsersEntity save = usersRepository.save(usersEntity);
                if (!Objects.isNull(save.getUserId())) {
                    UserRolesEntity userRolesEntity = new UserRolesEntity();
                    userRolesEntity.setUserId(save.getUserId());
                    userRolesEntity.setRoleId(2);//1 for admin,2 for user
                    userRolesRepository.save(userRolesEntity);
                    return new MessageResponse("User details registered successfully!");
                }
            }

        }
        return new MessageResponse("Unable to save details. Error occured while saving details");
    }

    public UserDetailsAfterLoginResponse fetchUserDetailsAfterLogin(UserLoginRequestDTO loginRequest) {
        UserDetailsAfterLoginResponse userDetailsAfterLoginResponse = new UserDetailsAfterLoginResponse();

        if (!Objects.isNull(loginRequest)) {
            final String emailId = loginRequest.getEmailId();
            final String password = loginRequest.getPassword();

            final UsersEntity byEmailId = usersRepository.findByEmailId(emailId);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (!Objects.isNull(byEmailId) && !Objects.isNull(byEmailId.getPassword()) && passwordEncoder.matches(password, byEmailId.getPassword())) {
                userDetailsAfterLoginResponse.setUserId(byEmailId.getUserId());
                userDetailsAfterLoginResponse.setEmailId(byEmailId.getEmailId());
                userDetailsAfterLoginResponse.setFirstName(byEmailId.getFirstName());
                userDetailsAfterLoginResponse.setLastName(byEmailId.getLastName());
                userDetailsAfterLoginResponse.setPhoneNumber(byEmailId.getPhoneNumber());
                userDetailsAfterLoginResponse.setWallet(byEmailId.getWallet());
            }

        }
        return userDetailsAfterLoginResponse;
    }

    public MessageResponse updateWalletAmountForAUser(AddAmountToWalletRequestDTO addAmountToWalletRequestDTO) {
        MessageResponse messageResponse = new MessageResponse();
        final String emailId = addAmountToWalletRequestDTO.getEmailId();

        final UsersEntity byEmailId = usersRepository.findByEmailId(emailId);
        if (!Objects.isNull(byEmailId) && addAmountToWalletRequestDTO.getAmount() != 0) {
            int amount = byEmailId.getWallet() + addAmountToWalletRequestDTO.getAmount();
            byEmailId.setWallet(amount);
        }

        final UsersEntity save = usersRepository.save(byEmailId);
        if (!Objects.isNull(save.getEmailId())) {
            messageResponse.setMessage("Successfully transacted amount to wallet!");
        } else {
            messageResponse.setMessage("Unable to transact amount to wallet");
        }
        return messageResponse;
    }

    public MessageResponse userBuysProduct(UserBuysProductRequestDTO userBuysProductRequestDTO) {
        MessageResponse messageResponse = new MessageResponse();

        final String productName = userBuysProductRequestDTO.getProductName();
        final String userEmailId = userBuysProductRequestDTO.getUserEmailId();
        final UsersEntity byEmailId = usersRepository.findByEmailId(userEmailId);
        final ProductsEntity byName = productsRepository.findByName(productName);
        if (!Objects.isNull(byName) && !Objects.isNull(byEmailId)) {
            int productId = byName.getProductId();
            int priceOfProduct = byName.getPrice();
            int stocks = byName.getStocks();

            Integer userId = byEmailId.getUserId();
            int walletAmount = byEmailId.getWallet();

            if (stocks > 0 && walletAmount >= priceOfProduct) {
                walletAmount = walletAmount - priceOfProduct;
                stocks = stocks - 1;

                byEmailId.setWallet(walletAmount);

                byName.setStocks(stocks);
            }
            final UsersEntity userDetails = usersRepository.save(byEmailId);
            final ProductsEntity productDetails = productsRepository.save(byName);

            if (!Objects.isNull(userDetails.getUserId()) && !Objects.isNull(productDetails.getProductId())) {
                messageResponse.setMessage("Successfully placed order");
            } else {
                messageResponse.setMessage("Error occured while placing order");
            }

            ProductUserMappingEntity productUserMappingEntity = new ProductUserMappingEntity();
            productUserMappingEntity.setProductId(productId);
            productUserMappingEntity.setUserId(userId);
            productUserMappingEntity.setCreatedDate(new Date(System.currentTimeMillis()));
            productUserMappingRepository.save(productUserMappingEntity);

        }
        return messageResponse;
    }
}
