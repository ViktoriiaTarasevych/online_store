package com.teamchallenge.online_store.repository;

import com.teamchallenge.online_store.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
