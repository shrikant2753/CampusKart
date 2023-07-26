package com.example.campusKart.Product.Controller;

import com.example.campusKart.Product.EntryDTOs.ProductEntryDto;
import com.example.campusKart.Product.EntryDTOs.UpdateProductDto;
import com.example.campusKart.Product.Exception.ProductNotFoundException;
import com.example.campusKart.Product.Payload.FileResponse;
import com.example.campusKart.Product.Service.ProductService;
import com.example.campusKart.Request.Exception.DatabaseException;
import com.example.campusKart.Request.Exception.UserNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

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
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
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
            return new ResponseEntity<>(new FileResponse(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new FileResponse(null, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileResponse(fileName, "Image is uploaded successfully"), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody UpdateProductDto updateProductDto){
        try{
            String response = productService.updateProduct(updateProductDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e){
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestBody Map<String, String> requestBody){
        try{
            ObjectId productId = new ObjectId(requestBody.get("productId"));
            String response = productService.deleteProduct(productId);
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        }
        catch (UserNotFoundException e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        } catch (ProductNotFoundException e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        } catch (DatabaseException e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        }
    }

    @DeleteMapping("delete-image")
    public ResponseEntity<String> deleteImage(@RequestParam String fileName, @RequestParam ObjectId productId){

        try{
            String response  = productService.deleteImage(fileName, productId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}