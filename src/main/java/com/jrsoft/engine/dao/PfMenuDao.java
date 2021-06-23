package com.jrsoft.engine.dao;

import com.jrsoft.engine.base.domain.sys.PfMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PfMenuDao extends JpaRepository<PfMenu,String> {
    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    PfMenu getMenuById(@Param("id") String id);
}
