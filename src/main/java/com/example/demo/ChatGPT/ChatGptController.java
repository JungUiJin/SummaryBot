package com.example.demo.ChatGPT;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatGptController {
	private final ChatService chatService;

    //chat-gpt 와 간단한 채팅 서비스 소스
    @PostMapping("/chatGptController")
    public String test(Model model){
    	String ocr = (String) model.getAttribute("ocrResult");
    	String gptQuestion = chatService.getChatResponse("다음 문장을 분석해서 주제, 내용, 3줄요약을 알려줘.");
    	model.addAttribute("gptQustion", gptQuestion);
        return "ocrResult";
    }
}
