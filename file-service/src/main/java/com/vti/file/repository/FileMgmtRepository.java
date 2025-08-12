package com.vti.file.repository;

import com.vti.file.entity.FileMgmt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileMgmtRepository extends MongoRepository<FileMgmt, String> {

}
