package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.enums.i18n.ErrorsI18N;
import com.t1tanic.true_vision.enums.i18n.MessagesI18N;
import com.t1tanic.true_vision.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageUtil messageUtil;

    @Override
    public String getMessage(MessagesI18N message, Object... args) {
        log.info("Resolving message for key: {}", message.getKey());
        return messageUtil.resolve(message.getKey(), args);
    }

    @Override
    public String getMessage(ErrorsI18N error, Object... args) {
        log.info("Resolving error message for key: {}", error.getKey());
        return messageUtil.resolve(error.getKey(), args);
    }
}
