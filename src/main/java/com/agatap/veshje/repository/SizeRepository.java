package com.agatap.veshje.repository;

import com.agatap.veshje.model.Size;
import com.agatap.veshje.model.SizeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SizeRepository extends JpaRepository<Size, Integer> {
    List<Size> findAllBySizeType(SizeType sizeType);
    List<Size> findByProductId(String id);
}
