package com.eomcs.lms.web;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.eomcs.lms.domain.AuthKey;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.domain.Sms;
import com.eomcs.lms.domain.TermsAgree;
import com.eomcs.lms.service.AuthKeyService;
import com.eomcs.lms.service.EmailService;
import com.eomcs.lms.service.FacebookService;
import com.eomcs.lms.service.MemberService;
import com.eomcs.lms.service.SmsService;

@Controller
@RequestMapping("/member")
public class MemberController {

  private static final Logger logger = LogManager.getLogger(MemberController.class);

  @Autowired MemberService memberService;
  @Autowired AuthKeyService authKeyService;
  @Autowired EmailService emailService;
  @Autowired FacebookService facebookService;
  @Autowired ServletContext servletContext;
  @Autowired SmsService smsService;

  @GetMapping("sms")
  public void sms() {
  }

  @PostMapping(value="checkSmsNo", produces="text/plain;charset=UTF-8")
  @ResponseBody
  private String checkSmsNo(@RequestBody Map<String,Object> content) {

    smsService.deleteTemp();

    String tel = (String) content.get("tel");
    String smsContent = (String) content.get("smsKey");
    Sms sms = new Sms();
    sms.setTel(tel);
    sms.setSmsContent(smsContent);
    sms.setType(1);

    if (smsService.getBySms(sms) != null) {
      return "auth" + 0;
    } else {
      return "auth" + 1;
    }
  }

  @PostMapping(value="smsSend", produces="text/plain;charset=UTF-8")
  @ResponseBody
  public String smsSend(@RequestBody Map<String,Object> content) throws Exception {
    String tel = (String) content.get("tel");
    try {
    smsService.sendAuthSms(tel);
    return "sms" + 1;
    } catch (Exception e) {
      e.printStackTrace();
      return "sms" + 2;
    }
  }

  @GetMapping("invalid")
  public void invalid() {
  }

  @GetMapping("form")
  public void form() {
  }

  @GetMapping("additional-form")
  public String additionalForm() {
    return "member/additionalForm";
  }

  @GetMapping("signUpCompletion")
  public void signUpCompletion() {
  }

  @GetMapping("findUserId")
  public void findUserId() {
  }

  @GetMapping("findPassword")
  public void findPassword() {
  }

  @RequestMapping(value="checkEmail", produces="text/plain;charset=UTF-8")
  @ResponseBody
  private String checkEMail(@RequestBody Map<String,Object> content) throws Exception {

    authKeyService.deleteTemp();

    String email = (String) content.get("email");
    String type = (String) content.get("type");

    AuthKey authKey = new AuthKey();

    authKey.setEmail(email);

    int authType = 0;
    switch(type) {
      case "join" : authType = 1; break;
      case "password" : authType = 2; break;
      default: break;
    }
    if (authType == 0) {
      return "email" + 2; 
    } else {

      authKey.setType(authType);

      if (memberService.get(email) == null) {
        if (authKeyService.getByEmail(authKey) != null) {
          authKeyService.delete(authKey);
        }
        return "email" + 1;
      } else {
        return "email" + 0;
      }
    }
  }

  @PostMapping(value="sendEmail", produces="text/plain;charset=UTF-8")
  @ResponseBody
  private String sendEMail(@RequestBody Map<String,Object> content) {

    int randomCode = new Random().nextInt(899999) + 100000;

    String joinCode = String.valueOf(randomCode);
    String email = (String) content.get("email");
    String type = (String) content.get("type");
    String subject = null;

    AuthKey authKey = new AuthKey();
    authKey.setEmail(email);
    int authType = 0;
    switch(type) {
      case "join" : 
        authType = 1; 
        subject = "BATTLE MATCHING 회원가입에 사용하실 이메일 인증 번호입니다."; 
        break;
      case "password" : 
        authType = 2; 
        subject = "BATTLE MATCHING 비밀번호 찾기에 사용하실 이메일 인증 번호입니다."; 
        break;
      default: break;
    }
    authKey.setType(authType);
    authKey.setKeyContent(joinCode);

    StringBuilder sb = new StringBuilder();
    sb.append("이메일 인증 승인 번호는 ").append(joinCode).append(" 입니다. 인증번호 6자리를 모두 입력해주세요.");
    if (authKeyService.add(authKey) != 0) {
      try {
        emailService.send(subject, sb.toString(), "gwanghosongT@gmail.com", email);
        return "send" + 1;      
      } catch (Exception e) {
        e.printStackTrace();
        return "send" + 2;
      }
    } else {
      return "send" + 0;
    }
  }

  @GetMapping(value="sendPwdEmail", produces="text/plain;charset=UTF-8")
  @ResponseBody
  private String sendPwdEmail(String email) throws UnsupportedEncodingException {

    String tempPwd = UUID.randomUUID().toString().replaceAll("-", ""); 
    tempPwd = tempPwd.substring(0, 10); 
    String subject = "BATTLE MATCHING에서 발급한 임시비밀번호입니다.";
    StringBuilder sb = new StringBuilder();
    Member member = memberService.get(email);
    if (member == null) {
      return "pwdSend" + 3;
    }
    if (!member.getLoginType().equalsIgnoreCase("homepage")) {
      return "pwdSend" + 4;
    }
    member.setPassword(tempPwd);

    sb.append("임시비밀번호는 ")
    .append(tempPwd)
    .append(" 입니다. 로그인하시고 마이페이지에서 비밀번호를 변경해주시길 바랍니다.");

    if (memberService.updatePassword(member) != 0) {
      try {
        emailService.send(subject, sb.toString(), "gwanghosongT@gmail.com", email);
        return "pwdSend" + 1;      
      } catch (Exception e) {
        e.printStackTrace();
        return "pwdSend" + 2;
      }
    } else {
      return "PwdSend" + 0;
    }
  }

