package com.hyo.hyoapp.hyoapp.question;

import java.util.ArrayList;
import java.util.List;

import com.hyo.hyoapp.hyoapp.answer.Answer;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.hyo.hyoapp.hyoapp.DataNotFoundException;
import com.hyo.hyoapp.hyoapp.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList(){
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            Question question1 = question.get();
            question1.setViews(question1.getViews()+1);
            this.questionRepository.save(question1);
            return question1;
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user){
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    public Page<Question> getList(int page, String sort, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();

        if ("views".equalsIgnoreCase(sort)) {
            sorts.add(Sort.Order.desc("views")); // 조회수 기준으로 내림차순 정렬
        } else {
            sorts.add(Sort.Order.desc("createDate")); // 기본적으로는 최신순으로 정렬
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
        Page<Question> result = this.questionRepository.findAll(spec, pageable);

        // 로그 추가
        System.out.println("Page number: " + page);
        System.out.println("Sort: " + sort);
        System.out.println("Keyword: " + kw);
        System.out.println("Sort orders: " + sorts);
        System.out.println("Total elements: " + result.getTotalElements());
        System.out.println("Total pages: " + result.getTotalPages());
        System.out.println("Current page content size: " + result.getContent().size());

        return result;
    }




    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType .LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

    public void modify(Question question, String subject, String content){
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
}
