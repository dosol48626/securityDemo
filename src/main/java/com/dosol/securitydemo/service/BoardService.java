package com.dosol.securitydemo.service;

import com.dosol.securitydemo.domain.Board;
import com.dosol.securitydemo.domain.User;

import java.util.List;

public interface BoardService {

    void insert(Board board, User user);
    public List<Board> list();
    public Board findById(Long num);
    public void update(Board board);
    public void delete(Long num);
    //근데 여기까지하는데 왜 dto는 안만들지? 그냥 간단하게 할려고 그런건가???
}
