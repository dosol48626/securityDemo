package com.dosol.securitydemo.service;

import com.dosol.securitydemo.domain.Board;
import com.dosol.securitydemo.domain.User;
import com.dosol.securitydemo.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public void insert(Board board, User user) {
        board.setUser(user); //유저 아이디가 들어가겠지 set유저 했으니.
        boardRepository.save(board); //보드 내용 저장
    }

    @Override
    public List<Board> list() {
        return boardRepository.findAll();
    }

    @Override
    public Board findById(Long num) {
        Board board = boardRepository.findById(num).get();
        board.updateHitcount(); //이게 그냥 카운트도 올리면서 특정 그거를 가져온거를 주는거네
        return board;
    }

    @Override
    public void update(Board board) {
        Board oldBoard = boardRepository.findById(board.getNum()).get();
        oldBoard.setTitle(board.getTitle()); //업데이트 할때 타이틀과 등등 바꿀꺼 실어서 올거니 이거만 바꾼다
        oldBoard.setContent(board.getContent());
        boardRepository.save(oldBoard);
    }

    @Override
    public void delete(Long num) {
        boardRepository.deleteById(num);
    }
}
