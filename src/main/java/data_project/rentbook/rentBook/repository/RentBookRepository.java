package data_project.rentbook.rentBook.repository;

import data_project.rentbook.rentBook.dto.RentBookDtoReq;
import data_project.rentbook.rentBook.dto.RentBookDtoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;

@Repository
public class RentBookRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //대여 가능한지 여부 확인
    public String checkRentBook(Long book_id) {
        String query = "SELECT status FROM Book WHERE book_id = ?";
        return this.jdbcTemplate.queryForObject(query, String.class, book_id);
    }

    public Long rentBook(RentBookDtoReq.rentBookReq request) {
        String query = "INSERT INTO Rent_book(user_id,book_id,rent_day,return_day) VALUES(?,?,?,?)";
        Object[] params = new Object[]{
                request.getUser_id(),
                request.getBook_id(),
                LocalDate.now(),
                request.getReturn_day()
        };

        this.jdbcTemplate.update(query, params);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,Long.class);

    }

    public int changeRentBook(Long book_id, String status) {
        String updateQuery = "UPDATE Book SET " +
                "status = ? " +
                "WHERE book_id = ? ";
        return jdbcTemplate.update(updateQuery,status,book_id);
    }

    public RentBookDtoRes.RentBookRes selectRentBook(Long rent_book_id) {
        String query = "SELECT rent_book_id, book_id, rent_day, return_day FROM Rent_book WHERE rent_book_id = ?";

        return this.jdbcTemplate.queryForObject(query, new Object[]{rent_book_id},
                (rs, rowNum) -> new RentBookDtoRes.RentBookRes(
                        rs.getLong("rent_book_id"),
                        rs.getLong("book_id"),
                        rs.getTimestamp("rent_day").toLocalDateTime().toLocalDate(),
                        rs.getTimestamp("return_day").toLocalDateTime().toLocalDate()
                )
        );
    }
    public Long getBookIdByRentBookId(Long rentBookId) {
        String query = "SELECT book_id FROM Rent_book WHERE rent_book_id = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{rentBookId}, Long.class);
    }


    public int returnBook(Long rent_book_id) {
        String query = "DELETE FROM Rent_book WHERE rent_book_id = ?";
        return jdbcTemplate.update(query,rent_book_id);
    }

    public int renewBook(Long rent_book_id) {
        String updateQuery = "UPDATE Rent_book SET " +
                "return_day = DATE_ADD(return_day, INTERVAL 7 DAY) " +
                "WHERE rent_book_id = ?";
        return jdbcTemplate.update(updateQuery, rent_book_id);
    }
}

