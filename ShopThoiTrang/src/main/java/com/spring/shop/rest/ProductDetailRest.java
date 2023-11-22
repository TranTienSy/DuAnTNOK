package com.spring.shop.rest;

import com.spring.shop.repository.ProductDetailRepository;
import com.spring.shop.request.ProductDetailRequest;
import com.spring.shop.request.ValidateForm;
import com.spring.shop.service.ProductDetailExelService;
import com.spring.shop.service.ProductDetailService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/product")
public class ProductDetailRest {
    @Autowired
     ProductDetailService service;
    @Autowired
    ProductDetailExelService productDetailExelService;
    
  
    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }
    @GetMapping("/getAllBanChay")
    public ResponseEntity<?> getAllBanChay(){
        return ResponseEntity.ok(service.getAllBanChay());
    }
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }


    @GetMapping("/search/{name}")
    public ResponseEntity<?> getAllByProductName(@PathVariable("name") String name){
        return ResponseEntity.ok(service.getAllbyProductName(name));
    }
    @GetMapping("/filter")
    public ResponseEntity<?> getAllByFilter(@RequestParam(name = "name",required = false) String name,
                                            @RequestParam(name = "idcategory",required = false) Integer IdCategory,
                                            @RequestParam(name = "idmaterial",required = false) Integer IdMaterial,
                                            @RequestParam(name = "idcolor",required = false) Integer IdColor,
                                            @RequestParam(name = "idsize",required = false) Integer IdSize,
                                            @RequestParam(name = "idbrand",required = false) Integer IdBrand,                     
                                            @RequestParam("min") Double min,
                                            @RequestParam("max") Double max,
                                            @RequestParam(name = "soLuong",required = false) Integer soLuong,
                                            @RequestParam(name = "soLuong1",required = false) Integer soLuong1){
        return ResponseEntity.ok(service.getAllbyFilter(name,IdColor,IdSize,IdMaterial,IdCategory,IdBrand,min,max,soLuong,soLuong1));
    }

    @GetMapping("/phantrang")
    public ResponseEntity<?> phanTrang(@RequestParam(value = "page",defaultValue = "0") Integer page){
        return ResponseEntity.ok(service.phanTrang(page));
    }
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody ProductDetailRequest productDetail, BindingResult result){
        if (result.hasErrors()){
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.badRequest().body(list);
        }
        return ResponseEntity.ok(service.add(productDetail));
    }
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@Valid @RequestBody ValidateForm validateForm, BindingResult result){
        if (result.hasErrors()){
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.badRequest().body(list);
        }
        if (service.getByCode(validateForm.getCode()) != null){
            return ResponseEntity.notFound().build();

        }


        return ResponseEntity.ok(validateForm);
    }
    @PostMapping("/validateupdate")
    public ResponseEntity<?> valid(@Valid @RequestBody ValidateForm validateForm, BindingResult result){
        if (result.hasErrors()){
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.badRequest().body(list);
        }


        return ResponseEntity.ok(validateForm);
    }

    @PostMapping( value = "/importExel", consumes = "multipart/form-data")
    public ResponseEntity<String> importExel(@RequestParam("file") MultipartFile file) throws IOException {
        try{
            productDetailExelService.importExel(file);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("ok");
        }
        catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.delete(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,@RequestBody ProductDetailRequest request){
        return ResponseEntity.ok(service.update(id,request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.getById(id));
    }
    @GetMapping("/category")
    public ResponseEntity<?> getByIdCategory(@RequestParam("id") Integer id,
                                             @RequestParam("idBrand") Integer idBrand
                                            ){
        return ResponseEntity.ok(service.getProductByCategory(id,idBrand));
    }
    @GetMapping("/quantitySold/{id}")
    public ResponseEntity<?> quantitySold(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.quantitySold(id));
    }
    @GetMapping("/totalSold/{id}")
    public ResponseEntity<?> totalSold(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.totalSold(id));
    }
    @GetMapping("/getVoucher")
    public ResponseEntity<?> getVoucher(){
        return ResponseEntity.ok(service.getVoucher());
    }
    @GetMapping("/getAllVoucher")
    public ResponseEntity<?> getAllVoucher(){
        return ResponseEntity.ok(service.getAllVoucher());
    }
    
    @GetMapping("/getAllVoucherByMinimun/{tongTien}")
    public ResponseEntity<?> getAllVoucherByMinimun(
    	@PathVariable("tongTien") String tongTien
    	){
    	System.out.println(tongTien);
    	return ResponseEntity.ok(service.getAllVoucherByTongTien(Integer.parseInt(tongTien)));
    	
    }

}