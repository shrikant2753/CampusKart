package com.example.campusKart.Product.Controller;

import com.example.campusKart.Product.EntryDTOs.ProductEntryDto;
import com.example.campusKart.Product.Payload.FileResponse;
import com.example.campusKart.Product.Service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${project.image}")
    private String path;

    @Autowired
    private ProductService productService;

    @PostMapping("add")
    public ResponseEntity<String> addProduct(@RequestBody ProductEntryDto productEntryDto){
        try{
            String response = productService.addProduct(productEntryDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("image") MultipartFile image, @RequestParam("productId")ObjectId productId) throws IOException {
        String fileName = null;
        try{
            fileName = this.productService.uploadImage(path, image, productId);
        }
        catch(IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(null, "Image is not uploaded due to some error"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(new FileResponse(fileName, "Image is uploaded successfully"), HttpStatus.OK);
    }
}