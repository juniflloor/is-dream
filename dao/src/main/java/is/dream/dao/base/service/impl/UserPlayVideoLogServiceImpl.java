package is.dream.dao.base.service.impl;

import is.dream.dao.base.service.UserPlayVideoLogService;
import is.dream.dao.entiry.UserPlayVideoLog;
import is.dream.dao.inter.UserPlayVideoLogDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/11 11:51
 */
public class UserPlayVideoLogServiceImpl implements UserPlayVideoLogService {

    @Autowired
    private UserPlayVideoLogDao userPlayVideoLogDao;

    @Override
    public void save(UserPlayVideoLog userPlayVideoLog) {
        userPlayVideoLogDao.save(userPlayVideoLogDao);
    }

    @Override
    public List<UserPlayVideoLog> getByAssociatedUserId(String associatedUserId) {
        return userPlayVideoLogDao.getByAssociatedUserId(associatedUserId);
    }
}
