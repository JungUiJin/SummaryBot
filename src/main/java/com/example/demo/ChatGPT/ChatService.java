package com.example.demo.ChatGPT;

import org.springframework.stereotype.Service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatgptService chatgptService;

    public String getChatResponse(String prompt) {
            // ChatGPT 에게 질문 전달
            return chatgptService.sendMessage(prompt);
    }
}
