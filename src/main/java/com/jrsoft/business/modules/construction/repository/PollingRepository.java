package com.jrsoft.business.modules.construction.repository;


import com.jrsoft.business.modules.construction.domain.Polling;

/**
 * 操作图书的JAP类
 *
 * @author Blueeyedboy
 * @create 2018-10-13 15:29
 **/
public interface PollingRepository {
	Polling findByName(String name);
}
