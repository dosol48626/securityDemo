package com.dosol.securitydemo.repository;

import com.dosol.securitydemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username); //유저네임이라는 필드가 있음. 그래서 이렇게 써짐. 뭔지 확실히 아니까.
    // select * from tbl_user where uesername = "abcd"; 위에께 이거랑 같은거임
    //사용자 아이디로 사용자 검색하는. 그럼 어제 만들던 멤버에도 이렇게 해야겠네?
}
