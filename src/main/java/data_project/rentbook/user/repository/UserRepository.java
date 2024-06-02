package data_project.rentbook.user.repository;

import data_project.rentbook.book.dto.BookDtoRes;
import data_project.rentbook.user.dto.UserDtoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 24.05.28 작성자 : 정주현
     * 회원가입 시 EMail 중복 SELECT 쿼리
     */
    public int checkEmail(String email) {
        String query = "SELECT EXISTS(SELECT user_id FROM User WHERE email = ?)";
        return this.jdbcTemplate.queryForObject(query, int.class, email);
    }

    /**
     * sellerDao - 2
     * 23.06.29 작성자 : 김성인
     * 회원가입 INSERT 쿼리
     */
    public Long signUp(UserDtoReq.enrollUser request){
        String query = "INSERT INTO User(email, name, password) VALUES (?, ?, ?);";
        Object[] params = new Object[]{
                request.getEmail(),
                request.getName(),
                request.getPassword()
        };

        this.jdbcTemplate.update(query, params);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,Long.class);
    }
    /**
     * 24.05.28 작성자 : 정주현
     * 로그인 시 이메일 찾기 - ACTIVE 상태
     */
    public Long findUserByEmailAtACTIVE(String email) {
        String query = "SELECT user_id FROM User WHERE email = ? and status ='ACTIVE'";
        return this.jdbcTemplate.queryForObject(query, Long.class, email);
    }
    /**
     * 24.05.28 작성자 : 정주현
     * 로그인 시 비밀번호 찾기 - ACTIVE 상태
     */
    public String findPwByEmail(String email) {
        String query = "SELECT password FROM User WHERE email = ? and status ='ACTIVE'";
        return this.jdbcTemplate.queryForObject(query, String.class, email);
    }


    /**
     * 24.05.28 작성자 : 정주현
     * 이메일 인증 전송시(user_id) 검증
     */
    public Long findUserByEmail(String email) {
        String query = "SELECT user_id FROM User WHERE email = ?";
        return this.jdbcTemplate.queryForObject(query, Long.class, email);
    }

    /**
     * 24.05.28 작성자 : 정주현
     * 이메일 인증 완료시 상태 바꾸기
     */
    public void userActiveChange(Long user_id){
        String query = "UPDATE User SET status = 'ACTIVE' WHERE user_id = ?";
        this.jdbcTemplate.update(query, user_id);
    }


    public List<BookDtoRes.MyBookRes> rentBookSearch(Long user_id){
        String query = "SELECT B.book_id, B.book_name, B.book_author " +
                "FROM User U " +
                "INNER JOIN Rent_book RB ON U.user_id = RB.user_id " +
                "INNER JOIN Book B ON RB.book_id = B.book_id " +
                "WHERE U.user_id = ?";

        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new BookDtoRes.MyBookRes(
                        rs.getLong("book_id"),
                        rs.getString("book_name"),
                        rs.getString("book_author")
                ),user_id);

    }
}
