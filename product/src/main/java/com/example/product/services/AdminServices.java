package com.example.product.services;

import com.example.product.domain.responses.MessageResponse;
import com.example.product.model.ProductsEntity;
import com.example.product.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@Slf4j
public class AdminServices {

    @Autowired
    private ProductsRepository productsRepository;

    public MessageResponse excelReader(MultipartFile excel) {
        MessageResponse messageResponse = new MessageResponse();

        List<ProductsEntity> productsEntityList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                ProductsEntity productsEntity = new ProductsEntity();
                XSSFRow row = sheet.getRow(i);
                final String productName = String.valueOf(row.getCell(0));
                ProductsEntity byName = productsRepository.findByName(productName);
                if (Objects.isNull(byName)) {
                    productsEntity.setName(productName);
                    productsEntity.setPrice(Integer.parseInt(String.valueOf(row.getCell(1).getRawValue())));
                    productsEntity.setDescription(String.valueOf(row.getCell(2)));
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    productsEntity.setDateOfManufacture(formatter.parse(String.valueOf(row.getCell(3))));
                    productsEntity.setStocks(Integer.parseInt(String.valueOf(row.getCell(4).getRawValue())));
                }
                productsEntityList.add(productsEntity);
            }

            List<ProductsEntity> productsEntities = productsRepository.saveAll(productsEntityList);
            if (!productsEntities.isEmpty()) {
                messageResponse.setMessage("Successfully stored excel details to db");
            }

        } catch (Exception e) {
            log.error("Error occured while storing details to Data base. {}", e);
        }

        return messageResponse;
    }
}
