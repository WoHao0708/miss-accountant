package com.g.miss.accountant.dao;

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
public interface DebtDao extends BaseMapper<Debt> {

    /**
     * 取得群組債權列表
     *
     * @param groupId  群組id
     * @param isDelete 是否刪除
     * @return 結果
     */
    List<Debt> listDebtByGroupId(@Param("groupId") String groupId, @Param("isDelete") int isDelete);

    /**
     * 取得使用者在群組的債
     *
     * @param groupId  群組id
     * @param userId   使用者id
     * @param isDelete 是否刪除
     * @return 結果
     */
    List<Debt> listDebtByGroupIdAndUserId(@Param("groupId") String groupId, @Param("userId") String userId, @Param("isDelete") int isDelete);

    /**
     * 取得使用者在群組的債權
     *
     * @param groupId    群組id
     * @param creditorId 使用者id
     * @param isDelete   是否刪除
     * @return 結果
     */
    List<Debt> listDebtByGroupIdAndCreditorId(@Param("groupId") String groupId, @Param("creditorId") String creditorId, @Param("isDelete") int isDelete);
}
