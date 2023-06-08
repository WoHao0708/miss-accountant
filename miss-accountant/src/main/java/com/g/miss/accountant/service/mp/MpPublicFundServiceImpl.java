package com.g.miss.accountant.service.mp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g.miss.accountant.dao.mp.MpPublicFundDao;
import com.g.miss.accountant.entity.PublicFund;
import org.springframework.stereotype.Service;

/**
 * @author G
 * @description 公款
 * @date 2023/6/7 4:40 PM
 */
@Service
public class MpPublicFundServiceImpl extends ServiceImpl<MpPublicFundDao, PublicFund> implements MpPublicFundService {
}