  @PostMapping(value="checkAuthNo", produces="text/plain;charset=UTF-8")
  @ResponseBody
  private String checkAuthNo(@RequestBody Map<String,Object> content) {

    authKeyService.deleteTemp();

    String email = (String) content.get("email");
    String keyContent = (String) content.get("authKey");
    String type = (String) content.get("type");
    AuthKey authKey = new AuthKey();
    authKey.setEmail(email);
    authKey.setKeyContent(keyContent);
    int authType = 0;
    switch(type) {
      case "join" : authType = 1; break;
      case "password" : authType = 2; break;
      default: break;
    }
    authKey.setType(authType);

    if (authKeyService.getByAuthKey(authKey) != null) {
      return "auth" + 0;
    } else {
      return "auth" + 1;
    }
  }

  @GetMapping(value="checkId", produces="text/plain;charset=UTF-8")
  @ResponseBody
  private String checkId(String userId) {
    logger.info("checkId >>> " + userId);
    if (memberService.checkId(userId) != null) {
      return "checkId" + 0;
    } else {
      return "checkId" + 1;
    }
  }

  @GetMapping(value="findId", produces="text/plain;charset=UTF-8")
  @ResponseBody
  private String findId(String email, String name) {

    Member member = new Member();
    member.setName(name);
    member.setEmail(email);

    member = memberService.findId(member);

    if (member != null) {
      if (member.getLoginType().equalsIgnoreCase("homepage")) {
        String userId = member.getId();
        return "findId" + 0 + userId;
      } else {
        String loginType = member.getLoginType();
        return "findId" + 2 + loginType;
      }
    } else {
      return "findId" + 1;
    }
  }

  @PostMapping("enter")
  public String add(
      Member member, 
      TermsAgree termsAgree,
      Boolean termsService,
      Boolean termsPrivacy,
      Boolean termsThirdParty,
      HttpSession session) throws Exception {

    if (termsService && termsPrivacy && termsThirdParty) {
      termsAgree.setTermsRequired(true);
    }

    memberService.add(member, termsAgree);

    // 회원가입 후 자동로그인처리
    Member newMember = memberService.get(member.getNo());

    if (newMember == null) {
      return "invalid";
    }

    session.setAttribute("loginUser", newMember);

    return "redirect:signUpCompletion";
  }

  @PostMapping("option-update")
  public String updateOption(
      Member member, 
      HttpSession session) throws Exception {

    Member loginMember = (Member) session.getAttribute("loginUser");

    member.setNo(loginMember.getNo());
System.out.println(member.getPhoto());
    memberService.updateOption(member);

    session.invalidate();

    return "redirect:complete";
  }

  @GetMapping("delete/{no}")
  public String delete(@PathVariable int no) {

    if (memberService.delete(no) == 0) 
      throw new RuntimeException("해당 번호의 회원이 없습니다.");
    return "redirect:../";
  }

  @GetMapping("{no}")
  public String detail(@PathVariable byte[] no, Model model, HttpSession session) {
    
    Member member = (Member) session.getAttribute("loginUser");
    if (member != null) {
    
    byte[] decodedBytes = Base64.getDecoder().decode(no);
    String no2 = new String(decodedBytes);
    
    memberService.get(no2);
    
    model.addAttribute("member", member);
    return "member/detailForm";
  }
    return "redirect:../auth/form";
  }
  
  @GetMapping("profUpdate/{no}")
  public String profChange(@PathVariable byte[] no, Model model, HttpSession session) {
    Member member = (Member) session.getAttribute("loginUser");
    byte[] decodedBytes = Base64.getDecoder().decode(no);
    String no2 = new String(decodedBytes);
    
    memberService.get(no2);
    
    model.addAttribute("member", member);
    return "member/profUpdate"; 
  }

  @GetMapping
  public String list(Model model) {
    List<Member> members = memberService.list(null);
    model.addAttribute("list", members);
    return "member/list";
  }

  @RequestMapping(value="profUpdate/update", method= {RequestMethod.POST})
  public String update(Member member, Part photoFile) throws Exception {  
    memberService.update(member);
    return "redirect:../../../"; 
  } 

  @GetMapping("passwordUpdate/{no}")
  public String passwordUpdate(@PathVariable byte[] no, Model model, HttpSession session) {
    Member member = (Member) session.getAttribute("loginUser");
    byte[] decodedBytes = Base64.getDecoder().decode(no);
    String no2 = new String(decodedBytes);
    
    memberService.get(no2);
    
    model.addAttribute("member", member);
    return "member/passwordUpdate";
  }

  @ResponseBody
  @PostMapping("checkPassword") 
  public Object checkPassword(String id, String password, Model model, HttpSession session) {
    Member member = (Member) session.getAttribute("loginUser");
    member.setPassword(password); 
    
    Map<String,Object> map = new HashMap<>();

    if (memberService.checkPassword(member) != null) { 
      map.put("status", "success"); 
    } else {
      map.put("status", "fail"); 
    }  
    return map; 
  };


  @PostMapping("updatePassword")
  public String updatePassword(Member member, HttpSession session) throws Exception {
    memberService.updatePassword(member);
    
    return "redirect:../../";
  }
}
