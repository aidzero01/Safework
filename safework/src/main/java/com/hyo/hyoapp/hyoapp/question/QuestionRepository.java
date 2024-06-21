package com.hyo.hyoapp.hyoapp.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable);

    //검색 기능 findall 메서드는 specification 과
    // pageable객체를 사용하여 db에서 questuin엔티티를 조회한 결과를 페이징하여 반환
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
    @Query("SELECT COUNT(a) FROM Question q JOIN q.answerList a WHERE q.id = :questionId")
    int countAnswersByQuestionId(Integer questionId);
}
