package com.jrsoft.engine.form.repository;

import com.jrsoft.engine.form.domain.FormBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FormRepository extends JpaRepository<FormBasic, String>, JpaSpecificationExecutor<FormBasic> {
	FormBasic findByFormKey(String formKey);
}