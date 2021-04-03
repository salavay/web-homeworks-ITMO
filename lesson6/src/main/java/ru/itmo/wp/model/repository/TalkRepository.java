package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.talks.Talk;

import java.util.List;

public interface TalkRepository {
    void save(Talk t);
    List<Talk> findAll();
    List<Talk> findPrivateMessages(long userId);
}
