package com.localhostgang.unict.filemanagementservice.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
    File getFileByOwner(User user);
    Iterable<File> getFilesByOwner(User user);

}
