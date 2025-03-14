package com.caomao.genshin.repository;

import com.caomao.genshin.entity.AvatarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<AvatarEntity, Integer> {
}
