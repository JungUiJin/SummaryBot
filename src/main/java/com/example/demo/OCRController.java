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

import com.example.demo.ChatGPT.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OCRController {
	private final ChatService chatService;
	
	@GetMapping("/OCRController")
    public ResponseEntity ocr() throws  IOException{
        String fileName = "test.JPG";
        File file = ResourceUtils.getFile("classpath:static/image/"+fileName);
        String secretKey = "apikey";
        
        List<String> result = OCRService.callApi("POST", file.getPath(),secretKey , "jpg");
        return new ResponseEntity(result, HttpStatus.OK);
    }
	
	
	@PostMapping("/OCRController")
    public String uploadAndOcr(@RequestParam(value="imageInput", required = false) MultipartFile file, Model model) throws IOException {
        if (file.isEmpty()) {
            return "error"; // 파일이 비어있을 경우 에러를 처리하는 HTML 템플릿으로 이동
        }

        String secretKey = "Your API Key";

        File tempFile = File.createTempFile("temp", file.getOriginalFilename());
        file.transferTo(tempFile);

        List<String> result = OCRService.callApi("POST", tempFile.getPath(), secretKey, "jpg");

        tempFile.delete(); // 임시 파일 삭제
        
        String gptQuestion = chatService.getChatResponse("다음 문장을 분석해서 주제, 간략한 내용을 알려줘. '"+result+"'");

        model.addAttribute("ocrResult", gptQuestion); // OCR 결과를 HTML 템플릿에 전달

        return "ocrResult"; // OCR 결과를 표시하는 HTML 템플릿 이름 반환
    }
}
