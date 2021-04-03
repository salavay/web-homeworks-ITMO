package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.talks.Talk;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;

import java.util.List;

public class TalkService {
    private final TalkRepository talkRepository = new TalkRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void validateMessage(String message) throws ValidationException {
        if (message.length() > 10) {
            throw new ValidationException("Message have to be 10 characters or less");
        }
        if (message.length() == 0) {
            throw new RedirectException("/talks");
        }
    }

    public void saveByLogin(User sourceUser, String targetUserLogin, String message) {
        Talk talk = new Talk();
        talk.setSourceUserId(sourceUser.getId());
        talk.setTargetUserId(userRepository.findByLoginOrEmail(targetUserLogin).getId());
        talk.setText(message);
        talkRepository.save(talk);
    }

    public List<Talk> findAll() {
        return talkRepository.findAll();
    }

    public  List<Talk> findPrivateMessages(long userId) {
        return talkRepository.findPrivateMessages(userId);
    }
}
