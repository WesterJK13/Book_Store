package rps.osipova.bookstore.Bookstore.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CommonSpecifications {

    public static <T> Specification<T> nameContains(String part) {
        return ((root, query, criteriaBuilder) -> part != null ? criteriaBuilder.and(nameContains(root, criteriaBuilder, part)): null);
    }

    public static <T> Predicate nameContains(Root<T> root, CriteriaBuilder criteriaBuilder, String part) {
        Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + part + "%");
        return criteriaBuilder.and(namePredicate);
    }


}
