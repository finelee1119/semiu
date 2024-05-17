package com.semiuniv.semiu.controller;


import com.semiuniv.semiu.dto.ProfessorDto;
import com.semiuniv.semiu.dto.StudentDto;
import com.semiuniv.semiu.dto.UserDto;
import com.semiuniv.semiu.entity.Professor;
import com.semiuniv.semiu.entity.Student;
import com.semiuniv.semiu.entity.Users;
import com.semiuniv.semiu.repository.UserRepository;
import com.semiuniv.semiu.service.EmailService;
import com.semiuniv.semiu.service.ProfessorService;
import com.semiuniv.semiu.service.StudentService;
import com.semiuniv.semiu.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/semi/find")
public class FindController {

    private final StudentService studentService;
    private final ProfessorService professorService;
    private final UserDetailService userDetailService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public FindController(StudentService studentService, ProfessorService professorService, UserDetailService userDetailService, UserRepository userRepository, EmailService emailService) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.userDetailService = userDetailService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    //아이디 찾기
    @GetMapping("/showId")
    public String showId(){
        return "find/showId";
    }

    //학생 아이디 찾기
    @GetMapping("/showStudentId")
    public String showStudentId(){
        return "find/studentFindId";
    }

    @PostMapping("studentId")
    public String studentId(@RequestParam("name") String name,
                            @RequestParam("birth") LocalDate birth,
                            @RequestParam("phone") String phone,
                            Model model) {
        Optional<Student> studentName = studentService.findByName(name);

        if (name != null && studentName.isPresent() && studentName.get().getName().equals(name) &&
                studentName.get().getBirth().equals(birth) && studentName.get().getPhone().equals(phone)) {
            model.addAttribute("studentId", studentName.get().getId());
            return "find/studentId";
        }
        model.addAttribute("error", "맞는 정보를 찾을 수 없습니다. 다시 시도해주세요");
        return "error";
    }


    //교수 아이디 찾기
    @GetMapping("/showProfessorId")
    public String showProfessorId(){
        return "find/professorFindId";
    }

    @PostMapping("professorId")
    public String professorId(@RequestParam("name") String name,
                              @RequestParam("birth") LocalDate birth,
                              @RequestParam("phone") String phone,
                              Model model) {
        Optional<Professor> professorOpt = professorService.findByName(name);

        if (professorOpt.isPresent() && professorMatches(professorOpt.get(), name, birth, phone)) {
            model.addAttribute("professorId", professorOpt.get().getId());
            return "find/professorId";
        }

        model.addAttribute("error", "맞는 정보를 찾을 수 없습니다. 다시 시도해주세요");
        return "error";
    }

    private boolean professorMatches(Professor professor, String name, LocalDate birth, String phone) {
        return professor.getName().equals(name) && professor.getBirth().equals(birth) && professor.getPhone().equals(phone);
    }

//    //비밀번호 찾기
//    @GetMapping("/showPassword")
//    public String showPassword(){
//        return "find/showPassword";
//    }
//
//    @PostMapping("findPassword")
//    public String findPassword(@RequestParam("id") Integer id, Model model) {
//        String userIdString = String.valueOf(id);
//        UserDetails userDetails = userDetailService.loadUserByUsername(userIdString);
//        Optional<Users> user = userRepository.findById(userIdString);
//        Users userAccount = user.get();
//        // 조회된 사용자 정보가 null이 아니고, ID와 사용자 이름이 같은지 확인합니다.
//        if (userDetails != null && userDetails.getUsername().equals(userIdString)) {
//            userAccount.setPassword(userIdString);
//            userRepository.save(userAccount);
//            model.addAttribute("password", userDetails.getPassword());
//            return "find/password";
//        } else {
//            model.addAttribute("error", "ID를 다시 확인해주세요");
//            return "errorPage";
//        }
//    }

    //비밀번호 찾기
    @GetMapping("/showPassword")
    public String showPassword(){
        return "find/showPassword";
    }

    @PostMapping("findPassword")
    public String findPassword(@RequestParam("id") Integer id,
                               @RequestParam("email") String email,
                               Model model) {
        // ID가 비어 있는지 확인
        if (id == null) {
            model.addAttribute("error", "ID를 입력해주세요.");
            return "find/password";
        }

        // 이메일이 비어 있는지 확인
        if (email == null || email.isEmpty()) {
            model.addAttribute("error", "이메일을 입력해주세요.");
            return "find/password";
        }

        // 이메일 형식인지 확인
        if (!EmailValidator.isValidEmail(email)) {
            model.addAttribute("error", "유효한 이메일 주소를 입력해주세요.");
            return "find/password";
        }

        Optional<Users> userOpt = userRepository.findById(String.valueOf(id));

        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            String temporaryPassword = EmailValidator.PasswordGenerator.generateTemporaryPassword();
            user.setPassword(temporaryPassword);
            userRepository.save(user);

            try {
                String message = String.format("안녕하세요, %d님. 임시 비밀번호 안내 관련 메일입니다. 귀하의 비밀번호는: %s 입니다. \n 임시 비밀번호로 다시 로그인해 주세요.", id, temporaryPassword);
                // 이메일 주소가 null이 아닌 경우에만 이메일을 보냅니다.
                if (email != null) {
                    emailService.sendSimpleMessage(email, "임시 비밀번호 안내", message);
                }
                log.info(message);
                model.addAttribute("message", "비밀번호가 이메일로 전송되었습니다.");
                return "find/password";
            } catch (Exception e) {
                model.addAttribute("error", "이메일 전송 중 오류가 발생했습니다: " + e.getMessage());
            }
        }
        model.addAttribute("error", "사용자를 찾을 수 없습니다.");
        return "find/password";
    }

    public class EmailValidator {

        public static boolean isValidEmail(String email) {
            // 간단한 이메일 형식 검사를 수행합니다.
            return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        }

        public class PasswordGenerator {

            // 임시 비밀번호 길이 설정
            private static final int TEMP_PASSWORD_LENGTH = 10;
            // 임시 비밀번호에 포함될 문자열
            private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

            public static String generateTemporaryPassword() {
                StringBuilder password = new StringBuilder(TEMP_PASSWORD_LENGTH);
                SecureRandom random = new SecureRandom();

                // 임시 비밀번호 생성
                for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
                    int index = random.nextInt(PASSWORD_CHARS.length());
                    password.append(PASSWORD_CHARS.charAt(index));
                }

                return password.toString();
            }

            public static void main(String[] args) {
                String tempPassword = generateTemporaryPassword();
                System.out.println("임시 비밀번호: " + tempPassword);
            }
        }
    }
}
