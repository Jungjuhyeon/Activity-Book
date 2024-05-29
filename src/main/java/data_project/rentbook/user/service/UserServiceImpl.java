package data_project.rentbook.user.service;

import data_project.rentbook.book.dto.BookDtoRes;
import data_project.rentbook.email.service.EmailService;
import data_project.rentbook.global.exception.BusinessException;
import data_project.rentbook.global.exception.errorcode.CommonErrorCode;
import data_project.rentbook.user.converter.UserConverter;
import data_project.rentbook.user.dto.UserDtoReq;
import data_project.rentbook.user.dto.UserDtoRes;
import data_project.rentbook.user.repository.UserRepository;
import data_project.rentbook.util.EncryptHelper;
import data_project.rentbook.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EncryptHelper encryptHelper;
    private final EmailService emailService;
    private final RedisUtil redisUtil;

    @Override
    @Transactional
    public UserDtoRes.enrollUser signUp(UserDtoReq.enrollUser request){


        // 이메일 중복 확인( 활동회원인지, 미인증 회원인지)
        if (userRepository.checkEmail(request.getEmail())==1){
            throw new BusinessException(CommonErrorCode.USER_EMAIL_DUPLICATE);
        }

        // 두 비밀번호 일치성 확인
        if(!request.getPassword().equals(request.getPasswordCheck())){
            throw new BusinessException(CommonErrorCode.USER_PASSWORD_NONEQULE);
        }

        //비밀번호 Bcrypt암호화
        try{
            String encryptedPW= encryptHelper.encrypt(request.getPassword());
            request.setPassword(encryptedPW);
        } catch (Exception ignored){
            throw new BusinessException(CommonErrorCode.PASSWORD_ENCRYPTION_ERROR);
        }


        try {
            Long userId = userRepository.signUp(request);
            return UserConverter.userIdRes(userId);
        } catch (Exception exception) {
            throw new BusinessException(CommonErrorCode.DATABASE_ERROR);
        }
    }

    //로그인
    @Override
    public UserDtoRes.userLoginRes login(UserDtoReq.userLoginReq request){
        Long user_id;

        try {
            user_id = userRepository.findUserByEmailAtACTIVE(request.getEmail());
        }catch (Exception exception) {
            throw new BusinessException(CommonErrorCode.USER_ID_PASSWORD_FOUND);
        }

        if(!encryptHelper.isMatch(request.getPassword(),userRepository.findPwByEmail(request.getEmail()))){
            throw new BusinessException(CommonErrorCode.USER_ID_PASSWORD_FOUND);
        }

        return UserConverter.userLoginRes(user_id);
    }

    //대여한 책 목록 조회
    @Override
    public BookDtoRes.searchMyBookList rentBookSearch(Long user_id){
        List<BookDtoRes.MyBookRes> list = userRepository.rentBookSearch(user_id);

        return UserConverter.rentBookList(list);
    }


    //인증번호 요청
    @Override
    public void sendEmailAuth(String email, String text){

        try {
            userRepository.findUserByEmail(email);
        }catch (Exception exception) {
            throw new BusinessException(CommonErrorCode.USER_NOT_FOUND);
        }

        emailService.sendMail(email,"인증");
    }

    @Override
    @Transactional
    public void signUpAuth(UserDtoReq.verifyAuth request){
        //인증이 유효한지
        Long user_id = verifyEmail(request);
        //인증완료시 활성화'
        userRepository.userActiveChange(user_id);

    }

    @Override
    //이메일 인증이 유효
    public Long verifyEmail(UserDtoReq.verifyAuth request) {

        Long user_id;
        try {
            user_id = userRepository.findUserByEmail(request.getEmail());
        }catch (Exception exception) {
            throw new BusinessException(CommonErrorCode.USER_NOT_FOUND);
        }

        String authCode = redisUtil.getData("AuthCode_"+request.getEmail());

        if(!request.getCertificationNumber().equals(authCode)){
            throw new BusinessException(CommonErrorCode.EMAIL_VERIFY_ERROR);
        }

        return user_id;
    }






}
