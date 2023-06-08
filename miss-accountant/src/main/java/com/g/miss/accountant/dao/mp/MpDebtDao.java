package com.g.miss.accountant.dao.mp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.g.miss.accountant.entity.Debt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author G
 * @description 債權
 * @date 2023/6/8 12:20 PM
 */
@Repository
public interface MpDebtDao extends BaseMapper<Debt> {

    List<Debt> listDebtByGroupId(@Param("groupId") String groupId, @Param("isDelete") int isDelete);

    List<Debt> listDebtByGroupIdAndUserId(@Param("groupId") String groupId, @Param("userId") String userId, @Param("isDelete") int isDelete);

    List<Debt> listDebtByGroupIdAndCreditorId(@Param("groupId") String groupId, @Param("creditorId") String creditorId, @Param("isDelete") int isDelete);

}
