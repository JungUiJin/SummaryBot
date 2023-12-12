package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class OCRController {
	@GetMapping("/OCRController")
    public ResponseEntity ocr() throws  IOException{
        String fileName = "test.JPG";
        File file = ResourceUtils.getFile("classpath:static/image/"+fileName);
        String secretKey = "Your API Key";
        
        List<String> result = OCRService.callApi("POST", file.getPath(),secretKey , "jpg");
        return new ResponseEntity(result, HttpStatus.OK);
    }
	
	
	@PostMapping("/OCRController")
    public String uploadAndOcr(@RequestParam(value="imageInput", required = false) MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            return "error"; // 파일이 비어있을 경우 에러를 처리하는 HTML 템플릿으로 이동
        }

        String secretKey = "T0RSWVh5SFpqYUliYXFMdXNmU0xpbndPd1JrYkRITUg=";

        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);

        List<String> result = OCRService.callApi("POST", tempFile.getPath(), secretKey, "jpg");

        tempFile.delete(); // 임시 파일 삭제

        model.addAttribute("ocrResult", result); // OCR 결과를 HTML 템플릿에 전달

        return "redirect:/chatGptController"; // OCR 결과를 표시하는 HTML 템플릿 이름 반환
    }
}
