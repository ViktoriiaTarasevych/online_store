package com.teamchallenge.online_store.repository;

import com.teamchallenge.online_store.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

}
